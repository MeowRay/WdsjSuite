package net.wdsj.mcserver.wdsjsuite.common.channel

import net.wdsj.mcserver.wdsjsuite.common.ServerTeleportData
import net.wdsj.mcserver.wdsjsuite.common.entity.FunctionDataEntity
import net.wdsj.servercore.remote.receiver.PluginMessageInvokeChannelReceiver
import java.util.*

/**
 * @author  Arthur
 * @date  2020/10/20 13:16
 * @version 1.0
 */
open class SuiteMessageChannel : PluginMessageInvokeChannelReceiver<SuiteMessageChannel>("WdsjSuite") {

    open fun revTeleport(uuid: UUID, location: String , function : FunctionDataEntity?) {

    }

    open fun reqTeleport(uuid: UUID, data: ServerTeleportData) {

    }

    open fun revExecCommands(uuid: UUID , commands: Array<String>){

    }

    open fun registerDomain(server: String , domain: String){

    }




}