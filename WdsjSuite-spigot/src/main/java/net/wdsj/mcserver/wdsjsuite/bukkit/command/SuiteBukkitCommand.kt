package net.wdsj.mcserver.wdsjsuite.bukkit.command

import net.wdsj.mcserver.wdsjsuite.common.ServerTeleportData
import mc233.cn.wdsjlib.bukkit.utils.BukkitUtils
import mc233.cn.wdsjlib.bukkit.utils.extensions.toSaveString
import net.wdsj.mcserver.wdsjsuite.bukkit.WdsjSuiteBukkit
import net.wdsj.mcserver.wdsjsuite.common.WdsjSuiteManager
import net.wdsj.servercore.WdsjServerAPI
import net.wdsj.servercore.common.command.WdsjCommand
import net.wdsj.servercore.common.command.anntations.Arg
import net.wdsj.servercore.common.command.anntations.GlobalCommand
import net.wdsj.servercore.common.command.anntations.SubCommand
import net.wdsj.servercore.utils.extensions.execute
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

/**
 * @author  Arthur
 * @date  2020/10/20 18:42
 * @version 1.0
 */
@GlobalCommand(permission = "bungeesuite.admin")
class SuiteBukkitCommand : WdsjCommand<CommandSender> {


    @SubCommand(async = true)
    fun tpLocation(sender: CommandSender, playerName: String, server: String, location: String) {
        BukkitUtils.getPlayerIfOnline(playerName).execute { player ->
            WdsjSuiteBukkit.instance.suiteBukkitMessageChannel.getRemoteCallerByCache(playerName)
                .reqTeleport(
                    player.uniqueId,
                    ServerTeleportData(WdsjServerAPI.getServerInfo().name, server, location, null)
                )
        }
    }


}