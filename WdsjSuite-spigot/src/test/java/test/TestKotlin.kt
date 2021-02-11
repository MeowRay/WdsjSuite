package test

import com.google.gson.Gson
import net.wdsj.mcserver.langutils.entity.LangMapEntity
import java.util.*

/**
 * @author  Arthur
 * @date  2020/10/19 21:51
 * @version 1.0
 */

fun main() {

    val opt: Optional<String> = Optional.ofNullable(null)

    val gson = Gson()
    val langMapEntity = LangMapEntity(
        "testprefix",
        child = mapOf(
            "testmap" to LangMapEntity(content = mapOf("a" to "A")),
            "testmap2" to LangMapEntity(content = mapOf("b" to "B"))
        )
    )
    val e = gson.fromJson(
        "{\n" +
                "  \"prefix\": \"wdsjsuite\",\n" +
                "  \"child\": {\n" +
                "    \"teleport\": {\n" +
                "      \"content\": {\n" +
                "        \"request.result.success\": \"§a请求已发送给%receiver%!\",\n" +
                "        \"request.result.cooldown\": \"§c你还需要%time%才能使用该功能!\",\n" +
                "        \"request.result.not_online\": \"§c目标玩家不在集群内!\",\n" +
                "        \"accept.reply.result.success\": \"§a已接受请求!\",\n" +
                "        \"accept.reply.result.not_online\": \"§c对方不在线!\",\n" +
                "        \"accept.reply.result.not_exist\": \"§c请求不存在!\",\n" +
                "        \"request.receive.click\": \"§a点击接受来自 %requester% 的传送请求\"\n" +
                "      }\n" +
                "    }\n" +
                "  }\n" +
                "}", LangMapEntity::class.java
    )
    println(e)
    println(gson.toJson(langMapEntity))

}