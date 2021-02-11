package net.wdsj.mcserver.wdsjsuite.bukkit.command.sub

import mc233.cn.wdsjlib.bukkit.utils.extensions.sendLangMessage
import net.wdsj.mcserver.wdsjsuite.bukkit.SuiteBukkitModule
import net.wdsj.mcserver.wdsjsuite.bukkit.WdsjSuiteBukkit
import net.wdsj.mcserver.wdsjsuite.bukkit.function.SuiteBukkitTeleportFunction
import net.wdsj.mcserver.wdsjsuite.bukkit.function.TeleportFunctionImpl
import net.wdsj.mcserver.wdsjsuite.bukkit.utils.getMessageAsAcceptReply
import net.wdsj.mcserver.wdsjsuite.bukkit.utils.getLangKeyAsRequest
import net.wdsj.servercore.common.command.WdsjCommand
import net.wdsj.servercore.common.command.anntations.Arg
import net.wdsj.servercore.common.command.anntations.GlobalCommand
import net.wdsj.servercore.common.command.anntations.SubCommand
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

/**
 * @author  Arthur
 * @date  2020/12/4 14:31
 * @version 1.0
 */


@GlobalCommand(permission = "wdsjsuite", subCmdPerm = true)
class CommandTpa(val impl : TeleportFunctionImpl) : WdsjCommand<Player>  {

    @SubCommand(permission = "tpa", async = true)
    fun main(sender: Player, @Arg(name = "玩家名") targetPlayer: String) {
        val requestTeleport = impl.requestTeleport(sender.name, targetPlayer)
        sender.sendLangMessage(requestTeleport.getLangKeyAsRequest() , *requestTeleport.params)
     //   sendMessage(sender, requestTeleport.getLangKeyAsRequest())
    }

}

@GlobalCommand(permission = "wdsjsuite", subCmdPerm = true)
class CommandTpaccept(val impl : TeleportFunctionImpl) : WdsjCommand<Player>  {

    @SubCommand(permission = "tpaccept")
    fun main(sender: Player, @Arg(name = "玩家名", required = false) targetPlayer: String?) {
        sender.sendLangMessage(impl.acceptTeleport(sender.name, targetPlayer).getMessageAsAcceptReply())
    }

}

@GlobalCommand(permission = "wdsjsuite", subCmdPerm = true)
class CommandTpahere(val impl : TeleportFunctionImpl) : WdsjCommand<Player>  {

    @SubCommand(permission = "tpahere", async = true)
    fun main(sender: Player, @Arg(name = "玩家名") targetPlayer: String) {
        val requestTeleport = impl.requestTeleport(sender.name, targetPlayer , true)
        sender.sendLangMessage(requestTeleport.getLangKeyAsRequest() , *requestTeleport.params)
    }

}
