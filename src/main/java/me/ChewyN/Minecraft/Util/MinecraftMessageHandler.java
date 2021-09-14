package me.ChewyN.Minecraft.Util;

import com.linkedin.urls.Url;
import com.linkedin.urls.detection.UrlDetector;
import com.linkedin.urls.detection.UrlDetectorOptions;
import me.ChewyN.Main;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;

import java.util.List;

import static me.ChewyN.Minecraft.Util.centerMessage.center;

public class MinecraftMessageHandler {

    private static final Main instance = Main.getInstance();

    public static void broadcastMessage(Player p, String message) {
//        if(containsUrl(message)) {
//            formatUrl(getFirstUrl(message));
//        }//TODO derek
        p.sendMessage(getUsedName(p) + org.bukkit.ChatColor.GRAY + " » " + org.bukkit.ChatColor.WHITE + message); //todo customizable in config.yml

    }

    public static void broadcastCenteredMessage(Player p, String message) {
        p.sendMessage(getUsedName(p) + org.bukkit.ChatColor.GRAY + " » " + org.bukkit.ChatColor.WHITE + message); //todo customizable in config.yml
    }

    public static void broadcastMessage(String message) {
        instance.getServer().broadcastMessage(message);

    }

    public static void broadcastCenteredMessage(String message) {
        instance.getServer().broadcastMessage(center(message));
    }

    private static TextComponent formatUrl(String link) {
        TextComponent message = new TextComponent(link);
        message.setColor(ChatColor.AQUA);
        message.setUnderlined(true);
        message.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, link));
        return message;
    }

    private static String getUsedName(Player p) {
        if(p.getCustomName().isEmpty()){
            return p.getName();
        }
        return p.getCustomName();
    }

    private static String getFirstUrl(String str) {
        List<Url> urls = new UrlDetector(str, UrlDetectorOptions.Default).detect();

        return urls.get(0).getFullUrl();
    }

    private static boolean containsUrl(String str) {
        return !new UrlDetector(str, UrlDetectorOptions.Default).detect().isEmpty();
    }


}
