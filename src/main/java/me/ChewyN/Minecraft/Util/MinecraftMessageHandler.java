package me.ChewyN.Minecraft.Util;

import me.ChewyN.Main;
import org.bukkit.entity.Player;

import static me.ChewyN.Minecraft.Util.centerMessage.center;

public class MinecraftMessageHandler {

    private static final Main instance = Main.getInstance();

    public static void sendMessage(Player p, String message) {
        p.sendMessage(message);
    }

    public static void sendCenteredMessage(Player p, String message) {
        p.sendMessage(center(message));
    }

    public static void sendMessage(String message) {
        instance.getServer().broadcastMessage(message);
    }

    public static void sendCenteredMessage(String message) {
        instance.getServer().broadcastMessage(center(message));
    }



}
