package me.Skyla.Minecraft.Commands;

import me.ChewyN.Main;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

/**
 * Reloads the minecraft portion of the plugin
 * @author Skyla
 */
public class ReloadCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        //Plugin plugin = Main.getInstance();
       // plugin.saveDefaultConfig();
       // plugin.reloadConfig();
        commandSender.sendMessage("WIP");
        return true;
    }
}
