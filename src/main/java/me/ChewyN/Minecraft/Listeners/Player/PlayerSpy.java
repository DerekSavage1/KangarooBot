package me.ChewyN.Minecraft.Listeners.Player;

import me.ChewyN.Discord.Listeners.DiscordMessageHandler;
import me.ChewyN.Main;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import static me.ChewyN.Main.instance;


public class PlayerSpy implements Listener {


    @EventHandler
    public void signSpy(SignChangeEvent e) {
        Player      p = e.getPlayer();
        String      signMessage =  e.getLine(0) + " " + e.getLine(1)  + " " +  e.getLine(2) + " " + e.getLine(3);
        String      playerName = p.getName();


        for(Player player : instance.getServer().getOnlinePlayers()) {
            if (player.hasPermission("commandspy.see.signs") & !player.equals(e.getPlayer())) player.sendMessage(ChatColor.AQUA + "(Sign) " + playerName + " » " + signMessage);
        }


        DiscordMessageHandler.sendToAdminChannel("(Sign) " + playerName, signMessage);
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

        for(Player player : instance.getServer().getOnlinePlayers()) {
            if (player.hasPermission("commandspy.see.commands") && !player.equals(e.getPlayer())) player.sendMessage(ChatColor.AQUA + playerName + " » " + commandMessage);
        }


        DiscordMessageHandler.sendToAdminChannel(playerName, commandMessage);
    }


}
