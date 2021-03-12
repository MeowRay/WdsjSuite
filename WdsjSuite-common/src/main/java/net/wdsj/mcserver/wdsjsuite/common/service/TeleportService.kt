package net.wdsj.mcserver.wdsjsuite.common.service

import net.wdsj.mcserver.wdsjsuite.common.entity.LocationEntity
import java.util.*

/**
 * @author  Arthur
 * @date  2021/2/25 21:49
 * @version 1.0
 */
interface TeleportService {

    fun teleport(uuid: UUID, from: String, to : String, location : LocationEntity)

}