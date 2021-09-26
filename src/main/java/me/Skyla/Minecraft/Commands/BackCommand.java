package me.Skyla.Minecraft.Commands;

import me.ChewyN.Minecraft.Commands.AbstractCommand;
import me.Main;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;

import java.util.List;

import static me.Main.getPluginConfig;

/**
 * A command that allows players to teleport to their death point once after death.
 * @Author Skyla
 */
public class BackCommand extends AbstractCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        // Cancel if not enabled
        super.setCommandEnabled(Main.getPluginConfigApi().isMinecraftBackCommandEnabled(getPluginConfig()));
        if(!isCommandEnabled()) {
            sender.sendMessage(net.md_5.bungee.api.ChatColor.RED + "Command is disabled.");
            return true;
        }

        // Cancel if not player
        if (!(sender instanceof Player p)) {
            sender.sendMessage("MR SQUIDWARD!? I SHOULD KICK YER FUCKING ARSE!");
            return true;
        }

        //Cancel if player has teleported
        if (hasTeleported(p)) {
            sender.sendMessage(ChatColor.RED + "You already teleported!");
            return true;
        }

        // Cancel if player hasn't died yet
        Location l = parseLocationFromPlayer(p);
        if(l == null) {
            sender.sendMessage(ChatColor.RED + "You haven't died yet!");
            return true;
        }


        // Delay if unsafe
        Material mat = l.clone().subtract(0, 1, 0).getBlock().getType();
        if (mat.equals(Material.AIR) || mat.equals(Material.LAVA)) {
            p.sendMessage(ChatColor.RED + "Teleporting to possibly unsafe location, waiting 5 seconds");

            //Delay 5s
            Main.getInstance().getServer().getScheduler().scheduleSyncDelayedTask(Main.getInstance(), () -> {
                // Add some protection effects for 20s
                p.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 400, 1));
                p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_FALLING, 200, 1));
                p.teleport(l.add(0, 1, 0));
                p.teleport(l);
                setHasTeleported(p);
            }, 100L);
        } else {
            p.teleport(l);
            setHasTeleported(p);
        }
        return true;
    }

    private static Location parseLocationFromPlayer(Player p) {

        PersistentDataContainer container = p.getPersistentDataContainer();
        if(!container.has(new NamespacedKey(Main.getInstance(), "LastPlayerLocation"),PersistentDataType.STRING)) {
            return null;
        }

        String locationString = container.get(new NamespacedKey(Main.getInstance(), "LastPlayerLocation"),PersistentDataType.STRING);
        if(locationString == null) {
            return null;
        }

        String[] locationComponents = locationString.split(",");

        World world = null;
        List<World> worlds = Main.getInstance().getServer().getWorlds();

        for(World worldTarget : worlds) {
            if(worldTarget.getName().equals(locationComponents[0])) {
                world = worldTarget;
                break;
            }

        }
        if(world == null)
            return null;

        double x = Double.parseDouble(locationComponents[1]);
        double y = Double.parseDouble(locationComponents[2]);
        double z = Double.parseDouble(locationComponents[3]);
        float yaw = Float.parseFloat(locationComponents[4]);
        float pitch = Float.parseFloat(locationComponents[5]);

        Main.debug(p.getWorld().toString());

        return new Location(world, x,y,z,yaw,pitch);

    }

    private static void setHasTeleported(Player p) {

        PersistentDataContainer container = p.getPersistentDataContainer();
        container.set(new NamespacedKey(Main.getInstance(), "hasTeleportedToDeathLocation"),PersistentDataType.BYTE, Byte.MAX_VALUE);

    }

    private static boolean hasTeleported(Player p) {

        PersistentDataContainer container = p.getPersistentDataContainer();
        Byte hasTeleportedByte = container.get(new NamespacedKey(Main.getInstance(), "hasTeleportedToDeathLocation"),PersistentDataType.BYTE);
        return hasTeleportedByte != null && hasTeleportedByte != Byte.MIN_VALUE;

    }


}
