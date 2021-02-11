package net.wdsj.mcserver.wdsjsuite.bungee

import net.wdsj.mcserver.wdsjsuite.common.ServerTeleportData
import com.github.benmanes.caffeine.cache.Cache
import com.github.benmanes.caffeine.cache.Caffeine
import net.md_5.bungee.api.ProxyServer
import net.md_5.bungee.api.config.ServerInfo
import net.md_5.bungee.api.connection.ProxiedPlayer
import net.md_5.bungee.api.plugin.Plugin
import net.wdsj.mcserver.wdsjsuite.bungee.channel.SuiteBungeeMessageChannel
import net.wdsj.mcserver.wdsjsuite.bungee.listener.PlayerListener
import net.wdsj.servercore.remote.ChannelManager
import java.util.concurrent.TimeUnit

class WdsjSuiteBungee : Plugin() {

    companion object {
        @JvmStatic
        lateinit var instance: WdsjSuiteBungee
    }

    private val serverDomain: MutableMap<ServerInfo, String> = mutableMapOf()


    private val teleportQueue: Cache<ProxiedPlayer, ServerTeleportData> =  Caffeine.newBuilder().expireAfterWrite(30, TimeUnit.SECONDS).build()
    val suiteMessageChannel: SuiteBungeeMessageChannel = SuiteBungeeMessageChannel(this);


    fun putPlayerTeleportQueue(player: ProxiedPlayer, serverTeleportData: ServerTeleportData) {
        teleportQueue.put(player, serverTeleportData)
    }

    fun getPlayerTeleportQueue(player: ProxiedPlayer): ServerTeleportData? = teleportQueue.getIfPresent(player);


    fun registerServerDomain(server: String, domain: String) {
        ProxyServer.getInstance().getServerInfo(server)?.let {
            serverDomain[it] = domain
        }
    }

    override fun onEnable() {
        instance = this
        // Plugin startup logic
        ProxyServer.getInstance().pluginManager.registerListener(this, PlayerListener(this))
        ChannelManager.registerChannelReceive(suiteMessageChannel)

    }

    override fun onDisable() {
        // Plugin shutdown logic
    }
}