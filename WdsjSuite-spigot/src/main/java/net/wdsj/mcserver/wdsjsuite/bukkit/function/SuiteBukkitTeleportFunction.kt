package net.wdsj.mcserver.wdsjsuite.bukkit.function

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import mc233.cn.wdsjlib.bukkit.utils.BukkitUtils
import mc233.cn.wdsjlib.bukkit.utils.extensions.connect
import mc233.cn.wdsjlib.bukkit.utils.extensions.getLangMessage
import mc233.cn.wdsjlib.bukkit.utils.extensions.sendMessage
import net.jodah.expiringmap.ExpiringMap
import net.md_5.bungee.api.chat.ClickEvent
import net.md_5.bungee.api.chat.ComponentBuilder
import net.wdsj.common.simpleconfig.ConfigurationSection
import net.wdsj.mcserver.wdsjsuite.bukkit.WdsjSuiteBukkit
import net.wdsj.mcserver.wdsjsuite.bukkit.command.sub.CommandTpa
import net.wdsj.mcserver.wdsjsuite.bukkit.command.sub.CommandTpaccept
import net.wdsj.mcserver.wdsjsuite.bukkit.command.sub.CommandTpahere
import net.wdsj.mcserver.wdsjsuite.bukkit.utils.SuiteBukkitUtils
import net.wdsj.mcserver.wdsjsuite.bukkit.utils.SuiteLang
import net.wdsj.servercore.WdsjServerAPI
import net.wdsj.servercore.common.ResultParams
import net.wdsj.servercore.common.command.CommandProxyBuilder
import net.wdsj.servercore.config.invoke.ConfigInvoke
import net.wdsj.servercore.database.frame.redis.RedisCooldown
import net.wdsj.servercore.remote.InvokeResult
import net.wdsj.servercore.utils.extensions.execute
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent
import java.util.concurrent.TimeUnit

/**
 * @author  Arthur
 * @date  2020/12/4 13:40
 * @version 1.0
 */
