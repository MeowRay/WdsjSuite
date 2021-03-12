package net.wdsj.mcserver.wdsjsuite.bukkit.function

import org.bukkit.entity.Player

/**
 * @author  Arthur
 * @date  2021/3/1 22:06
 * @version 1.0
 */
object JoinRemote {

    private val runnableMap = mutableMapOf<String, JoinRunnable>()

    fun playerJoin(player: Player) {
        for (entry in runnableMap.entries) {
            try {
                entry.value(player)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun registerRunnable(key: String, runnable: JoinRunnable) {
        runnableMap[key] = runnable
    }

    fun unregisterRunnable(key: String) {
        runnableMap.remove(key)
    }



}

public typealias JoinRunnable = (Player) -> Unit



