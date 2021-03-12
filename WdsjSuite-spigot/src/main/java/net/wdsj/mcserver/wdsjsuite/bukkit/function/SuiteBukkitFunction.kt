package net.wdsj.mcserver.wdsjsuite.bukkit.function

import net.wdsj.common.simpleconfig.ConfigurationSection
import net.wdsj.mcserver.wdsjsuite.common.WdsjSuiteManager

/**
 * @author  Arthur
 * @date  2020/12/3 22:00
 * @version 1.0
 */
interface SuiteBukkitFunction {

    fun initialize(configSection: ConfigurationSection? , manager: WdsjSuiteManager)

    fun deInitialize()

}