open class SuiteBukkitTeleportFunction : SuiteBukkitChannelFunction<SuiteBukkitTeleportFunction>("Teleport"),
    SuiteBukkitFunction {

    lateinit var config: TeleportConfig
    lateinit var impl: TeleportFunctionImpl

    open fun requestTeleport(tData: TeleportData, uData: UserData): InvokeResult<RequestResult> {
        return BukkitUtils.getPlayerIfOnline(uData.receiver).execute {
            impl.receiverDataMap[uData] = tData
            it.sendMessage(
                *ComponentBuilder(
                    SuiteLang.formatKey("teleport.request.receive.click")
                        .getLangMessage(it, "requester" to uData.requester)
                )
                    .event(ClickEvent(ClickEvent.Action.RUN_COMMAND, "/tpaccept ${uData.requester}")).create()
            )
            InvokeResult(InvokeResult.Type.SUCCESS, RequestResult.SUCCESS)
        } ?: return InvokeResult(InvokeResult.Type.SUCCESS, RequestResult.NOT_ONLINE)
    }

    open fun replyTeleport(tData: TeleportData, data: UserData): InvokeResult<AcceptReplyResult> {
        var result: AcceptReplyResult = AcceptReplyResult.NOT_EXIST

        //   println(" receive reply : $tData , $data")
        impl.requestMap[data.requester]?.let {
            //     println(" receive reply exist: $tData , $data")
            impl.requestMap.remove(data.requester)
            BukkitUtils.getPlayerIfOnline(data.requester).execute { player ->
                val from: String
                val to: String
                if (tData.comeHere) {
                    from = data.receiver
                    to = data.requester
                } else {
                    from = data.requester
                    to = data.receiver
                }
                result = if (impl.teleport(from, to) == TeleportResult.SUCCESS) {
                    AcceptReplyResult.SUCCESS
                } else {
                    AcceptReplyResult.NOT_ONLINE
                }
            }
        }
        return InvokeResult(InvokeResult.Type.SUCCESS, result)
    }

    open fun teleportTo(
        fromPlayerName: String,
        toPlayerName: String,
        bypassDomain: Boolean = false
    ): InvokeResult<TeleportResult> {
        var r: TeleportResult = TeleportResult.NOT_ONLINE_FROM
        BukkitUtils.getPlayerIfOnline(fromPlayerName).execute { player ->
            BukkitUtils.getPlayerIfOnline(toPlayerName).execute(
                {
                    player.teleport(it)
                    r = TeleportResult.SUCCESS
                }, {
                    SuiteBukkitUtils.getPlayerRedisOnlineInfo(toPlayerName)?.let {
                        r = if (bypassDomain || WdsjSuiteBukkit.instance.isDomainServer(it.server)) {
                            val teleportResult =
                                getRemoteCallerByCache(it.server).teleportFrom(fromPlayerName, toPlayerName).`object`
                            if (teleportResult == TeleportResult.SUCCESS) {
                                player.connect(it.server)
                            }
                            teleportResult
                        } else {
                            TeleportResult.NOT_IN_SOME_DOMAIN
                        }
                    }
                })
        }
        return InvokeResult(InvokeResult.Type.SUCCESS, r)
    }

    open fun teleportFrom(fromPlayerName: String, toPlayerName: String): InvokeResult<TeleportResult> {
        return BukkitUtils.getPlayerIfOnline(toPlayerName).execute { player ->
            impl.acceptMap[fromPlayerName] = toPlayerName
            InvokeResult(InvokeResult.Type.SUCCESS, TeleportResult.SUCCESS)
        } ?: InvokeResult(InvokeResult.Type.SUCCESS, TeleportResult.NOT_ONLINE_FROM)
    }

    override fun initialize(configSection: ConfigurationSection?) {
        impl = TeleportFunctionImpl(this, config)
        config = ConfigInvoke.invoke(TeleportConfig::class.java, configSection)
        WdsjServerAPI.getPluginManager().run {
            if (config.enableTpa) {
                registerCommand(
                    CommandProxyBuilder.newBuilder(this, CommandTpa(impl)).setName("TPA")
                        .setLabel("tpa")
                )
            }
            if (config.enableTpaHere) {
                registerCommand(
                    CommandProxyBuilder.newBuilder(this, CommandTpahere(impl)).setName("TPAHERE")
                        .setLabel("tpahere")
                )
            }
            registerCommand(
                CommandProxyBuilder.newBuilder(this, CommandTpaccept(impl)).setName("TPACCEPT")
                    .setLabel("tpaccept")
            )
        }
    }

    override fun deInitialize() {
        WdsjServerAPI.getPluginManager().run {
            unregisterCommand("tpa")
            unregisterCommand("tpahere")
            unregisterCommand("tpaccept")
        }
    }


}

data class TeleportConfig(
    var enableTpa: Boolean = true,
    var enableTpaHere: Boolean = true,
    var cooldown: Int = 3000
)

class TeleportFunctionImpl(val channelFunction: SuiteBukkitTeleportFunction, config: TeleportConfig) {

    private val teleportListener: TeleportListener = TeleportListener(this)

    init {
        Bukkit.getPluginManager().registerEvents(teleportListener, WdsjSuiteBukkit.instance)
        println("debug > new TeleportImpl")
    }


    val receiverDataMap: ExpiringMap<UserData, TeleportData> =
        ExpiringMap.builder().expiration(1, TimeUnit.MINUTES).build()

    val receiverMap: ExpiringMap<String, UserData> =
        ExpiringMap.builder().expiration(1, TimeUnit.MINUTES).build()

    val requestMap: ExpiringMap<String, String> =
        ExpiringMap.builder().expiration(1, TimeUnit.MINUTES).build()

    val acceptMap: ExpiringMap<String, String> = ExpiringMap.builder().expiration(15, TimeUnit.SECONDS).build()


    private val tpaCooldown: RedisCooldown by lazy {
        RedisCooldown(
            WdsjServerAPI.getDatabaseFactory().defRedisDbManager,
            "wdsjsuite:${WdsjSuiteBukkit.instance.suiteConfig.serverDomain.name}:tpa",
            config.cooldown
        )
    }

    private val teleportCooldown: RedisCooldown by lazy {
        RedisCooldown(
            WdsjServerAPI.getDatabaseFactory().defRedisDbManager,
            "wdsjsuite:${WdsjSuiteBukkit.instance.suiteConfig.serverDomain.name}:teleport",
            30000
        )
    }

