package com.example.untitleddiscordbot.Utils;

import com.example.untitleddiscordbot.Models.UserGuildsModel.UserGuildModelItem;

public class PermissionUtil {

    public static boolean canManageServer(UserGuildModelItem item){
        if(item.isOwner())
            return true;
        int permissions = item.getPermissions();
        return ((permissions & 0x8) == 0x8 || (permissions & 0x20) == 0x20);
    }
}
