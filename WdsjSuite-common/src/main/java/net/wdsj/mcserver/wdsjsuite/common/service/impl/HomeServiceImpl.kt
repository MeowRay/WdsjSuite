package net.wdsj.mcserver.wdsjsuite.common.service.impl

import com.github.benmanes.caffeine.cache.Cache
import com.github.benmanes.caffeine.cache.Caffeine
import com.github.benmanes.caffeine.cache.LoadingCache
import net.wdsj.mcserver.wdsjsuite.common.dao.HomeDao
import net.wdsj.mcserver.wdsjsuite.common.dao.entity.HomeEntity
import net.wdsj.mcserver.wdsjsuite.common.result.HomeSetResult
import net.wdsj.mcserver.wdsjsuite.common.service.HomeService
import java.util.*
import java.util.concurrent.TimeUnit

class HomeServiceImpl(private val homeDao: HomeDao) : HomeService {


    private val cache: LoadingCache<Long, Cache<String, Optional<HomeEntity>>> =
        Caffeine.newBuilder().expireAfterWrite(1, TimeUnit.DAYS).build {
            Caffeine.newBuilder().build()
        }
    private  val homeIdRegex = Regex("[\\u4e00-\\u9fa5_a-zA-Z0-9_]{1,10}")


    override fun setHome(uid : Long , homeId : String , server : String , location: String) : HomeSetResult {
        if (homeId.matches(homeIdRegex)) {
            val saveHome = homeDao.saveHome(uid, homeId, server, location)
            cache.get(uid)?.put(homeId, Optional.of(saveHome))
            return  HomeSetResult.SUCCESS
        }
        return HomeSetResult.NOT_MATCH_REGEX
    }

    override fun delHome(uid: Long ,homeId : String) {
        homeDao.delHome(uid,homeId)
        val entity = cache.get(uid)
        entity?.invalidate(homeId)
    /*
        if (homeId == ""){
            cache.invalidate(uid)
        }else{
        }*/
    }

    override fun getHome (uid: Long, homeId: String, refresh: Boolean):  HomeEntity?{
        if (! homeId.matches( homeIdRegex)) {
            return null
        }
        if (refresh) cache.get(uid)?.invalidate(homeId)
       return cache.get(uid)?.get(homeId){
           Optional.ofNullable( homeDao.getHome(uid, it))
        }?.orElse(null)
    }

}