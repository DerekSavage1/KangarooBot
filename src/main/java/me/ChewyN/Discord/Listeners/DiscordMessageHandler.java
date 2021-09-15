package me.ChewyN.Discord.Listeners;

import me.ChewyN.Data.AbstractFile;
import me.ChewyN.Data.ConfigFile;
import me.ChewyN.Main;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.TextChannel;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.logging.Level;

import static me.ChewyN.Main.*;

public class DiscordMessageHandler {

    final private static JDA DISCORDBOT = Main.getDiscordbot();
    final private static TextChannel MINECRAFT_CHANNEL = ConfigFile.getMinecraftChannel(Main.getConfigFile() ,getDiscordbot());

    public static void sendToMinecraftChannel(MessageEmbed message) {
        MINECRAFT_CHANNEL.sendMessage(message).queue();
    }

    public static void sendToMinecraftChannel(String username, String message) {
        MINECRAFT_CHANNEL.sendMessage("`" + username + " »` " + message).queue(); //TODO: make text format customizable in config
    }

    public static void sendToAdminChannel(String message) {
        TextChannel adminChannel = ConfigFile.getAdminChannel(getConfigFile(), DISCORDBOT);
        if(Main.getConfigFile().isAdminChannelEnabled(getConfigFile()) && adminChannel == null) {
            Main.getConfigFile().setAdminChannelEnabled(getConfigFile(),false);
            Main.log(Level.SEVERE, "Admin channel enabled but not found! Disabling...");
        }
        else if (Main.getConfigFile().isAdminChannelEnabled(getConfigFile())) {
            adminChannel.sendMessage(message);
        }
    }

    public static void sendToAdminChannel(MessageEmbed message) {
        TextChannel adminChannel = ConfigFile.getAdminChannel(getConfigFile(), discordbot);
        if(Main.getConfigFile().isAdminChannelEnabled(getConfigFile()) && adminChannel == null) {
            Main.getConfigFile().setAdminChannelEnabled(getConfigFile(), false);
            Main.log(Level.SEVERE, "Admin channel enabled but not found! Disabling...");
        }
        else if (Main.getConfigFile().isAdminChannelEnabled(getConfigFile())) {
            adminChannel.sendMessage(message).queue();
        }
    }

    public static void sendToAdminChannel(String username, String message) {
        try{
            if(Main.getConfigFile().isAdminChannelEnabled(getConfigFile()) && ConfigFile.getAdminChannel(getConfigFile(), DISCORDBOT) == null) {
                Main.getConfigFile().setAdminChannelEnabled(getConfigFile(), false);
                AbstractFile.save(configFile);
                Main.log(Level.SEVERE, "Admin channel enabled but not found! Disabling...");
            }
            else if (Main.getConfigFile().isAdminChannelEnabled(getConfigFile())) {
                ConfigFile.getAdminChannel(getConfigFile(), DISCORDBOT).sendMessage("`" + username + " »` " + message).queue();
            }
        }
        catch(IllegalArgumentException | NullPointerException e) {
            log(Level.SEVERE, "Admin channel is enabled but " + e.getMessage());
            Main.log(Level.SEVERE, "Admin channel enabled but not found! Disabling...");
            Main.getConfigFile().setAdminChannelEnabled(getConfigFile(), false);
        }
    }

    public static void sendToBothDiscordChannels(MessageEmbed embed) {
        sendToAdminChannel(embed);
        sendToMinecraftChannel(embed);
    }

    public static void sendToBothDiscordChannels(String username, String message) {
        sendToAdminChannel(username, message);
        sendToMinecraftChannel(username, message);
    }

    public static String formatMessage(Message message) {

        if(!message.getAttachments().isEmpty()) {

            List<Message.Attachment> attachments = message.getAttachments();

            if(attachments.size() > 1)
                return "Multiple Attachments: " + message.getJumpUrl();

            if(attachments.get(0).isImage())
                return "Image: " + attachments.get(0).getUrl();

            if(attachments.get(0).isVideo())
                return "Video: " + attachments.get(0).getUrl();

            if(attachments.get(0).isSpoiler())
                return "Spoiler: " + attachments.get(0).getUrl();

            if(!message.getStickers().isEmpty() && message.getContentStripped().isEmpty())
                return "Sticker: " + message.getJumpUrl();
        }

        return message.getContentStripped();

    }

    public static void sendToDebugChannel(String message) {
        discordbot.getTextChannelById("887460231876083735").sendMessage(message).queue();
        //FIXME hardcoded text channel ID
    }

    public static void sendToDebugChannel(MessageEmbed message) {
        getDiscordbot().getGuildById("885683010123497494").getTextChannelById("887460231876083735").sendMessage(message).queue();
        //FIXME hardcoded text channel ID
    }

    public static void sendJoinOrQuitMessageToDiscord(Player player, boolean isJoining) {
        String playerUUID = player.getUniqueId().toString().replaceAll("-", "");
        String faceURL = "https://minotar.net/avatar/" + playerUUID + "/25"; //discord wants as string
        String playerName = player.getPlayerListName();
        int playerCount = Bukkit.getOnlinePlayers().size();

        EmbedBuilder joinMessage = new EmbedBuilder();
        joinMessage.setThumbnail(faceURL);
        if (isJoining) {
            joinMessage.setTitle(playerName + ConfigFile.getDiscordPlayerJoinMessage(getConfigFile()));
            joinMessage.setColor(0x42f545);

        } else { //is leaving
            joinMessage.setTitle(playerName + ConfigFile.getDiscordPlayerLeaveMessage(getConfigFile()));
            joinMessage.setColor(0xeb4034);
            playerCount--; //I removed this thinking it was unnecessary, and it is very necessary
        }

        if (playerCount <= 0) {
            joinMessage.setDescription("No players online");
            DiscordMessageHandler.sendToBothDiscordChannels(joinMessage.build());
            joinMessage.clear();
            return;
        }

        //Building description
        StringBuilder description = new StringBuilder("Now " + playerCount + " players online: \n");
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (!isJoining && p.getPlayerListName().equals(player.getPlayerListName()))
                continue; //if they are quitting leave out their name
            description.append("`").append(p.getPlayerListName()).append("`, ");
        }

        description = new StringBuilder(description.substring(0, description.length() - 2)); //removing the space and comma at the end
        joinMessage.setDescription(description.toString());

        DiscordMessageHandler.sendToBothDiscordChannels(joinMessage.build());

        joinMessage.clear();

    } //discord message
}
