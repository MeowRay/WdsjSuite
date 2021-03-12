package net.wdsj.mcserver.wdsjsuite.common.service.impl

import com.github.benmanes.caffeine.cache.Caffeine
import net.wdsj.mcserver.wdsjsuite.common.dao.ResidenceDao
import net.wdsj.mcserver.wdsjsuite.common.dao.entity.ResidenceEntity
import net.wdsj.mcserver.wdsjsuite.common.service.ResidenceService
import java.util.*
import java.util.concurrent.TimeUnit

class ResidenceServiceImpl(private val dao: ResidenceDao) : ResidenceService {

    private val cache =
        Caffeine.newBuilder().expireAfterWrite(10, TimeUnit.MINUTES).build<String, Optional<ResidenceEntity>> {
            Optional.ofNullable(dao.getResidence(it))
        }


    override  fun addResidence(resName: String , server : String ) {
        dao.addResidence(resName ,server )
    }

    override fun getResidence(resName: String, refresh: Boolean): ResidenceEntity? {
        if (refresh) cache.invalidate(resName)
        return cache.get(resName)?.orElse(null)
    }

    override fun delResidence(resName: String) {
        cache.invalidate(resName)
        dao.delResidence(resName)
    }
}