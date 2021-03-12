package net.wdsj.mcserver.wdsjsuite.common.service.impl

import net.wdsj.mcserver.wdsjsuite.common.ServerTeleportData
import net.wdsj.mcserver.wdsjsuite.common.channel.SuiteMessageChannel
import net.wdsj.mcserver.wdsjsuite.common.entity.LocationEntity
import net.wdsj.mcserver.wdsjsuite.common.service.TeleportService
import java.util.*

class TeleportServiceImpl(private val channel: SuiteMessageChannel) : TeleportService {

    override fun teleport(uuid: UUID, from: String, to: String, location: LocationEntity) {
        channel.getRemoteCallerByCache(uuid.toString())
            .reqTeleport(uuid, ServerTeleportData(from, to, location = location.toLocationData()))
    }

}