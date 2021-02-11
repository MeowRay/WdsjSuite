package test;

import net.wdsj.common.library.relocations.moshi.v1.com.squareup.moshi.JsonAdapter;
import net.wdsj.common.library.relocations.moshi.v1.com.squareup.moshi.Moshi;
import net.wdsj.common.library.relocations.moshi.v1.com.squareup.moshi.Types;
import net.wdsj.mcserver.wdsjsuite.bukkit.function.RequestResult;
import net.wdsj.servercore.remote.InvokeResult;
import net.wdsj.servercore.utils.SerializableUtils;

import java.io.IOException;

/**
 * @author Arthur
 * @version 1.0
 * @date 2020/12/18 20:15
 */
public class Mwdaw {

    public static void main(String[] args) throws IOException {
        InvokeResult invokeResult = new InvokeResult(InvokeResult.Type.SUCCESS, RequestResult.NOT_ONLINE);
        String serial = SerializableUtils.DEF_STRING_SERIAL.serial(invokeResult);
        System.out.println( serial);
        Object o = SerializableUtils.INSTANCE.GSON_INSTANCE.fromJson(serial, Types.newParameterizedType(InvokeResult.class, RequestResult.class));
        System.out.println(o);
    }

}
