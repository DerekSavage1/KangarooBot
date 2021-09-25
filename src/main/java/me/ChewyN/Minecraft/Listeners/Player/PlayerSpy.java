package me.ChewyN.Minecraft.Listeners.Player;

import me.ChewyN.Discord.Listeners.DiscordMessageHandler;
import me.Main;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;


public class PlayerSpy implements Listener {

    private final Main instance = Main.getInstance();

    @EventHandler
    public void signSpy(SignChangeEvent e) {
        Player      p = e.getPlayer();
        String[] lines = e.getLines();

        String signText = "";
        for (String line: lines) {
            signText = signText.concat(" ").concat(line);
        }

        String      playerName = p.getName();

        for(Player player : instance.getServer().getOnlinePlayers()) {
            if (player.hasPermission("commandspy.see.signs") & !player.equals(e.getPlayer())) player.sendMessage(ChatColor.AQUA + "(Sign) " + playerName + " » " + signText);
        }

        DiscordMessageHandler.sendToAdminChannel("(Sign) " + playerName, signText);
        instance.getServer().getConsoleSender().sendMessage(ChatColor.AQUA + "(Sign) " + playerName + " » " + signText);
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
