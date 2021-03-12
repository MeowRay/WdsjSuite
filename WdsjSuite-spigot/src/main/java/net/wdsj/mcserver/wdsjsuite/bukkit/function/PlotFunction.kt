package net.wdsj.mcserver.wdsjsuite.bukkit.function

import com.plotsquared.core.api.PlotAPI
import net.wdsj.common.simpleconfig.ConfigurationSection
import net.wdsj.mcserver.wdsjsuite.bukkit.WdsjSuiteBukkit
import net.wdsj.mcserver.wdsjsuite.bukkit.command.sub.PlotCommand
import net.wdsj.mcserver.wdsjsuite.bukkit.config.Server
import net.wdsj.mcserver.wdsjsuite.bukkit.utils.LOGGER
import net.wdsj.mcserver.wdsjsuite.common.WdsjSuiteManager
import net.wdsj.servercore.utils.extensions.invoke
import org.bukkit.Bukkit
import net.wdsj.mcserver.wdsjsuite.bukkit.utils.getServers
import net.wdsj.servercore.WdsjServerAPI
import net.wdsj.servercore.common.command.CommandProxyBuilder
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.player.AsyncPlayerChatEvent

/**
 * @author  Arthur
 * @date  2021/3/3 13:07
 * @version 1.0
 */
class PlotFunction : SuiteBukkitFunction {

    var plotServer = false

    var server : Set<Server> = emptySet()

    override fun initialize(configSection: ConfigurationSection?, manager: WdsjSuiteManager) {
        configSection?.invoke(PlotConfig::class.java)!!.let {config->
            val map = config.plotServerGroup.map {
                WdsjSuiteBukkit.instance.suiteConfig.serverDomain.servers.getServers(it)
            }
            val s = mutableSetOf<Server>()
            for (list in map) {
                s.addAll(list)
            }
            server = s
            Bukkit.getPluginManager().registerEvents(PlotListener(this) ,WdsjSuiteBukkit.instance )

            if (Bukkit.getPluginManager().isPluginEnabled("PlotSquared")) {
                val plotAPI = PlotAPI()
                plotServer = true
            }else{
                WdsjServerAPI.getPluginManager().run {
                    registerCommand(
                        CommandProxyBuilder.newBuilder(this, PlotCommand(this@PlotFunction)).setName("Plot").setLabel("plot").setAlias(config.commandAlias.toTypedArray())
                    )
                }
            }


        }

    }

    override fun deInitialize() {

    }

}


data class PlotConfig(

    var plotServerGroup: List<String> = emptyList(),

    var commandAlias : List<String> = listOf("p" ,"p2" )

)


class PlotListener(private val func: PlotFunction) : Listener {

    @EventHandler(priority = EventPriority.HIGH , ignoreCancelled = true)
    fun on(event: AsyncPlayerChatEvent){
        func.LOGGER.info(event.message)
        if (event.message.startsWith("/")) {


        }
    }


}