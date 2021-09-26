package me.Skyla.Minecraft.Commands;

import me.ChewyN.Minecraft.Commands.AbstractCommand;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;

/**
 * A simple command to open a GUI that acts as a "trash can". Players will put it items, close the menu, and the items will be destroyed.
 * @Author Skyla
 */
public class TrashcanCommand extends AbstractCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(!isCommandEnabled()) {
            sender.sendMessage(ChatColor.RED + "Command is disabled.");
            return true;
        }

        if (!(sender instanceof Player p)) {
            sender.sendMessage("MR SQUIDWARD!? I SHOULD KICK YER FUCKING ARSE!");
            return false;
        }
        Inventory i = Bukkit.createInventory(p, InventoryType.HOPPER, "Trash Can");

        p.openInventory(i);
        return true;
    }
}
