package net.wdsj.mcserver.wdsjsuite.bukkit.listener

import hu.montlikadani.tablist.bukkit.API.TabListAPI
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import mc233.cn.wdsjlib.bukkit.utils.BukkitUtils
import net.wdsj.mcserver.wdsjsuite.bukkit.WdsjSuiteBukkit
import net.wdsj.mcserver.wdsjsuite.bukkit.function.JoinRemote
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import javax.xml.bind.JAXBElement

/**
 * @author Arthur
 * @version 1.0
 * @date 2020/12/5 22:57
 */
object PlayerListener : Listener {


    @EventHandler
    fun on(event: PlayerJoinEvent){
        BukkitUtils.runOptimalASyncOrSync {
            JoinRemote.playerJoin(event.player)

        }
    //    TabListAPI.createFakePlayer(event.player,"test a player")
    }

}