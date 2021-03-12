package net.wdsj.mcserver.wdsjsuite.common.service

import net.wdsj.mcserver.wdsjsuite.common.dao.entity.WarpEntity

/**
 * @author Arthur
 * @version 1.0
 * @date 2021/2/23 22:15
 */
interface WarpService
{

    fun getServerLocation(key: String, refresh: Boolean = false): WarpEntity?

    fun saveServerLocation(key: String, server: String, location: String): WarpEntity

    fun clearCache()
}