package net.wdsj.mcserver.wdsjsuite.common.dao

import net.wdsj.mcserver.wdsjsuite.common.dao.entity.PlotEntity
import net.wdsj.mcserver.wdsjsuite.common.dao.entity.ResidenceEntity

/**
 * @author  Arthur
 * @date  2021/3/1 17:12
 * @version 1.0
 */
interface PlotDao {

    fun getPlayerPlot(uid : Long): PlotEntity?


}