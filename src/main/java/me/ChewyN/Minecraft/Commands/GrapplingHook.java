package me.ChewyN.Minecraft.Commands;

import me.ChewyN.Main;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.scheduler.BukkitScheduler;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
//import java.util.HashMap;

public class GrapplingHook extends AbstractCommand implements CommandExecutor {

    // Grapple Cooldown HashMap
    //private HashMap<Player, Integer> coolMap = new HashMap<Player, Integer>();

    @Override
    public boolean onCommand(@NotNull CommandSender s, Command cmd, @NotNull String label, String[] args) {
        if (cmd.getName().equals("grapplinghook")) {
            // Check if player
            if (s instanceof Player) {
                Player sender = (Player) s;
                // Check if cooldown exists
                if (!sender.hasMetadata("grapple_cooldown")) {
                    executeCommand(sender);
                    return true;
                }
                sender.sendMessage(ChatColor.RED + "Command on cooldown!");
                return true;
            } else {
                s.sendMessage("Only players may run this command!");
                return false;
            }
        }

        return false;

    }

    private void executeCommand(Player p) {
        if (p.getInventory().firstEmpty() == -1) {
            //inventory is full
            Location loc = p.getLocation();
            World world = p.getWorld();
            world.dropItemNaturally(loc, getGrapplingHook());
        } else {
            p.getInventory().addItem(getGrapplingHook());
        }
        // Add the cooldown and remove it after 200 ticks (10s)
        p.setMetadata("grapple_cooldown", new FixedMetadataValue(Main.getInstance(), System.currentTimeMillis()));
        BukkitScheduler sch = Main.getInstance().getServer().getScheduler();
        sch.scheduleSyncDelayedTask(Main.getInstance(), () -> p.removeMetadata("grapple_cooldown", Main.getInstance()), 200L);
    }

    private ItemStack getGrapplingHook() {
        ItemStack grapple = new ItemStack(Material.FISHING_ROD);
        ItemMeta meta = grapple.getItemMeta();

        assert meta != null;
        meta.setDisplayName(ChatColor.GOLD + "Grappling Hook");
        ArrayList<String> lore = new ArrayList<>();
        lore.add("Explore the world with ease");
        meta.setLore(lore);
        grapple.setItemMeta(meta);
        return grapple;
    }
}

