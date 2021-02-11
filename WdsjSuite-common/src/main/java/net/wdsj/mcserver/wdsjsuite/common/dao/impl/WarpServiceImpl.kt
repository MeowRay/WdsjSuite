package net.wdsj.mcserver.wdsjsuite.common.dao.impl

import net.wdsj.mcserver.wdsjsuite.common.TABLE_PREFIX
import net.wdsj.mcserver.wdsjsuite.common.dao.WarpService
import net.wdsj.mcserver.wdsjsuite.common.dao.entity.WarpEntity
import net.wdsj.servercore.database.DatabaseManager
import net.wdsj.servercore.database.handler.DBTableHandler
import net.wdsj.servercore.utils.extensions.kv
import net.wdsj.servercore.utils.extensions.populateOneOrNull

class WarpServiceImpl(databaseManager: DatabaseManager, domain: String) : WarpService {

    private val table: DBTableHandler = DBTableHandler.Builder.newBuilder().setModelClass(WarpEntity::class.java)
        .setTable("${TABLE_PREFIX}_${domain}_warp").build(databaseManager)

    override fun getLocation(key: String): WarpEntity? {
        return table.populateOneOrNull(clazz = WarpEntity::class.java, "key" kv key)
    }

    override fun saveLocation(key: String, server: String, location: String): WarpEntity {
        val warpEntity = WarpEntity(key, server, location)
        table.save(warpEntity)
        return warpEntity
    }

}