package net.wdsj.mcserver.wdsjsuite.common.dao

import net.wdsj.mcserver.wdsjsuite.common.dao.entity.ResidenceEntity

/**
 * @author  Arthur
 * @date  2021/3/1 17:12
 * @version 1.0
 */
interface ResidenceDao {

    fun addResidence(resName: String , server : String): ResidenceEntity

    fun getResidence(resName : String): ResidenceEntity?

    fun delResidence(resName : String)



}