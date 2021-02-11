package net.wdsj.mcserver.wdsjsuite.common.channel

import net.wdsj.mcserver.wdsjsuite.common.entity.FunctionDataEntity
import net.wdsj.servercore.remote.InvokeResult
import net.wdsj.servercore.remote.receiver.SocketStringInvokeChannelReceiver
import java.util.*

/**
 * @author  Arthur
 * @date  2020/10/20 13:16
 * @version 1.0
 */
open class SuiteSocketChannel : SocketStringInvokeChannelReceiver<SuiteSocketChannel>("WdsjSuite") {


    open fun registerDomain(server: String , domain: String): InvokeResult<Boolean> {
        return InvokeResult.NO_OVERRIDE as InvokeResult<Boolean>;
    }


    open fun revExecCommands(uuid: UUID , commands: Array<String>) : InvokeResult<Boolean> {
        return InvokeResult.NO_OVERRIDE as InvokeResult<Boolean>;
    }

    open fun executeFunction(vararg entity: FunctionDataEntity) : InvokeResult<*> {
        return InvokeResult.NO_OVERRIDE as InvokeResult<Boolean>;
    }
}