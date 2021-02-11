package net.wdsj.mcserver.wdsjsuite.common

import net.wdsj.mcserver.wdsjsuite.common.entity.FunctionDataEntity
import java.io.Serializable

/**
 * @author  Arthur
 * @date  2020/10/20 2:25
 * @version 1.0
 */
data class ServerTeleportData(val sourceServer: String , val targetServer :String, val location: String ,val functionEntity: FunctionDataEntity?) : Serializable