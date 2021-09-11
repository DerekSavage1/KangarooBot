package me.Skyla.Minecraft.Util;

import org.bukkit.entity.Player;

import java.util.HashMap;

/**
 * Will eventually be something like a local-chat. SUPER WIP, may delete later..
 */
public class LocalChat {

    // Map with a player and their current chat status
    private HashMap<Player, ChatStatus> chatMap = new HashMap<>();


    public ChatStatus getPlayerChatStatus(Player p) {
        try {
            return chatMap.get(p);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        return null;
    }

}
