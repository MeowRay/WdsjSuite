package net.wdsj.mcserver.wdsjsuite.bungee.channel

import net.wdsj.mcserver.wdsjsuite.bungee.WdsjSuiteBungee
import net.wdsj.mcserver.wdsjsuite.common.channel.SuiteSocketChannel
import net.wdsj.servercore.remote.InvokeResult

/**
 * @author  Arthur
 * @date  2020/12/3 20:34
 * @version 1.0
 */
class SuiteBungeeSocketChannel(private val manager: WdsjSuiteBungee) : SuiteSocketChannel() {

    override fun registerDomain(server: String, domain: String) : InvokeResult<Boolean>  {
        manager.registerServerDomain(server, domain)
        return InvokeResult(InvokeResult.Type.SUCCESS, true)
    }

    override fun getConstructorObjects(): MutableList<Any>  = mutableListOf(manager)
}