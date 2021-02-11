package net.wdsj.mcserver.wdsjsuite.bukkit

import mc233.cn.wdsjlib.bukkit.utils.BukkitUtils
import net.wdsj.common.simpleconfig.file.YamlConfiguration
import net.wdsj.mcserver.wdsjsuite.bukkit.channel.SuiteBukkitMessageChannel
import net.wdsj.mcserver.wdsjsuite.bukkit.channel.SuiteBukkitSocketChannel
import net.wdsj.mcserver.wdsjsuite.bukkit.command.SuiteBukkitCommand
import net.wdsj.mcserver.wdsjsuite.bukkit.config.SuiteBukkitConfig
import net.wdsj.mcserver.wdsjsuite.bukkit.function.SuiteBukkitFunction
import net.wdsj.mcserver.wdsjsuite.bukkit.function.SuiteBukkitTeleportFunction
import net.wdsj.mcserver.wdsjsuite.bukkit.function.SuiteBukkitWarpFunction
import net.wdsj.mcserver.wdsjsuite.bukkit.listener.PlayerListener
import net.wdsj.mcserver.wdsjsuite.common.WdsjSuiteManager
import net.wdsj.servercore.WdsjServerAPI
import net.wdsj.servercore.common.command.CommandProxyBuilder
import net.wdsj.servercore.database.frame.box.value.bytes.ymal.DatabaseBytesConfigValue
import net.wdsj.servercore.remote.ChannelManager
import net.wdsj.servercore.utils.extensions.invoke
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin
import java.util.logging.Logger

class WdsjSuiteBukkit : JavaPlugin() {

    companion object {
        @JvmStatic
        lateinit var instance: WdsjSuiteBukkit

        lateinit var LOGGER: Logger
    }


    var suiteConfig: SuiteBukkitConfig = SuiteBukkitConfig();
    val domainServers: List<String>
        get() {
            //         println("serverlist: ${suiteConfig.serverDomain.servers}")
            return suiteConfig.serverDomain.servers.map { it.server }
        }

    val suiteBukkitMessageChannel: SuiteBukkitMessageChannel = SuiteBukkitMessageChannel(this)
    val suiteSocketChannel: SuiteBukkitSocketChannel = SuiteBukkitSocketChannel(this)


    private val functionMap: MutableMap<String, SuiteBukkitFunction> = mutableMapOf()

    fun registerFunction(name: String, function: SuiteBukkitFunction) {
        functionMap[name] = function
    }

    override fun onEnable() {
        // Plugin startup logic
        instance = this




        ChannelManager.registerChannelReceive(suiteBukkitMessageChannel)
        ChannelManager.registerChannelReceive(suiteSocketChannel)
        LOGGER = logger


        initFunc()
        init()
/*
        GlobalScope.launch {
            val classLoader = getClassLoader()

            println(classLoader)
            val scanner = Scanner(classLoader)

            val jarFile = JarFile(file)
            for (entry in jarFile.entries()) {
                println(entry.name)
            }
   */
/*         val scanForResources = scanner.scanForResources("lang") { true }
            for (scanForResource in scanForResources) {
                println(scanForResource.location + " " + scanForResource.filename + " " + scanForResource.locationOnDisk)
            }*//*

        }
*/

        Bukkit.getPluginManager().registerEvents(   PlayerListener() , this)

    }

    override fun onDisable() {
        deInit()
        // Plugin shutdown logic
    }

    fun isDomainServer(server: String) = domainServers.contains(server)

    fun init() {
        WdsjServerAPI.getPluginManager().run {
            registerCommand(
                CommandProxyBuilder.newBuilder(this, SuiteBukkitCommand()).setName("WdsjSuite").setLabel("wdsjsuite")
            )
        }
        BukkitUtils.loadLang(this)


        val groupConfig: YamlConfiguration? =
            WdsjServerAPI.getConfigManager().readServerGroup("WdsjSuite", DatabaseBytesConfigValue())


        suiteConfig = (groupConfig ?: return).invoke(SuiteBukkitConfig::class.java)

        WdsjSuiteManager(WdsjServerAPI.getDatabaseFactory().defMySqlDbManager  , suiteConfig.serverDomain.name )

        for (entry in suiteConfig.function.function) {
            if (entry.value.enable){
              val f=   SuiteBukkitModule.getFunction(entry.key)
              if (f !=null){
                  logger.info("初始化功能: ${entry.key}")
                  f.initialize(entry.value.rootConfig)
              }else{
                  logger.info("找不到功能: ${entry.key}")
              }
            }else{
                logger.info("禁用功能: ${entry.key}")
            }
        }

    }

    fun deInit() {
        for (suiteBukkitFunction in SuiteBukkitModule.getAllFunction()) {
            suiteBukkitFunction.deInitialize()
        }
    }

    fun initFunc() {
        SuiteBukkitModule.registerFunction("teleport", SuiteBukkitTeleportFunction())
        SuiteBukkitModule.registerFunction("warp", SuiteBukkitWarpFunction())
    }

}


