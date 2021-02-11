package net.wdsj.mcserver.wdsjsuite.common.dao.impl

import net.wdsj.mcserver.wdsjsuite.common.TABLE_PREFIX
import net.wdsj.mcserver.wdsjsuite.common.dao.HomeService
import net.wdsj.mcserver.wdsjsuite.common.dao.WarpService
import net.wdsj.mcserver.wdsjsuite.common.dao.entity.HomeEntity
import net.wdsj.servercore.database.DatabaseManager
import net.wdsj.servercore.database.handler.DBTableHandler
import net.wdsj.servercore.utils.extensions.kv
import net.wdsj.servercore.utils.extensions.populateOneOrNull

class HomeServiceImpl(databaseManager: DatabaseManager, domain: String) : HomeService {

    private val table: DBTableHandler = DBTableHandler.Builder.newBuilder().setModelClass(HomeEntity::class.java)
        .setTable("${TABLE_PREFIX}_${domain}_home").build(databaseManager)


    override fun getHome(uid: Long, homeId: Int): HomeEntity? {
        return table.populateOneOrNull(clazz = HomeEntity::class.java, "uid" kv uid , "home_id" kv homeId)
    }

    override fun removeHome(uid: Long, homeId: Int) : Boolean =  table.delete("uid" kv homeId, "home_id" kv  homeId)

    override fun saveHome(uid: Long, homeId: Int, server: String, location: String): HomeEntity {
        val entity = HomeEntity(null , uid , homeId ,server, location)
        table.save(entity)
        return entity
    }

}