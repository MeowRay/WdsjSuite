package net.wdsj.mcserver.wdsjsuite.bukkit.listener

import hu.montlikadani.tablist.bukkit.API.TabListAPI
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent

/**
 * @author Arthur
 * @version 1.0
 * @date 2020/12/5 22:57
 */
class PlayerListener : Listener {


    @EventHandler
    fun on(event: PlayerJoinEvent){
        TabListAPI.createFakePlayer(event.player,"test a player")
    }

}