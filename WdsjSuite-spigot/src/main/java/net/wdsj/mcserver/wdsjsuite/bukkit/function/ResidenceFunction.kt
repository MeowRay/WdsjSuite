package net.wdsj.mcserver.wdsjsuite.bukkit.function

import com.bekvon.bukkit.residence.Residence
import com.bekvon.bukkit.residence.commands.suitetp
import com.bekvon.bukkit.residence.event.ResidenceCommandEvent
import com.bekvon.bukkit.residence.event.ResidenceCreationEvent
import com.bekvon.bukkit.residence.event.ResidenceDeleteEvent
import meow.anno.NoArg
import net.jodah.expiringmap.ExpiringMap
import net.wdsj.common.simpleconfig.ConfigurationSection
import net.wdsj.mcserver.wdsjsuite.bukkit.SuiteBukkitModule
import net.wdsj.mcserver.wdsjsuite.bukkit.WdsjSuiteBukkit
import net.wdsj.mcserver.wdsjsuite.bukkit.WdsjSuiteBukkit.Companion.LOGGER
import net.wdsj.mcserver.wdsjsuite.common.WdsjSuiteManager
import net.wdsj.mcserver.wdsjsuite.common.service.ResidenceService
import net.wdsj.servercore.WdsjServerAPI
import net.wdsj.servercore.remote.InvokeResult
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.HandlerList
import org.bukkit.event.Listener
import java.util.*
import java.util.concurrent.TimeUnit

/**
 * @author  Arthur
 * @date  2021/3/1 17:34
 * @version 1.0
 */
class ResidenceFunction : SuiteBukkitFunction {

    lateinit var service: ResidenceService


    private val listener = ResidenceFunctionListener(this)

    var channel = ResidenceChannel(this)


    override fun initialize(configSection: ConfigurationSection?, manager: WdsjSuiteManager) {
        suitetp.function = this
        service = manager.residenceService
        if (Bukkit.getPluginManager().isPluginEnabled("Residence")) {
            Bukkit.getPluginManager().registerEvents(listener, WdsjSuiteBukkit.instance)

        } else {
            LOGGER.warning("Can't found 'Residence' Plugin , ResidenceFunction will be disabled")

        }
    }

    override fun deInitialize() {
        suitetp.function = null
        HandlerList.unregisterAll(listener)
    }

}

class ResidenceFunctionListener(private val func: ResidenceFunction) : Listener {

    @EventHandler(priority = EventPriority.MONITOR)
    fun on(event: ResidenceCreationEvent) {
        if (event.isCancelled) return
        func.service.addResidence(event.residenceName, WdsjServerAPI.getServerInfo().name)
    }

    @EventHandler(priority = EventPriority.MONITOR)
    fun on(event: ResidenceDeleteEvent) {
        if (event.isCancelled) return
        func.service.delResidence(event.residence.name)
    }

    @EventHandler
    fun on(event: ResidenceCommandEvent) {
        if (event.args[0] == "tp") {
            event.args[0] = "suitetp"
        }
    }

}

@NoArg
open class ResidenceChannel(private val func: ResidenceFunction) : ChannelFunction<ResidenceChannel>("Residence") {

    open fun reqTp(uuid: UUID, resName: String): InvokeResult<Boolean> {
        SuiteBukkitModule.getFunction(JoinCommandFunction::class.java).setCommand(uuid, listOf("player:res tp $resName"))
        return InvokeResult(InvokeResult.Type.SUCCESS, true)
    }

}
