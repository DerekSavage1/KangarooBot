package me.ChewyN.Minecraft.Listeners.Player;

import me.ChewyN.Data.ConfigFile;
import me.ChewyN.Main;
import me.ChewyN.Minecraft.Util.Message;
import me.Skyla.Minecraft.Objects.DeathStatus;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.TextChannel;
import org.apache.commons.lang.ObjectUtils;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitScheduler;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.logging.Level;

import static me.ChewyN.Data.ConfigFile.getDeathMessages;
import static me.ChewyN.Main.configFile;
import static me.ChewyN.Main.getInstance;

public class PlayerDeath implements Listener {

    private static final HashMap<Player, DeathStatus> deathMap = new HashMap<>();
    private String cOD = "unknown";

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e) {

        e.setDeathMessage(getDeathMessage(e, configFile.centeredDeathMessageEnabled()));

        deathMap.put(e.getEntity(), new DeathStatus(e.getEntity().getLocation()));

        cOD = e.getEntity().getLastDamageCause().getCause().name();
        String l  = ("X:" + e.getEntity().getLocation().getBlockX() + ", Y: " + e.getEntity().getLocation().getBlockY() + ", Z: " + e.getEntity().getLocation().getBlockZ());

        sendDeathMessageToDiscord(e.getEntity().getName(), cOD, l);

        if (ConfigFile.backCommandEnabled()) {
            new BukkitRunnable() {
                @Override
                public void run() {
                    e.getEntity().sendMessage(ChatColor.GOLD + "You can teleport to where you died with /back");
                }
            }.runTaskLaterAsynchronously(Main.getInstance(), 10);

        }

    }

    private String getDeathMessage(PlayerDeathEvent e, boolean isEnabled) {
        String randomDeathMessage = " passed away :(";

        List<String> deathMessages = getDeathMessages();
        if (!deathMessages.isEmpty()) {
            int messageNumber = new Random().nextInt(getDeathMessages().size());
            randomDeathMessage = deathMessages.get(messageNumber);
        }

        if (isEnabled) {
           randomDeathMessage = Message.getCenteredMessage(ChatColor.RED + "☠ " + ChatColor.WHITE + e.getEntity().getPlayerListName() + " " + randomDeathMessage + ChatColor.RED + " ☠");
        }

        return randomDeathMessage;
    }

    public static DeathStatus getPlayerDeathStatus(Player p) {
        return deathMap.get(p);
    }

    /**
     * Sends death messages to discord. Will send a normal message to the minecraft channel, and a message with extra info to the admin channel.
     * @param name The players name
     * @param c The death cause
     * @param l The death location
     */
    private void sendDeathMessageToDiscord(String name, String c, String l) {
        final TextChannel DISCORD_MINECRAFT_CHANNEL = ConfigFile.getMinecraftChannel(Main.getDiscordbot());

        EmbedBuilder message = new EmbedBuilder();
        EmbedBuilder mAdmin = new EmbedBuilder();
        message.setTitle(":skull:");
        mAdmin.setTitle(":skull:");
        message.setColor(0x888888);
        mAdmin.setColor(0x888888);
        message.setDescription(name + " died");
        mAdmin.setDescription(name + " died from " + c + ". Location: " + l);


        Objects.requireNonNull(DISCORD_MINECRAFT_CHANNEL.sendMessage(message.build())).queue();
        Objects.requireNonNull(ConfigFile.getAdminChannel(Main.getDiscordbot()).sendMessage(mAdmin.build())).queue();

        message.clear();
        mAdmin.clear();
    }

}
