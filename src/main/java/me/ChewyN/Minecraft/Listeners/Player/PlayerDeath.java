package me.ChewyN.Minecraft.Listeners.Player;

import me.ChewyN.Data.ConfigFile;
import me.ChewyN.Main;
import me.ChewyN.Minecraft.Util.centerMessage;
import me.Skyla.Minecraft.Objects.DeathStatus;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.TextChannel;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.scheduler.BukkitRunnable;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.Random;

import static me.ChewyN.Data.ConfigFile.getDeathMessages;
import static me.ChewyN.Main.configFile;

public class PlayerDeath implements Listener {

    /**
     * Map that contains a player and their latest death status
     */
    private static final HashMap<Player, DeathStatus> deathMap = new HashMap<>();

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e) {

        // get our new death message id
        int deathMessageID = getDeathMessageID();

        // Construct the death message
        String deathMessage = getDeathMessage(e, configFile.centeredDeathMessageEnabled(), deathMessageID);

        // replace the death message
        e.setDeathMessage(deathMessage);

        // get new non-centered message after replacing the mc death message
        deathMessage = getDeathMessage(e, false, deathMessageID);

        deathMap.put(e.getEntity(), new DeathStatus(e.getEntity().getLocation()));

        // players cause of death
        String cOD = Objects.requireNonNull(e.getEntity().getLastDamageCause()).getCause().toString();


        // If it was a player, make the player the cause //FIXME: THIS BREAKS
        // if (!Objects.requireNonNull(e.getEntity().getKiller()).getPlayerListName().isEmpty())
        //    cOD = Objects.requireNonNull(e.getEntity().getKiller().getPlayerListName());

        // location of the death
        String l  = ("X:" + e.getEntity().getLocation().getBlockX() + ", Y: " + e.getEntity().getLocation().getBlockY() + ", Z: " + e.getEntity().getLocation().getBlockZ());

        // Send the death to discord if enabled
        if (ConfigFile.sendDeathToDiscord())
            sendDeathMessageToDiscord(e.getEntity().getName(), cOD, l, deathMessage);

        // Send the player info about the back command if enabled
        if (ConfigFile.backCommandEnabled()) {
            new BukkitRunnable() {
                @Override
                public void run() {
                    e.getEntity().sendMessage(ChatColor.GOLD + "You can teleport to where you died with /back");
                }
            }.runTaskLaterAsynchronously(Main.getInstance(), 10);

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

        List<String> deathMessages = getDeathMessages();
        if (!(deathMessages == null)) {
            int messageNumber = id;
            randomDeathMessage = e.getEntity().getPlayerListName() + " " + deathMessages.get(messageNumber);
        }

        if (isEnabled) {
           randomDeathMessage = centerMessage.center(ChatColor.RED + "☠ " + ChatColor.WHITE + e.getEntity().getPlayerListName() + " " + randomDeathMessage + ChatColor.RED + " ☠");
        }

        return randomDeathMessage;
    }

    /**
     * Helper method to get a death message ID. Used to send the same message sent in MC chat to Discord.
     * @return The id of the death message in the deathMessages list.
     */
    private int getDeathMessageID() {
         int messageNumber = new Random().nextInt(getDeathMessages().size()); //TODO throw a warning and disable if message list is empty
         return messageNumber;
    }

    /**
     * Method that returns the players DeathStatus
     * @param p
     * @return
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
        final TextChannel DISCORD_MINECRAFT_CHANNEL = ConfigFile.getMinecraftChannel(Main.getDiscordbot());
        final TextChannel DISCORD_ADMIN_CHANNEL = ConfigFile.getAdminChannel(Main.getDiscordbot());


        EmbedBuilder message = new EmbedBuilder();
        EmbedBuilder mAdmin = new EmbedBuilder();
        message.setTitle(deathMessage);
        mAdmin.setTitle(deathMessage);
        message.setColor(0x888888);
        mAdmin.setColor(0x888888);
        message.setDescription(ConfigFile.getDiscordDeathDescription());
        mAdmin.setDescription(name + " died from " + c + ". Location: " + l);


        assert DISCORD_MINECRAFT_CHANNEL != null;
        Objects.requireNonNull(DISCORD_MINECRAFT_CHANNEL.sendMessage(message.build())).queue();

        // Check if admin channel is enabled and check if we should send death data to the admin channel
        if(Main.getConfigFile().isAdminChannelEnabled() && ConfigFile.logDeathInAdmin()) {
            Objects.requireNonNull(DISCORD_ADMIN_CHANNEL.sendMessage(mAdmin.build())).queue();
        }

        message.clear();
        mAdmin.clear();
    }

}
