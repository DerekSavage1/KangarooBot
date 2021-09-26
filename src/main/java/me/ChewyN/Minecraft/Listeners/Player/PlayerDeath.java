package me.ChewyN.Minecraft.Listeners.Player;

import me.ChewyN.Discord.Listeners.DiscordMessageHandler;
import me.ChewyN.Minecraft.Util.centerMessage;
import me.Main;
import net.dv8tion.jda.api.EmbedBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;
import java.util.Locale;
import java.util.Random;

import static me.Main.getPluginConfig;
import static me.Main.getPluginConfigApi;

public class PlayerDeath implements Listener {

    private final Main instance = Main.getInstance();

    @EventHandler
    @SuppressWarnings("StatementWithEmptyBody")
    public void onPlayerDeath(PlayerDeathEvent e) {

        Location loc = e.getEntity().getLocation();

        EntityDamageEvent damageEvent = e.getEntity().getLastDamageCause();
        assert  damageEvent != null;
        EntityDamageEvent.DamageCause damageCause = e.getEntity().getLastDamageCause().getCause();

        //Add death information to Player's NBT tag
        PersistentDataContainer container = e.getEntity().getPersistentDataContainer();
        String locationString = loc.getWorld().getName()+ ',' + loc.toVector() + ',' + loc.getYaw() + ',' + loc.getPitch();
        container.set(new NamespacedKey(Main.getInstance(), "LastPlayerLocation"), PersistentDataType.STRING,locationString);
        container.set(new NamespacedKey(Main.getInstance(), "hasTeleportedToDeathLocation"),PersistentDataType.BYTE,Byte.MIN_VALUE);

        // Construct the death message
        String deathMessage = getDeathMessage(e, Main.getPluginConfigApi().isMinecraftCenterDeathMessages(getPluginConfig()), getDeathMessageID());

        // replace the death message
        e.setDeathMessage(deathMessage);

        // get new non-centered message after replacing the mc death message
        deathMessage = getDeathMessage(e, false, getDeathMessageID());

        e.getEntity().getLastDamageCause().getCause().toString().toLowerCase(Locale.ROOT);

        String cause = damageCause.name().toLowerCase(Locale.ROOT).replaceAll("_", " ").replaceAll("entity ", "");
        if(cause.endsWith("ing")) {
            //empty body intended
        } else if(cause.charAt(0) == 'a'
                || cause.charAt(0) == 'e'
                || cause.charAt(0) == 'i'
                || cause.charAt(0) == 'o'
                || cause.charAt(0) == 'u') {
            cause = "an " + cause;
        } else {
            cause = "a " + cause;
        }


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
        String randomDeathMessage = "";

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
     * Sends death messages to discord. Will send a normal message to the minecraft channel, and a message with extra info to the admin channel.
     * @param name The players name
     * @param c The death cause
     * @param l The death location
     */
    private void sendDeathMessageToDiscord(String name, String c, String l, String deathMessage) {

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

}
