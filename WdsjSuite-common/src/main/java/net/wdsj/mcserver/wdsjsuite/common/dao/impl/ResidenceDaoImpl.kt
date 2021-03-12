package net.wdsj.mcserver.wdsjsuite.common.dao.impl

import net.wdsj.mcserver.wdsjsuite.common.TABLE_PREFIX
import net.wdsj.mcserver.wdsjsuite.common.dao.ResidenceDao
import net.wdsj.mcserver.wdsjsuite.common.dao.entity.ResidenceEntity
import net.wdsj.servercore.database.DatabaseManager
import net.wdsj.servercore.database.handler.DBTableHandler
import net.wdsj.servercore.utils.extensions.kv

class ResidenceDaoImpl(databaseManager: DatabaseManager, domain: String) : ResidenceDao {

    private val table_res = DBTableHandler.Builder.newBuilder().setModelClass(ResidenceEntity::class.java)
        .setTable("${TABLE_PREFIX}_${domain}_residence").build(databaseManager)

    override fun addResidence(resName: String, server: String): ResidenceEntity {
        val saveGetId = table_res.saveGetId(ResidenceEntity(null, resName, server))
        return ResidenceEntity(saveGetId , resName, server)
    }

    override fun getResidence(resName: String): ResidenceEntity? {
        return table_res.populateOneOrEmpty(ResidenceEntity::class.java, "res_name" kv resName).orElse(null)
    }

    override fun delResidence(resName: String) {
        table_res.delete("res_name" kv resName)
    }
}