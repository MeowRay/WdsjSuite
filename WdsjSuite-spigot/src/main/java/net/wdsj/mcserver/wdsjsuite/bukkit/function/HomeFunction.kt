package net.wdsj.mcserver.wdsjsuite.bukkit.function

import net.wdsj.common.simpleconfig.ConfigurationSection
import net.wdsj.mcserver.langutils.lang.LangKey
import net.wdsj.mcserver.wdsjsuite.bukkit.WdsjSuiteBukkit
import net.wdsj.mcserver.wdsjsuite.bukkit.command.sub.HomeCommand
import net.wdsj.mcserver.wdsjsuite.common.WdsjSuiteManager
import net.wdsj.mcserver.wdsjsuite.common.entity.LocationEntity
import net.wdsj.mcserver.wdsjsuite.common.service.HomeService
import net.wdsj.mcserver.wdsjsuite.common.util.SuiteLang
import net.wdsj.servercore.WdsjServerAPI
import net.wdsj.servercore.common.ResultParams
import net.wdsj.servercore.common.command.CommandProxyBuilder
import net.wdsj.servercore.utils.extensions.uid
import org.bukkit.entity.Player

/**
 * @author Arthur
 * @version 1.0
 * @date 2021/1/21 20:10
 */
class HomeFunction : SuiteBukkitFunction {

     lateinit var service: HomeService

    override fun initialize(configSection: ConfigurationSection?, manager: WdsjSuiteManager) {
        service = manager.homeService
        WdsjServerAPI.getPluginManager().run {
            registerCommand(
                CommandProxyBuilder.newBuilder(this, HomeCommand(this@HomeFunction)).setName("warp")
                    .setLabel("warp")
            )
        }
    }

    override fun deInitialize() {
        WdsjServerAPI.getPluginManager().run {
            unregisterCommand("warp")
        }
    }


    fun backHome(player: Player, key : String) : ResultParams<BackHomeResult> {
        val home = service.getHome(player.uid!!, key, false)
        return if (home != null) {
            WdsjSuiteBukkit.instance.manager.teleportService.teleport(player.uniqueId, WdsjServerAPI.getServerInfo().name, home.server , LocationEntity.fromLocationData(home.location))
            ResultParams(BackHomeResult.SUCCESS, "home_id" to home.homeId)
        }else{
            ResultParams(BackHomeResult.NOT_EXIST , "home_id" to key)
        }
    }


}

enum class BackHomeResult : LangKey{
    SUCCESS,
    NOT_EXIST,
    ;

    override fun getLocalKey(): String {
        return SuiteLang.formatKeyString("home.back.${name}")
    }
}