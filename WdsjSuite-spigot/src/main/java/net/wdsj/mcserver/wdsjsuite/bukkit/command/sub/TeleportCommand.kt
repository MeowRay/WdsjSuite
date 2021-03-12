package net.wdsj.mcserver.wdsjsuite.bukkit.command.sub

import mc233.cn.wdsjlib.bukkit.utils.extensions.sendLangMessage
import net.wdsj.mcserver.wdsjsuite.bukkit.function.TeleportFunction
import net.wdsj.servercore.common.command.WdsjCommand
import net.wdsj.servercore.common.command.anntations.Arg
import net.wdsj.servercore.common.command.anntations.GlobalCommand
import net.wdsj.servercore.common.command.anntations.SubCommand
import org.bukkit.entity.Player

/**
 * @author  Arthur
 * @date  2020/12/4 14:31
 * @version 1.0
 */


@GlobalCommand(permission = "wdsjsuite", subCmdPerm = true)
class CommandTpa(val impl : TeleportFunction) : WdsjCommand<Player>  {

    @SubCommand(permission = "tpa", async = true)
    fun main(sender: Player, @Arg(name = "玩家名") targetPlayer: String) {
        val requestTeleport = impl.requestTeleport(sender.name, targetPlayer)
        sender.sendLangMessage(requestTeleport.result , *requestTeleport.params)
     //   sendMessage(sender, requestTeleport.getLangKeyAsRequest())
    }

}

@GlobalCommand(permission = "wdsjsuite", subCmdPerm = true)
class CommandTpaccept(val impl : TeleportFunction) : WdsjCommand<Player>  {

    @SubCommand(permission = "tpaccept")
    fun main(sender: Player, @Arg(name = "玩家名", required = false) targetPlayer: String?) {
        sender.sendLangMessage(impl.acceptTeleport(sender.name, targetPlayer).result)
    }

}

@GlobalCommand(permission = "wdsjsuite", subCmdPerm = true)
class CommandTpahere(val impl : TeleportFunction) : WdsjCommand<Player>  {

    @SubCommand(permission = "tpahere", async = true)
    fun main(sender: Player, @Arg(name = "玩家名") targetPlayer: String) {
        val requestTeleport = impl.requestTeleport(sender.name, targetPlayer , true)
        sender.sendLangMessage(requestTeleport.result , *requestTeleport.params)
    }

}
