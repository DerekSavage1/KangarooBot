package me.Skyla.Minecraft.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

/**
 * A command to switch chat between global and local for a player. Still very WIP
 * @author Skyla
 */
public class ChangeChat implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        // Make sure sender is a player
        if (!(sender instanceof Player)) {
            sender.sendMessage("Must be a player!");
            return false;
        }
        Player p = (Player) sender;
        p.sendMessage("WIP!");
        return true;
    }
}
