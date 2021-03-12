package net.wdsj.mcserver.wdsjsuite.common.dao.impl

import net.wdsj.mcserver.wdsjsuite.common.TABLE_PREFIX
import net.wdsj.mcserver.wdsjsuite.common.dao.PlotDao
import net.wdsj.mcserver.wdsjsuite.common.dao.entity.PlotEntity
import net.wdsj.servercore.database.DatabaseManager
import net.wdsj.servercore.database.handler.DBTableHandler
import net.wdsj.servercore.utils.extensions.kv

class PlotDaoImpl(databaseManager: DatabaseManager, domain: String) : PlotDao {

    private val table = DBTableHandler.Builder.newBuilder().setModelClass(PlotEntity::class.java)
        .setTable("${TABLE_PREFIX}_${domain}_plot").build(databaseManager)


    override fun getPlayerPlot(uid: Long): PlotEntity? {
        return table.populateOneOrEmpty(PlotEntity::class.java, "uid" kv uid).orElse(null)
    }

}