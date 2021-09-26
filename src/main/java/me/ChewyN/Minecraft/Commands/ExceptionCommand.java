package me.ChewyN.Minecraft.Commands;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class ExceptionCommand extends AbstractCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {

        if(!isCommandEnabled()) {
            sender.sendMessage(ChatColor.RED + "Command is disabled.");
            return true;
        }

        if(!sender.isOp())
            return true;

        throw new NullPointerException("Exception Command");

    }
}
