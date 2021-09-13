package me.ChewyN.Minecraft.Util;

import me.ChewyN.Main;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static me.ChewyN.Minecraft.Util.centerMessage.center;

public class MinecraftMessageHandler {

    private static final Main instance = Main.getInstance();

    public static void broadcastMessage(Player p, String message) {
        if(isUrl(message)){
            p.spigot().sendMessage(formatUrl(message));
        }
        else {
            p.sendMessage(p.getDisplayName() + org.bukkit.ChatColor.GRAY + " » " + org.bukkit.ChatColor.WHITE + message);
        }
    }

    public static void broadcastCenteredMessage(Player p, String message) {
        if(isUrl(message)){
            p.spigot().sendMessage(formatUrl(center(message)));
        }
        else {
            p.sendMessage(center(message));
        }
    }

    public static void broadcastMessage(String message) {
        if(isUrl(message)){
            instance.getServer().spigot().broadcast(formatUrl(message));
        }
        else {
            instance.getServer().broadcastMessage(message);
        }
    }

    public static void broadcastCenteredMessage(String message) {
        if(isUrl(message)){
            instance.getServer().spigot().broadcast(formatUrl(center(message)));
        }
        else {
            instance.getServer().broadcastMessage(center(message));
        }
    }

    private static TextComponent formatUrl(String link) {
        TextComponent message = new TextComponent(link);
        message.setColor(ChatColor.AQUA);
        message.setUnderlined(true);
        message.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, link));
        return message;
    }

    private static boolean isUrl(String possibleLink) {
        String urlRegex = "((http:\\/\\/|https:\\/\\/)?(www.)?(([a-zA-Z0-9-]){2,}\\.){1,4}([a-zA-Z]){2,6}(\\/([a-zA-Z-_\\/\\.0-9#:?=&;,]*)?)?)";
        Pattern pattern = Pattern.compile(urlRegex);
        Matcher matcher = pattern.matcher(possibleLink);
        return matcher.find();
    }


}