    fun requestTeleport(
        requester: String,
        receiver: String,
        comeHere: Boolean = false,
        cd: Boolean = true
    ): ResultParams<RequestResult> {
        if (requester == receiver) return ResultParams(RequestResult.OWN)
        if (cd) {
            val cooldown = tpaCooldown.get(requester)
            if (cooldown.isCooldown) {
                return ResultParams(RequestResult.COOLDOWN, "time" to (cooldown.time / 1000).toString())
            }
        }

        SuiteBukkitUtils.getPlayerRedisOnlineInfo(receiver).run {
            if (this != null && WdsjSuiteBukkit.instance.isDomainServer(server)) {
                val uData = UserData(requester, receiver)
                val requestResult =
                    channelFunction.getRemoteCallerByCache(server)
                        .requestTeleport(TeleportData(WdsjServerAPI.getServerInfo().name, comeHere), uData).`object`
                if (requestResult == RequestResult.SUCCESS) {
                    requestMap[requester] = receiver
                    if (cd) {
                        tpaCooldown.put(requester)
                    }
                }
                return ResultParams(requestResult ?: RequestResult.ERROR, "receiver" to receiver)
            }
        }
        return ResultParams(RequestResult.NOT_ONLINE)
    }

    fun teleport(fromPlayer: String, toPlayer: String): TeleportResult {
        val r = BukkitUtils.getPlayerIfOnline(fromPlayer).run {
            if (isPresent) {
                channelFunction.teleportTo(fromPlayer, toPlayer)
            } else {
                SuiteBukkitUtils.getPlayerRedisOnlineInfo(fromPlayer)?.let {
                    channelFunction.getRemoteCallerByCache(it.server).teleportTo(fromPlayer, toPlayer)
                }
            }
        }
        r?.let {
            if (it.type == InvokeResult.Type.SUCCESS) {
                return it.`object`
            }
        }
        return TeleportResult.ERROR

    }

    fun acceptTeleport(receiver: String, requester: String?): ResultParams<AcceptReplyResult> {
        if (requester == null) {
            receiverMap[receiver]?.let {
                val tData = receiverDataMap.remove(it)
                receiverMap.remove(receiver)
                acceptMap[it.requester] = receiver
                tData?.let { s ->
                    return ResultParams(
                        channelFunction.getRemoteCallerByCache(s.fromServer)
                            .replyTeleport(tData, it).`object`
                    )
                }
            }
        } else {
            val teleportData = UserData(requester, receiver)
            println("accept $teleportData")
            receiverDataMap[teleportData]?.let {
                println("accept exist $teleportData")
                receiverDataMap.remove(teleportData)
                receiverMap.remove(receiver)
                acceptMap[requester] = receiver
                return ResultParams(
                    channelFunction.getRemoteCallerByCache(it.fromServer)
                        .replyTeleport(it, teleportData).`object`
                )
            }
        }
        return ResultParams(AcceptReplyResult.NOT_EXIST)
    }
    // fun getKey(requester: String, receiver: String) = "$requester@$receiver"
}

class TeleportListener(private val impl: TeleportFunctionImpl) : Listener {

    @EventHandler
    fun on(event: PlayerJoinEvent) {
        impl.acceptMap[event.player.name]?.let { p2 ->
            impl.acceptMap.remove(event.player.name)
            GlobalScope.launch {
                BukkitUtils.getPlayerIfOnline(p2).map {
                    event.player.teleport(it)
                }
            }
        }
    }

    @EventHandler
    fun on(event: PlayerQuitEvent) {
        impl.requestMap[event.player.name]?.let { impl.requestMap.remove(it) }
    }

}


enum class RequestResult {
    SUCCESS,
    NOT_ONLINE,
    OWN,
    TIMEOUT,
    COOLDOWN,
    ERROR,
}

enum class ReceiverResult {
    SUCCESS,
    NOT_ONLINE,
    TIMEOUT,
}

enum class AcceptReplyResult {
    SUCCESS,
    NOT_ONLINE,
    NOT_EXIST,
}

enum class TeleportResult {
    SUCCESS,
    NOT_ONLINE_FROM,
    NOT_ONLINE_TO,
    NOT_IN_SOME_DOMAIN,
    ERROR,
}


data class TeleportData(val fromServer: String, val comeHere: Boolean)

data class UserData(val requester: String, val receiver: String)

