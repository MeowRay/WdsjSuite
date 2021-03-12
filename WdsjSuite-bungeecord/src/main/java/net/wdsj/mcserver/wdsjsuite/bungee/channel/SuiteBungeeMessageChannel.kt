package net.wdsj.mcserver.wdsjsuite.bungee.channel

import net.wdsj.mcserver.wdsjsuite.common.ServerTeleportData
import net.wdsj.mcserver.wdsjsuite.common.channel.SuiteMessageChannel
import mc233.cn.wdsjlib.bungee.utils.BungeeUtils
import net.md_5.bungee.api.ProxyServer
import net.wdsj.mcserver.wdsjsuite.bungee.WdsjSuiteBungee
import net.wdsj.servercore.utils.extensions.execute
import java.util.*

/**
 * @author  Arthur
 * @date  2020/10/19 22:11
 * @version 1.0
 */
open class SuiteBungeeMessageChannel(private val manager: WdsjSuiteBungee) : SuiteMessageChannel() {

    override fun reqTeleport(uuid: UUID, data: ServerTeleportData) {
        BungeeUtils.getPlayerIfOnline(uuid).execute { player ->
            ProxyServer.getInstance().getServerInfo(data.targetServer)?.let { serverInfo ->
                if (player.server.info == serverInfo) {
                    getRemoteCallerByCache(player.name).revTeleport(player.uniqueId, data.location )
                } else {
                    player.connect(serverInfo)
                    manager.putPlayerTeleportQueue(player, data)
                }
            }
        }
    }


    override fun getConstructorObjects(): MutableList<Any> = mutableListOf(manager)


}

