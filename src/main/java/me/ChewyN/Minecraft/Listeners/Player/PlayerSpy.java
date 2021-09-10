package me.ChewyN.Minecraft.Listeners.Player;

import me.ChewyN.Data.ConfigFile;
import me.ChewyN.Main;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import java.util.Objects;

import static me.ChewyN.Main.discordbot;


public class PlayerSpy implements Listener {


    @EventHandler
    public void signSpy(SignChangeEvent e) {
        Player      player = e.getPlayer();
        String      signMessage =  e.getLine(0) + " " + e.getLine(1)  + " " +  e.getLine(2) + " " + e.getLine(3);
        String      playerName = player.getName();

        if (player.hasPermission("commandspy.see.signs")) player.sendMessage(ChatColor.AQUA + "(Sign) " + playerName + " » " + signMessage);

        sendToAdminDiscord("(Sign) " + playerName, signMessage);
        Main.getInstance().getServer().getConsoleSender().sendMessage(ChatColor.AQUA + "(Sign) " + playerName + " » " + signMessage);
    }

    @EventHandler
    public void commandSpy(PlayerCommandPreprocessEvent e) {
        Player      p = e.getPlayer();
        String      commandMessage = e.getMessage();

        String      playerName;

        if(p.getCustomName() == null) {
            playerName = p.getName();
        } else {
            playerName = p.getCustomName();
        }

        sendToAdminDiscord(playerName, commandMessage);
    }

    public static void sendToAdminDiscord(String username, String message) {
        Objects.requireNonNull(ConfigFile.getAdminChannel(discordbot)).sendMessage("`" + username + " »` " + message).queue();
    }

}
