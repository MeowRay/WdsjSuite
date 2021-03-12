package net.wdsj.mcserver.wdsjsuite.common.dao

import net.wdsj.mcserver.wdsjsuite.common.dao.entity.HomeEntity
import net.wdsj.mcserver.wdsjsuite.common.dao.entity.WarpEntity

/**
 * @author  Arthur
 * @date  2020/10/18 18:53
 * @version 1.0
 */

interface WarpDao {

    fun getLocation(key: String) : WarpEntity?

    fun saveLocation( key: String, server: String, location: String): WarpEntity
}