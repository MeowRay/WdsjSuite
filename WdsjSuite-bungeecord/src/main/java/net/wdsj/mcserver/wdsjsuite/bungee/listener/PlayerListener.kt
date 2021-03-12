package net.wdsj.mcserver.wdsjsuite.bungee.listener

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import net.md_5.bungee.api.event.ServerConnectedEvent
import net.md_5.bungee.api.plugin.Listener
import net.md_5.bungee.event.EventHandler
import net.wdsj.mcserver.wdsjsuite.bungee.WdsjSuiteBungee

/**
 * @author  Arthur
 * @date  2020/10/19 22:23
 * @version 1.0
 */
class PlayerListener(val wdsjSuiteBungee: WdsjSuiteBungee) : Listener {

    @EventHandler
    fun on(event: ServerConnectedEvent) {
        val playerTeleportQueue = wdsjSuiteBungee.getPlayerTeleportQueue(event.player)
        playerTeleportQueue?.let {
            if (event.server.info.name == it.targetServer) {
                GlobalScope.launch {
                    delay(100)
                    wdsjSuiteBungee.suiteMessageChannel.getRemoteCaller(event.player.name).revTeleport(
                        event.player.uniqueId,
                        it.location,
                    )
                }
            }
        }
    }


}