package net.wdsj.mcserver.wdsjsuite.bukkit.command.sub

import mc233.cn.wdsjlib.bukkit.utils.BukkitUtils
import mc233.cn.wdsjlib.bukkit.utils.extensions.sendLangMessage
import mc233.cn.wdsjlib.bukkit.utils.extensions.toSaveString
import net.wdsj.mcserver.wdsjsuite.bukkit.function.HomeFunction
import net.wdsj.servercore.common.command.WdsjCommand
import net.wdsj.servercore.common.command.anntations.SubCommand
import net.wdsj.servercore.utils.extensions.uid
import org.bukkit.entity.Player

/**
 * @author  Arthur
 * @date  2021/2/4 21:49
 * @version 1.0
 */
class HomeCommand(val impl: HomeFunction) : WdsjCommand<Player> {


    @SubCommand(async = true)
    fun setHome(player: Player, name: String = "1") {
        val entity =
            impl.service.setHome(player.uid!!, name, BukkitUtils.getServerDetail().name, player.location.toSaveString())
        player.sendLangMessage(entity)
    }

    @SubCommand(async = true)
    fun delHome(player: Player, name: String = "1") {
        impl.service.delHome(player.uid!!, name)
    }

    @SubCommand(async = true)
    fun home(player: Player, name: String = "1") {
        impl.backHome(player , name )
    }

}