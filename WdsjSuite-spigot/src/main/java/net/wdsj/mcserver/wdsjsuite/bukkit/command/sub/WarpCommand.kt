package net.wdsj.mcserver.wdsjsuite.bukkit.command.sub

import mc233.cn.wdsjlib.bukkit.utils.BukkitUtils
import mc233.cn.wdsjlib.bukkit.utils.extensions.toSaveString
import net.wdsj.mcserver.wdsjsuite.bukkit.WdsjSuiteBukkit
import net.wdsj.mcserver.wdsjsuite.common.ServerTeleportData
import net.wdsj.mcserver.wdsjsuite.common.WdsjSuiteManager
import net.wdsj.mcserver.wdsjsuite.common.service.WarpService
import net.wdsj.servercore.WdsjServerAPI
import net.wdsj.servercore.common.command.WdsjCommand
import net.wdsj.servercore.common.command.anntations.Arg
import net.wdsj.servercore.common.command.anntations.GlobalCommand
import net.wdsj.servercore.common.command.anntations.SubCommand
import net.wdsj.servercore.utils.extensions.execute
import org.bukkit.Location
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

/**
 * @author  Arthur
 * @date  2021/1/21 20:01
 * @version 1.0
 */

@GlobalCommand(permission = "bungeesuite", subCmdPerm = true )
class WarpCommand(private val warpService: WarpService) : WdsjCommand<CommandSender> {

    @SubCommand(async = true)
    fun warp(sender: CommandSender,key: String , @Arg(required = false) playerName: String?) {
        BukkitUtils.getPlayerIfOnline(playerName).execute { player ->
            warpService.let {
                val warpEntity = it.getServerLocation( key)
                if (warpEntity != null) {
                    if (warpEntity.location.isNotBlank()) {
                        WdsjSuiteBukkit.instance.suiteBukkitMessageChannel.getRemoteCallerByCache(playerName)
                            .reqTeleport(
                                player.uniqueId,
                                ServerTeleportData(
                                    WdsjServerAPI.getServerInfo().name,
                                    warpEntity.server,
                                    warpEntity.location,
                                )
                            );
                        sendMessage(sender, "传送中...")
                    }
                } else {
                    sendMessage(sender, "不存在 $key")
                }
            }
        }
    }

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
    fun setWarp(sender: Player, warpName: String) {
        warpService.saveServerLocation(
            warpName,
            WdsjServerAPI.getServerInfo().name,
            sender.location.toSaveString()
        ).let {
            sendMessage(sender, "保存成功 > $it")
        }
    }


    @SubCommand(async = true)
    fun test(sender: Player) {
        val serverInfo = WdsjServerAPI.getServerInfo()
        if (serverInfo == null) {
            sendMessage(sender, "isnull")
        } else
            sendMessage(sender, serverInfo.name)
    }

}