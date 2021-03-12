package net.wdsj.mcserver.wdsjsuite.common.result

import net.wdsj.mcserver.langutils.lang.LangKey
import net.wdsj.mcserver.langutils.lang.LocalDisplayGetter
import net.wdsj.mcserver.wdsjsuite.common.util.SuiteLang

/**
 * @author  Arthur
 * @date  2021/2/25 20:58
 * @version 1.0
 */
enum class HomeSetResult : LocalDisplayGetter {
    SUCCESS,
    NOT_MATCH_REGEX

    ;

    override fun getLocalKey(): String {
        return SuiteLang.formatKeyString("home.set.${name}")
    }
}