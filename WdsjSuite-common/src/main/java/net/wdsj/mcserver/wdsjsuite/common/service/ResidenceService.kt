package net.wdsj.mcserver.wdsjsuite.common.service

import net.wdsj.mcserver.wdsjsuite.common.dao.entity.ResidenceEntity

/**
 * @author  Arthur
 * @date  2021/2/25 21:49
 * @version 1.0
 */
interface ResidenceService {

    fun getResidence(resName : String , refresh : Boolean): ResidenceEntity?

    fun delResidence(resName: String )

    fun addResidence(resName: String  , server : String)
}