package net.wdsj.mcserver.wdsjsuite.bukkit.command

import com.bekvon.bukkit.residence.Residence
import net.wdsj.mcserver.wdsjsuite.common.ServerTeleportData
import mc233.cn.wdsjlib.bukkit.utils.BukkitUtils
import mc233.cn.wdsjlib.bukkit.utils.extensions.toSaveString
import net.wdsj.mcserver.wdsjsuite.bukkit.SuiteBukkitModule
import net.wdsj.mcserver.wdsjsuite.bukkit.WdsjSuiteBukkit
import net.wdsj.mcserver.wdsjsuite.bukkit.function.ResidenceFunction
import net.wdsj.mcserver.wdsjsuite.common.WdsjSuiteManager
import net.wdsj.servercore.WdsjServerAPI
import net.wdsj.servercore.common.command.WdsjCommand
import net.wdsj.servercore.common.command.anntations.Arg
import net.wdsj.servercore.common.command.anntations.GlobalCommand
import net.wdsj.servercore.common.command.anntations.SubCommand
import net.wdsj.servercore.utils.extensions.execute
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import java.lang.Exception

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
                    ServerTeleportData(WdsjServerAPI.getServerInfo().name, server, location)
                )
        }
    }

    @SubCommand(async = true)
    fun resConvert(sender: CommandSender) {
        try {
            val service = SuiteBukkitModule.getFunction(ResidenceFunction::class.java).service

            for (entry in Residence.getInstance().residenceManager.residences.entries) {
                service.addResidence(entry.key, WdsjServerAPI.getServerInfo().name)
            }

        } catch (e: Exception) {
            sender.sendMessage(e.localizedMessage)
            e.printStackTrace()
        }

    }


}