package me.Skyla.Minecraft.Commands;

import me.ChewyN.Minecraft.Commands.AbstractCommand;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

/**
 * Reloads the minecraft portion of the plugin
 * @author Skyla
 */
public class ReloadCommand extends AbstractCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {

        if(isCommandEnabled()) {
            sender.sendMessage(ChatColor.RED + "Command is disabled.");
            return true;
        }

//        Plugin plugin = Main.getInstance();
//        plugin.saveDefaultConfig();
//        plugin.reloadConfig();

        sender.sendMessage("WIP");
        return true;
    }
}
