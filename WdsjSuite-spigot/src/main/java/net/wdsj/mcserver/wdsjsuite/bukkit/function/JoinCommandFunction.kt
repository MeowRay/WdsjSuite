package net.wdsj.mcserver.wdsjsuite.bukkit.function

import com.github.benmanes.caffeine.cache.Caffeine
import mc233.cn.wdsjlib.bukkit.utils.BukkitUtils
import meow.anno.NoArg
import net.jodah.expiringmap.ExpiringMap
import net.wdsj.common.simpleconfig.ConfigurationSection
import net.wdsj.mcserver.wdsjsuite.common.WdsjSuiteManager
import java.util.*
import java.util.concurrent.TimeUnit

/**
 * @author  Arthur
 * @date  2021/3/3 21:41
 * @version 1.0
 */
class JoinCommandFunction : SuiteBukkitFunction {

    val pendingMap: ExpiringMap<UUID, List<String>> = ExpiringMap.builder().expiration(10, TimeUnit.SECONDS).build()

    override fun initialize(configSection: ConfigurationSection?, manager: WdsjSuiteManager) {
        JoinRemote.registerRunnable("JoinCommand") { p ->
            pendingMap[p.uniqueId]?.let {
                synchronized(p) {
                    pendingMap.remove(p)
                    BukkitUtils.executeCommand(p, *it.toTypedArray())
                }

            }
        }
    }

    override fun deInitialize() {
        JoinRemote.unregisterRunnable("JoinCommand")
    }


    fun setCommand(uuid: UUID ,  commands: List<String>){
       pendingMap[uuid] = commands
    }
}

@NoArg
open class JoinCommandChannel(private val func: JoinCommandFunction) :
    ChannelFunction<JoinCommandFunction>("JoinCommand") {

    open fun setCommand(uuid: UUID,    commands: List<String>) {
        func.setCommand(uuid , commands)
    }


}