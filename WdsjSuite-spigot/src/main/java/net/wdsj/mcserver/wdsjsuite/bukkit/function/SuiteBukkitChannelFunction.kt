package net.wdsj.mcserver.wdsjsuite.bukkit.function

import net.wdsj.servercore.remote.receiver.SocketStringInvokeChannelReceiver

/**
 * @author  Arthur
 * @date  2020/12/4 16:46
 * @version 1.0
 */
abstract class SuiteBukkitChannelFunction<C>(channel: String) : SocketStringInvokeChannelReceiver<C>("WdsjSuite:$channel")
