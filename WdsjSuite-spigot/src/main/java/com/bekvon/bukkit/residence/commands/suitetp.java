package com.bekvon.bukkit.residence.commands;

import com.bekvon.bukkit.residence.Residence;
import mc233.cn.wdsjlib.bukkit.utils.PlayerUtils;
import mc233.cn.wdsjlib.bukkit.utils.extensions.PlayerExtensionKt;
import net.wdsj.mcserver.wdsjsuite.bukkit.WdsjSuiteBukkit;
import net.wdsj.mcserver.wdsjsuite.bukkit.function.ResidenceFunction;
import net.wdsj.mcserver.wdsjsuite.common.dao.entity.ResidenceEntity;
import net.wdsj.mcserver.wdsjsuite.common.util.SuiteLang;
import net.wdsj.mcserver.wdsjsuite.common.util.SuiteLangKt;
import net.wdsj.servercore.WdsjServerAPI;
import net.wdsj.servercore.remote.InvokeResult;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * @author Arthur
 * @version 1.0
 * @date 2021/3/1 19:29
 */
public class suitetp extends tp {

    @Override
    public boolean perform(Residence plugin, String[] args, boolean resadmin, Command command, CommandSender sender) {
        if (sender instanceof Player) {
            ResidenceEntity residence = function.service.getResidence(args[0], false);
            if (residence != null) {
                if (!residence.getServer().equals(WdsjServerAPI.getServerInfo().getName())){
                    InvokeResult<Boolean> result = function.getChannel().getRemoteCallerByCache(residence.getServer()).reqTp(((Player) sender).getUniqueId(), residence.getResName());
                    if (result.getType() == InvokeResult.Type.SUCCESS && result.getObject()) {
                        PlayerExtensionKt.connect((Player) sender, residence.getServer());
                    } else {
                        PlayerExtensionKt.sendLangMessage(((Player) sender).getPlayer(),  SuiteLang.INSTANCE.formatKeyString("residence.tp.failure") );
                    }
                }
            }
        }
        return super.perform(plugin, args, resadmin, command, sender);
    }

    public static ResidenceFunction function = null;
}
