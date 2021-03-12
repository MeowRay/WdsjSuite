package net.wdsj.mcserver.wdsjsuite.common

import com.github.benmanes.caffeine.cache.Cache
import com.github.benmanes.caffeine.cache.Caffeine
import net.wdsj.mcserver.wdsjsuite.common.channel.SuiteMessageChannel
import net.wdsj.mcserver.wdsjsuite.common.dao.WarpDao
import net.wdsj.mcserver.wdsjsuite.common.dao.entity.WarpEntity
import net.wdsj.mcserver.wdsjsuite.common.dao.impl.HomeDaoImpl
import net.wdsj.mcserver.wdsjsuite.common.dao.impl.ResidenceDaoImpl
import net.wdsj.mcserver.wdsjsuite.common.dao.impl.WarpDaoImpl
import net.wdsj.mcserver.wdsjsuite.common.service.HomeService
import net.wdsj.mcserver.wdsjsuite.common.service.ResidenceService
import net.wdsj.mcserver.wdsjsuite.common.service.TeleportService
import net.wdsj.mcserver.wdsjsuite.common.service.WarpService
import net.wdsj.mcserver.wdsjsuite.common.service.impl.HomeServiceImpl
import net.wdsj.mcserver.wdsjsuite.common.service.impl.ResidenceServiceImpl
import net.wdsj.mcserver.wdsjsuite.common.service.impl.TeleportServiceImpl
import net.wdsj.mcserver.wdsjsuite.common.service.impl.WarpServiceImpl
import net.wdsj.servercore.database.DatabaseManager
import java.lang.reflect.Proxy
import java.util.concurrent.TimeUnit

/**
 * @author  Arthur
 * @date  2020/10/19 2:08
 * @version 1.0
 */

val TABLE_PREFIX = "wdsjsuite_"

class WdsjSuiteManager(databaseManager: DatabaseManager, domain: String , suiteMessageChannel: SuiteMessageChannel ) {


    init {
        instance = this
    }

    companion object {
        lateinit var instance: WdsjSuiteManager
    }

    val warpService: WarpService = WarpServiceImpl(WarpDaoImpl(databaseManager, domain))

    val homeService: HomeService = HomeServiceImpl(HomeDaoImpl(databaseManager, domain))

    val residenceService : ResidenceService = ResidenceServiceImpl(ResidenceDaoImpl(databaseManager, domain))

    val teleportService : TeleportService = TeleportServiceImpl(suiteMessageChannel)


}