package me.Skyla.Minecraft.Util;

import org.bukkit.entity.Player;

import java.util.HashMap;

public class LocalChat {

    private HashMap<Player, Boolean> chatMap = new HashMap<>();


    public Boolean getPlayerChatStatus(Player p) {
        try {
            return chatMap.get(p);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        return null;
    }

}
