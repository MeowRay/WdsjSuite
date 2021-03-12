package net.wdsj.mcserver.wdsjsuite.bukkit.function

import net.wdsj.common.simpleconfig.ConfigurationSection
import net.wdsj.mcserver.wdsjsuite.bukkit.command.sub.WarpCommand
import net.wdsj.mcserver.wdsjsuite.common.WdsjSuiteManager
import net.wdsj.servercore.WdsjServerAPI
import net.wdsj.servercore.common.command.CommandProxyBuilder

/**
 * @author Arthur
 * @version 1.0
 * @date 2021/1/21 20:10
 */
class WarpFunction :SuiteBukkitFunction {


    override fun initialize(configSection: ConfigurationSection?, manager: WdsjSuiteManager) {
        WdsjServerAPI.getPluginManager().run {
            registerCommand(
                CommandProxyBuilder.newBuilder(this, WarpCommand(manager.warpService)).setName("warp")
                    .setLabel("warp")
            )
        }
    }

    override fun deInitialize() {
        WdsjServerAPI.getPluginManager().run {
            unregisterCommand("warp"
            )
        }
    }
}