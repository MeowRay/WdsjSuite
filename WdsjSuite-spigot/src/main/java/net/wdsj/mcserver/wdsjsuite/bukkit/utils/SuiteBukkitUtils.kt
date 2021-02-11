package net.wdsj.mcserver.wdsjsuite.bukkit.utils

import mc233.cn.wdsjlib.bungee.utils.RedisBungeeUtils

/**
 * @author  Arthur
 * @date  2020/12/4 16:39
 * @version 1.0
 */
class SuiteBukkitUtils {

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