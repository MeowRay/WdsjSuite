package net.wdsj.mcserver.wdsjsuite.bukkit.utils

import net.wdsj.mcserver.langutils.lang.LangKey
import net.wdsj.mcserver.wdsjsuite.bukkit.function.AcceptReplyResult
import net.wdsj.mcserver.wdsjsuite.bukkit.function.RequestResult
import net.wdsj.servercore.common.ResultParams
import org.apache.commons.lang3.StringEscapeUtils
import org.omg.CosNaming.NamingContextExtPackage.StringNameHelper

/**
 * @author  Arthur
 * @date  2020/12/8 16:00
 * @version 1.0
 */

const val PREFIX: String = "wdsjsuite."

object SuiteLang {
    fun formatKey(key: String) = LangKey { PREFIX + key }
}


fun ResultParams<RequestResult>.getLangKeyAsRequest() =
    LangKey { "${PREFIX}teleport.request.result.${result.name.toLowerCase()}" }

fun ResultParams<AcceptReplyResult>.getMessageAsAcceptReply() =
    LangKey { "${PREFIX}teleport.accept.reply.result.${result.name.toLowerCase()}" }


