package net.wdsj.mcserver.wdsjsuite.common.dao

import net.wdsj.mcserver.wdsjsuite.common.dao.entity.HomeEntity

/**
 * @author  Arthur
 * @date  2021/2/4 21:53
 * @version 1.0
 */
interface HomeService {

    fun getHome(uid: Long, homeId: Int): HomeEntity?

    fun removeHome(uid : Long , homeId: Int) : Boolean

    fun saveHome(uid: Long, homeId: Int, server: String, location: String): HomeEntity

}