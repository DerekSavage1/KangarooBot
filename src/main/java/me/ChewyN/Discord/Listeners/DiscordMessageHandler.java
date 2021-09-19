package me.ChewyN.Discord.Listeners;

import me.ChewyN.Main;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.TextChannel;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.List;

import static me.ChewyN.Main.*;

public class DiscordMessageHandler {

    final private static JDA discordbot = Main.getDiscordbot();

    public static void sendToMinecraftChannel(MessageEmbed message) {
        DiscordChannelHandler.getDiscordMinecraftChannel(getPluginConfig(), discordbot).sendMessage(message).queue();
    }

    public static void sendToMinecraftChannel(String username, String message) {
        DiscordChannelHandler.getDiscordMinecraftChannel(getPluginConfig(), discordbot).sendMessage("`" + username + " :`" + message).queue();
    }

    public static void sendToAdminChannel(String message) {
        if(!Main.getPluginConfigApi().isDiscordAdminChannelEnabled(Main.getPluginConfig()))
            return;

        TextChannel adminChannel = DiscordChannelHandler.getDiscordAdminChannel(getPluginConfig(), discordbot);

        Main.getPluginConfigApi().isDiscordAdminChannelEnabled(getPluginConfig());
        if (Main.getPluginConfigApi().isDiscordAdminChannelEnabled(getPluginConfig())) {
            adminChannel.sendMessage(message).queue();
        }
    }

    public static void sendToAdminChannel(String username, String message) {
        if(!Main.getPluginConfigApi().isDiscordAdminChannelEnabled(Main.getPluginConfig()))
            return;

        TextChannel adminChannel = DiscordChannelHandler.getDiscordAdminChannel(getPluginConfig(), discordbot);

        Main.getPluginConfigApi().isDiscordAdminChannelEnabled(getPluginConfig());
        if (Main.getPluginConfigApi().isDiscordAdminChannelEnabled(getPluginConfig())) {
            adminChannel.sendMessage("`" + username + " :`" + message).queue();
        }
    }

    public static void sendToAdminChannel(MessageEmbed message) {
        if(!Main.getPluginConfigApi().isDiscordAdminChannelEnabled(Main.getPluginConfig()))
            return;

        TextChannel adminChannel = DiscordChannelHandler.getDiscordAdminChannel(getPluginConfig(), discordbot);
        Main.getPluginConfigApi().isDiscordAdminChannelEnabled(getPluginConfig());
        if (Main.getPluginConfigApi().isDiscordAdminChannelEnabled(getPluginConfig())) {
            adminChannel.sendMessage(message).queue();
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
        try {
            getDiscordbot().getGuildById(Main.getPluginConfigApi().getDiscordDebugGuildID(getPluginConfig()))
                    .getTextChannelById(getPluginConfigApi().getDiscordDebugChannelID(getPluginConfig()))
                    .sendMessage(message).queue();
        } catch(NullPointerException exception) {
            Main.debug("No Debug channel/guild found!");
        }
    }

    public static void sendToDebugChannel(MessageEmbed message) {
        try {
            getDiscordbot().getGuildById(Main.getPluginConfigApi().getDiscordDebugGuildID(getPluginConfig()))
                            .getTextChannelById(getPluginConfigApi().getDiscordDebugChannelID(getPluginConfig()))
                            .sendMessage(message).queue();
        } catch(NullPointerException exception) {
            Main.debug("No Debug channel/guild found!");
        }

    }

    public static void sendJoinOrQuitMessageToDiscord(Player player, boolean isJoining) {
        String playerUUID = player.getUniqueId().toString().replaceAll("-", "");
        String faceURL = "https://minotar.net/avatar/" + playerUUID + "/25"; //discord wants as string
        String playerName = player.getPlayerListName();
        int playerCount = Bukkit.getOnlinePlayers().size();

        EmbedBuilder joinMessage = new EmbedBuilder();
        joinMessage.setThumbnail(faceURL);
        if (isJoining) {
            joinMessage.setTitle(playerName + getPluginConfigApi().getCustomDiscordPlayerJoinMessage(getPluginConfig()));
            joinMessage.setColor(0x42f545);

        } else { //is leaving
            joinMessage.setTitle(playerName + getPluginConfigApi().getCustomDiscordPlayerLeaveMessage(getPluginConfig()));
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
