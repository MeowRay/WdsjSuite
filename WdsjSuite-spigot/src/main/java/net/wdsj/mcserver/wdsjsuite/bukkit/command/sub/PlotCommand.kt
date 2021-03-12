package net.wdsj.mcserver.wdsjsuite.bukkit.command.sub

import net.wdsj.mcserver.wdsjsuite.bukkit.function.PlotFunction
import net.wdsj.servercore.WdsjPluginManager
import net.wdsj.servercore.common.command.WdsjCommand
import net.wdsj.servercore.common.command.anntations.SubCommand
import net.wdsj.servercore.utils.extensions.uid
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

/**
 * @author  Arthur
 * @date  2021/3/3 13:19
 * @version 1.0
 */
class PlotCommand(val impl: PlotFunction) :WdsjCommand<CommandSender> {

    @SubCommand(async = true)
    fun main(player: Player){

    }


    @SubCommand(async = true)
    fun h(player: Player, name: String = "1") {

    }


}