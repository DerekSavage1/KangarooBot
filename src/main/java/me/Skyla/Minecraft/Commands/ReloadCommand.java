package me.Skyla.Minecraft.Commands;

import me.ChewyN.Minecraft.Commands.AbstractCommand;
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
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        //Plugin plugin = Main.getInstance();
       // plugin.saveDefaultConfig();
       // plugin.reloadConfig();
        commandSender.sendMessage("WIP");
        return true;
    }
}
