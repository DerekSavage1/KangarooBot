package me.ChewyN.Minecraft.Util;

import com.linkedin.urls.Url;
import com.linkedin.urls.detection.UrlDetector;
import com.linkedin.urls.detection.UrlDetectorOptions;
import me.Main;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.List;

import static me.ChewyN.Minecraft.Util.centerMessage.center;

public class MinecraftMessageHandler {

    private static final Main instance = Main.getInstance();

    public static void sendMessage(Player p, String message) {
//        if(containsUrl(message)) {
//            formatUrl(getFirstUrl(message));
//        }//TODO derek
        String name = p.getDisplayName();
        ChatColor nameColor = ChatColor.WHITE;
        String separator = " » ";
        ChatColor separatorColor = ChatColor.GRAY;
        ChatColor messageColor = ChatColor.WHITE;

        p.sendMessage(nameColor + name + separatorColor + separator + messageColor + message); //todo customizable in config.yml

    }


    public static void broadcastMessage(String message) {
        instance.getServer().broadcastMessage(message);

    }

    public static void broadcastCenteredMessage(String message) {
        instance.getServer().broadcastMessage(center(message));
    }

    private static TextComponent formatUrl(String link) {
        TextComponent message = new TextComponent(link);
        message.setColor(net.md_5.bungee.api.ChatColor.AQUA);
        message.setUnderlined(true);
        message.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, link));
        return message;
    }

    private static String getFirstUrl(String str) {
        List<Url> urls = new UrlDetector(str, UrlDetectorOptions.Default).detect();

        return urls.get(0).getFullUrl();
    }

    private static boolean containsUrl(String str) {
        return !new UrlDetector(str, UrlDetectorOptions.Default).detect().isEmpty();
    }


}
