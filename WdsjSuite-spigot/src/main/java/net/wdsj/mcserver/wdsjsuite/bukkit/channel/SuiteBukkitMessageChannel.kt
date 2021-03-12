package net.wdsj.mcserver.wdsjsuite.bukkit.channel

import net.wdsj.mcserver.wdsjsuite.common.channel.SuiteMessageChannel
import mc233.cn.wdsjlib.bukkit.utils.BukkitUtils
import mc233.cn.wdsjlib.bukkit.utils.extensions.toLocation
import net.wdsj.mcserver.wdsjsuite.bukkit.WdsjSuiteBukkit
import net.wdsj.mcserver.wdsjsuite.common.entity.FunctionDataEntity
import net.wdsj.servercore.utils.extensions.execute
import org.bukkit.Bukkit
import java.util.*

/**
 * @author  Arthur
 * @date  2020/10/19 21:19
 * @version 1.0
 */
open class SuiteBukkitMessageChannel @JvmOverloads constructor(var suiteBukkit: WdsjSuiteBukkit? = null)  : SuiteMessageChannel() {



    override fun revTeleport(uuid: UUID, location: String ) {
        BukkitUtils.getPlayerIfOnline(uuid).execute {
            val toBukkitLocation = location.toLocation()
            it.teleport(toBukkitLocation)
        }
    }

    override fun revExecCommands(uuid: UUID, commands: Array<String>) {
        BukkitUtils.executeCommand(Bukkit.getPlayer(uuid), *commands)
    }


}