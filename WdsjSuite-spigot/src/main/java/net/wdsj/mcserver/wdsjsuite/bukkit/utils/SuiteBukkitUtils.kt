package net.wdsj.mcserver.wdsjsuite.bukkit.utils

import mc233.cn.wdsjlib.bungee.utils.RedisBungeeUtils
import net.wdsj.mcserver.wdsjsuite.bukkit.config.Server
import net.wdsj.mcserver.wdsjsuite.bukkit.function.SuiteBukkitFunction
import net.wdsj.servercore.WdsjServerAPI
import net.wdsj.servercore.utils.extensions.LazySpawner
import java.util.logging.Logger

/**
 * @author  Arthur
 * @date  2020/12/4 16:39
 * @version 1.0
 */
open class SuiteBukkitUtils {

    companion object {

        fun getPlayerRedisOnlineInfo(playerName: String): RedisBungeeUtils.BungeePlayerInfo? {
            RedisBungeeUtils.getPlayerInfo(playerName)?.let { info ->
                info.server?.let {
                    return info
                }
            }
            return null
        }

    }





}


val SuiteBukkitFunction.LOGGER by LazySpawner<SuiteBukkitFunction, Logger>(init = {
    WdsjServerAPI.getLogger("SuiteFunction-${it.javaClass.simpleName.split(".").last()}")
})

fun Collection<Server>.getServers(expression: String): List<Server> {
    val str = expression.split(Regex(":"), 2)

    val key = if (str.size == 1) "name" else str[0].toLowerCase()
    val value = if (str.size == 1) str else str[1]
    when (key) {
        "group" -> {
            return filter {
                for (s in it.group) {
                    if (s == value) return@filter true
                }
                return@filter false
            }
        }
        "name" -> {
            return filter {
                return@filter it.server == value
            }
        }
        "alias" -> {
            return filter {
                return@filter it.alias == value
            }
        }
        else -> {
            return emptyList()
        }

    }
}
