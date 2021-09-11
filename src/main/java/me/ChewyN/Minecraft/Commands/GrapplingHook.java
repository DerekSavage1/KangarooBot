package me.ChewyN.Minecraft.Commands;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class GrapplingHook extends AbstractCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender s, Command cmd, @NotNull String label, String[] args) {

        if(!isCommandEnabled()) {
            s.sendMessage(net.md_5.bungee.api.ChatColor.RED + "Command is disabled.");
            return true;
        }

        setup(s, true, false);

        if(cmd.getName().equals("grapplinghook")) {
            if(s instanceof Player) {
                Player sender = (Player) s;
                executeCommand(sender);
                return true;
            } else {
                s.sendMessage("Only players may run this command!");
                return false;
            }
        }

        return false;

    }

    private void executeCommand(Player p) {

        if(p.getInventory().firstEmpty() == -1) {
            //inventory is full
            Location loc = p.getLocation();
            World world = p.getWorld();
            world.dropItemNaturally(loc, getGrapplingHook());
        } else {
            p.getInventory().addItem(getGrapplingHook());
        }
    }

    private ItemStack getGrapplingHook() {
        ItemStack   grapple = new ItemStack(Material.FISHING_ROD);
        ItemMeta    meta = grapple.getItemMeta();

        assert meta != null;
        meta.setDisplayName(ChatColor.GOLD + "Grappling Hook");
        ArrayList<String> lore = new ArrayList<>();
        lore.add("Explore the world with ease");
        meta.setLore(lore);
        grapple.setItemMeta(meta);
        return grapple;
    }
}

