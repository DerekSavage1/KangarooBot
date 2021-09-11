package me.Skyla.Minecraft.Commands;

import me.ChewyN.Data.ConfigFile;
import me.ChewyN.Main;
import me.ChewyN.Minecraft.Commands.AbstractCommand;
import me.ChewyN.Minecraft.Listeners.Player.PlayerDeath;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitScheduler;
import org.jetbrains.annotations.NotNull;

/**
 * A command that allows players to teleport to their death point once after death.
 * @Author Skyla
 */
public class BackCommand extends AbstractCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(!isCommandEnabled()) {
            sender.sendMessage(net.md_5.bungee.api.ChatColor.RED + "Command is disabled.");
            return true;
        }

        // Check if sender is a player

        if (!(sender instanceof Player)) {
            sender.sendMessage("MR SQUIDWARD!? I SHOULD KICK YER FUCKING ARSE!");
            return false;
        }

        // Our Player
        Player p = (Player) sender;

        // If command is enabled or if the player has not teleported back once yet, run the command
        if (ConfigFile.backCommandEnabled() && !PlayerDeath.getPlayerDeathStatus(p).getTPStatus()) {

            Location l = PlayerDeath.getPlayerDeathStatus(p).getLocation();
            Block b = l.getBlock();

            if (b.getType() == Material.LAVA || b.getType().isAir()) {
                p.sendMessage(ChatColor.RED + "Teleporting to possibly unsafe location, waiting 5 seconds");
                BukkitScheduler scheduler = Main.getInstance().getServer().getScheduler();
                // Schedule the tp event, and delay it by 5s
                scheduler.scheduleSyncDelayedTask(Main.getInstance(), new Runnable() {
                    @Override
                    public void run() {
                        // Add some protection effects for 20s
                        p.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 400, 1));
                        p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_FALLING, 200, 1));
                        p.teleport(l);
                    }
                }, 20L);
                // set tp status to true
                PlayerDeath.getPlayerDeathStatus(p).setTPStatus(true);
                return true;
            }
            p.teleport(l);
            // set tp status to true
            PlayerDeath.getPlayerDeathStatus(p).setTPStatus(true);
            return true;
        }
        p.sendMessage(ChatColor.RED + "This command has been disabled, or you have already teleported back!");
        return true;
    }
}
