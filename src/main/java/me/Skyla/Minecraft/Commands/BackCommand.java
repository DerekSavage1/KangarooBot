package me.Skyla.Minecraft.Commands;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import me.ChewyN.Minecraft.Listeners.Player.*;

public class BackCommand implements CommandExecutor {


    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("MR SQUIDWARD!? I SHOULD KICK YER FUCKING ARSE!");
            return false;
        }
        Player p = (Player) sender;
        Location l = PlayerDeath.getPlayerDeathLocation(p);

        //TODO: Check if block under is lava or air, then send safety message

        p.teleport(l);

        return true;
    }
}
