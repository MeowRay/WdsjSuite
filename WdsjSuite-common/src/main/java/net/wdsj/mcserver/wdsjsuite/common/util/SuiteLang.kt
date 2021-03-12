package net.wdsj.mcserver.wdsjsuite.common.util

import net.wdsj.mcserver.langutils.lang.LangKey

/**
 * @author  Arthur
 * @date  2020/12/8 16:00
 * @version 1.0
 */

const val PREFIX: String = "wdsjsuite."

object SuiteLang {
    fun formatKey(key: String) = LangKey { PREFIX + key }
    fun formatKeyString(key: String) = PREFIX + key
}



