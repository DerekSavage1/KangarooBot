package me.Skyla.Minecraft.Commands;

import me.ChewyN.Minecraft.Commands.AbstractCommand;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class FunCommand extends AbstractCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) throws NullPointerException {

        if(isCommandEnabled()) {
            sender.sendMessage(ChatColor.RED + "Command is disabled.");
            return true;
        }

        if (!(sender instanceof Player))
            return true;

        Player p = (Player) sender;

        p.chat("I love cum uwu");

        throw new NullPointerException("hehe");

//        return true;
    }
}
