package net.wdsj.mcserver.wdsjsuite.common.service

import net.wdsj.mcserver.wdsjsuite.common.dao.entity.HomeEntity
import net.wdsj.mcserver.wdsjsuite.common.result.HomeSetResult

/**
 * @author  Arthur
 * @date  2021/2/23 22:26
 * @version 1.0
 */
interface HomeService {


    fun setHome(uid: Long, homeId: String, server: String, location: String): HomeSetResult

    fun delHome(uid: Long, homeId: String)

    fun getHome(uid: Long, homeId: String, refresh : Boolean): HomeEntity?
}