package net.wdsj.mcserver.wdsjsuite.bukkit

import net.wdsj.mcserver.wdsjsuite.bukkit.function.SuiteBukkitFunction

/**
 * @author Arthur
 * @version 1.0
 * @date 2020/12/7 20:43
 */
object SuiteBukkitModule {

    private val funcMap: MutableMap<String,  SuiteBukkitFunction> = mutableMapOf()

    fun registerFunction(name: String, function: SuiteBukkitFunction) {
        funcMap["func:$name"] = function
    }

    fun registerFunction(function: SuiteBukkitFunction) {
        funcMap[SuiteBukkitFunction::class.java.name] = function
    }

    fun unregisterFunction(name: String) {
        funcMap.remove(name)
    }

    fun getAllFunction(): Collection<SuiteBukkitFunction> {
        return funcMap.values
        //   return funcMap.map { it.value  }
    }

    fun < C : SuiteBukkitFunction> getFunction(c: Class<C>): C {
       return funcMap[c.name] as C
    }

    fun getFunction(key: String) = funcMap["func:$key"]

}