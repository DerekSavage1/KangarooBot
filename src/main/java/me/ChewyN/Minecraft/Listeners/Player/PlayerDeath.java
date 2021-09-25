package me.ChewyN.Minecraft.Listeners.Player;

import me.ChewyN.Discord.Listeners.DiscordChannelHandler;
import me.ChewyN.Discord.Listeners.DiscordMessageHandler;
import me.ChewyN.Minecraft.Util.centerMessage;
import me.Main;
import me.Skyla.Minecraft.Objects.DeathStatus;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.TextChannel;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import static me.Main.*;

public class PlayerDeath implements Listener {

    private Main instance = Main.getInstance();

    /**
     * Map that contains a player and their latest death status
     */
    private static final HashMap<Player, DeathStatus> deathMap = new HashMap<>();

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e) {

        EntityDamageEvent damageEvent = e.getEntity().getLastDamageCause();
        assert  damageEvent != null;
        EntityDamageEvent.DamageCause damageCause = e.getEntity().getLastDamageCause().getCause();

        // get our new death message id
        int deathMessageID = getDeathMessageID();

        // Construct the death message
        String deathMessage = getDeathMessage(e, Main.getPluginConfigApi().isMinecraftCenterDeathMessages(getPluginConfig()), deathMessageID);

        // replace the death message
        e.setDeathMessage(deathMessage);

        // get new non-centered message after replacing the mc death message
        deathMessage = getDeathMessage(e, false, deathMessageID);

        deathMap.put(e.getEntity(), new DeathStatus(e.getEntity().getLocation()));

        e.getEntity().getLastDamageCause().getCause().toString().toLowerCase(Locale.ROOT);

        String cause = damageCause.name().toLowerCase(Locale.ROOT).replaceAll("_", " ");

        // location of the death
        Location loc = e.getEntity().getLocation();

        String l  = ("X:" + loc.getBlockX() + ", Y: " + loc.getBlockY() + ", Z: " + loc.getBlockZ());

        // Send the death to discord if enabled
        if (Main.getPluginConfigApi().isSendDeathMessagesToDiscordEnabled(getPluginConfig()))
            sendDeathMessageToDiscord(e.getEntity().getName(), cause, l, deathMessage);

        // Send the player info about the back command if enabled
        if (Main.getPluginConfigApi().isMinecraftBackCommandEnabled(getPluginConfig())) {
            new BukkitRunnable() {
                @Override
                public void run() {
                    e.getEntity().sendMessage(ChatColor.GOLD + "You can teleport to where you died with /back");
                }
            }.runTaskLaterAsynchronously(instance, 10);

        }

    }

    /**
     * Gets the new death message when a player dies
     * @param e The death event that occurred.
     * @param isEnabled For centered text. True if it is enabled, false if not.
     * @param id The id of the death message. Used in death message selection.
     * @return The completed death message String.
     */
    private String getDeathMessage(PlayerDeathEvent e, boolean isEnabled, Integer id) {
        String randomDeathMessage = " passed away :(";

        List<String> deathMessages = getPluginConfigApi().getDeathMessages(getPluginConfig());
        if (!(deathMessages == null)) {
            int messageNumber = id;
            randomDeathMessage = deathMessages.get(messageNumber);
        }

        if (isEnabled) {
           randomDeathMessage = centerMessage.center(ChatColor.RED + "☠ " + ChatColor.WHITE + e.getEntity().getPlayerListName() + " " + randomDeathMessage + ChatColor.RED + " ☠");
        } else {
            randomDeathMessage = e.getEntity().getName() + " " + randomDeathMessage;
        }

        return randomDeathMessage;
    }

    /**
     * Helper method to get a death message ID. Used to send the same message sent in MC chat to Discord.
     * @return The id of the death message in the deathMessages list.
     */
    private int getDeathMessageID() {
        return new Random().nextInt(getPluginConfigApi().getDeathMessages((getPluginConfig())).size());
    }

    /**
     * Method that returns the players DeathStatus
     * @param p the player
     * @return returns the player's death status
     */
    public static DeathStatus getPlayerDeathStatus(Player p) {
        return deathMap.get(p);
    }

    /**
     * Sends death messages to discord. Will send a normal message to the minecraft channel, and a message with extra info to the admin channel.
     * @param name The players name
     * @param c The death cause
     * @param l The death location
     */
    private void sendDeathMessageToDiscord(String name, String c, String l, String deathMessage) {

        final TextChannel DISCORD_MINECRAFT_CHANNEL = DiscordChannelHandler.getDiscordMinecraftChannel(getPluginConfig(),getDiscordbot());
        EmbedBuilder minecraftEmbed = new EmbedBuilder();
        minecraftEmbed.setTitle(deathMessage);
        minecraftEmbed.setColor(0x888888);

        if(Main.getPluginConfigApi().getCustomDiscordDeathMessageDescription(getPluginConfig()) != null) {
            minecraftEmbed.setDescription(Main.getPluginConfigApi().getCustomDiscordDeathMessageDescription(getPluginConfig()));
        }

        if(Main.getPluginConfigApi().isDiscordAdminChannelEnabled(getPluginConfig())
                && Main.getPluginConfigApi().isLogDeathInfoInAdminChannel(getPluginConfig())) {
            EmbedBuilder adminEmbed = new EmbedBuilder();
            adminEmbed.copyFrom(minecraftEmbed);
            adminEmbed.setDescription(name + " died from " + c + ". Location: " + l);
            DiscordMessageHandler.sendToAdminChannel(adminEmbed.build());
            adminEmbed.clear();
        }

        DiscordMessageHandler.sendToMinecraftChannel(minecraftEmbed.build());

        minecraftEmbed.clear();
    }

    public static void setDeathInfo(Player p, DeathStatus status) {
        deathMap.put(p, status);
    }

}
