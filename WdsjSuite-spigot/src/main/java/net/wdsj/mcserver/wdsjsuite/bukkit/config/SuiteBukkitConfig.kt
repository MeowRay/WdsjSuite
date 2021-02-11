package net.wdsj.mcserver.wdsjsuite.bukkit.config

import net.wdsj.common.simpleconfig.ConfigurationSection
import net.wdsj.servercore.config.invoke.annotation.ListInvoke
import net.wdsj.servercore.config.invoke.annotation.SubConfigInvoke

/**
 * @author  Arthur
 * @date  2020/10/20 19:28
 * @version 1.0
 */

data class SuiteBukkitConfig(

    @SubConfigInvoke
    var serverDomain: ServerDomain = ServerDomain(),

    var function: Function = Function()


)


data class ServerDomain(

    var name: String = "global",

    @ListInvoke
    var servers: List<Server> = emptyList()



)

data class Server(var server: String = "", var alias: String = "", var group: String = "")

data class Function(

    var function: Map<String,FunctionSub> = emptyMap()

)

data class FunctionSub(
    var enable: Boolean = false,
    var rootConfig: ConfigurationSection? = null
)