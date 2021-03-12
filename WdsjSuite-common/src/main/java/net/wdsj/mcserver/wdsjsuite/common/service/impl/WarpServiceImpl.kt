package net.wdsj.mcserver.wdsjsuite.common.service.impl

import com.github.benmanes.caffeine.cache.Cache
import com.github.benmanes.caffeine.cache.Caffeine
import net.wdsj.mcserver.wdsjsuite.common.dao.WarpDao
import net.wdsj.mcserver.wdsjsuite.common.dao.entity.WarpEntity
import net.wdsj.mcserver.wdsjsuite.common.service.WarpService
import java.util.concurrent.TimeUnit

class WarpServiceImpl(private val warpDao: WarpDao) : WarpService {


    private val cache: Cache<String, WarpEntity> =
        Caffeine.newBuilder().expireAfterWrite(1, TimeUnit.DAYS).build();


    override fun getServerLocation(key: String, refresh: Boolean): WarpEntity? {
        if (refresh) {
            cache.invalidate(key)
        }
        return cache.get(key) {
            warpDao.getLocation(it) ?: WarpEntity()
        }
    }

    override fun saveServerLocation(key: String, server: String, location: String): WarpEntity {
        val saveLocation = warpDao.saveLocation(key, server, location)
        cache.put(key, saveLocation)
        return saveLocation
    }

    override fun clearCache() {
        cache.invalidateAll()
    }
}