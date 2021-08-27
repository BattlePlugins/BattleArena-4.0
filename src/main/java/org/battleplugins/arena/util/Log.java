package org.battleplugins.arena.util;

import org.bukkit.plugin.Plugin;

public class Log {

    private static Plugin plugin;

    public static void info(String msg){
        plugin.getLogger().info(colorChat(msg));
    }

    public static void warn(String msg){
        plugin.getLogger().warning(colorChat(msg));
    }

    public static void err(String msg){
        plugin.getLogger().severe(colorChat(msg));
    }

    public static String colorChat(String msg) {
        return msg.replace("&", String.valueOf('§'));
    }

    public static void setPlugin(Plugin plugin) {
        Log.plugin = plugin;
    }
}
