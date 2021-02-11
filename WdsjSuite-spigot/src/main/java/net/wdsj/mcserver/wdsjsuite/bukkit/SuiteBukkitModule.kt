package net.wdsj.mcserver.wdsjsuite.bukkit

import net.wdsj.mcserver.wdsjsuite.bukkit.function.SuiteBukkitFunction

/**
 * @author Arthur
 * @version 1.0
 * @date 2020/12/7 20:43
 */
object SuiteBukkitModule {

    private val funcMap: MutableMap<String, SuiteBukkitFunction> = mutableMapOf()

    fun registerFunction(name: String, function: SuiteBukkitFunction) {
        funcMap["func:$name"] = function
    }

    fun unregisterFunction(name: String) {
        funcMap.remove(name)
    }

    fun getAllFunction(): Collection<SuiteBukkitFunction>{
        return funcMap.values
    //   return funcMap.map { it.value  }
    }

    fun getFunction(key: String) = funcMap[key]


}