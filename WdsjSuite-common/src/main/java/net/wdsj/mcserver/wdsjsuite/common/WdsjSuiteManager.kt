package net.wdsj.mcserver.wdsjsuite.common

import com.github.benmanes.caffeine.cache.Cache
import com.github.benmanes.caffeine.cache.Caffeine
import net.wdsj.mcserver.wdsjsuite.common.dao.WarpService
import net.wdsj.mcserver.wdsjsuite.common.dao.entity.WarpEntity
import net.wdsj.mcserver.wdsjsuite.common.dao.impl.WarpServiceImpl
import net.wdsj.servercore.cache.CacheHibernateMethodInvoke
import net.wdsj.servercore.database.DatabaseManager
import java.util.concurrent.TimeUnit

/**
 * @author  Arthur
 * @date  2020/10/19 2:08
 * @version 1.0
 */

val TABLE_PREFIX = "wdsjsuite_"

class WdsjSuiteManager(databaseManager: DatabaseManager, domain: String) {


    init {
        instance = this
    }

    companion object {
        lateinit var instance: WdsjSuiteManager
    }

    private val locationCache: Cache<String, WarpEntity> =
        Caffeine.newBuilder().expireAfterWrite(1, TimeUnit.DAYS).build();

    private val warpService: WarpService = WarpServiceImpl(databaseManager, domain);


    fun getServerLocation(key: String, refresh: Boolean = false): WarpEntity? {
        if (refresh) {
            locationCache.invalidate(key)
        }
        return locationCache.get(key) {
            warpService.getLocation(it) ?: WarpEntity()
        }
    }

    fun saveServerLocation(key: String, server: String, location: String): WarpEntity {
        val saveLocation = warpService.saveLocation(key, server, location)
        locationCache.put(key, saveLocation)
        return saveLocation
    }

    fun clearCache() {
        locationCache.invalidateAll()
    }


}