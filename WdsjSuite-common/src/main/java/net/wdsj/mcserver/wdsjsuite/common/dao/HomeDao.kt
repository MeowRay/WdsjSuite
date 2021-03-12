package net.wdsj.mcserver.wdsjsuite.common.dao

import net.wdsj.mcserver.wdsjsuite.common.dao.entity.HomeEntity

/**
 * @author  Arthur
 * @date  2021/2/4 21:53
 * @version 1.0
 */
interface HomeDao {

    fun getHome(uid: Long, homeId: String): HomeEntity?

    fun delHome(uid : Long, homeId: String) : Boolean

    fun saveHome(uid: Long, homeId: String, server: String, location: String): HomeEntity

}