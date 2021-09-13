package me.ChewyN.Discord.Listeners;

import me.ChewyN.Data.ConfigFile;
import me.ChewyN.Main;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.List;

import static me.ChewyN.Main.getDiscordbot;

public class DiscordMessageHandler {

    final private static JDA DISCORDBOT = Main.getDiscordbot();
    final private static TextChannel MINECRAFT_CHANNEL = ConfigFile.getMinecraftChannel(DISCORDBOT);

    public static void sendToMinecraftChannel(String message) {
        MINECRAFT_CHANNEL.sendMessage(message).queue();
    }

    public static void sendToMinecraftChannel(String message, String username) {
        MINECRAFT_CHANNEL.sendMessage("`" + username + " »` " + message).queue(); //TODO: make text format customizable in config
    }

    public static void sendToAdminChannel(String message) {
        if(Main.getConfigFile().isAdminChannelEnabled()) {
                ConfigFile.getAdminChannel(DISCORDBOT).sendMessage(message).queue();
        }
    }

    public static void sendToAdminChannel(String message, String username) {
        if(Main.getConfigFile().isAdminChannelEnabled()) {
            ConfigFile.getAdminChannel(DISCORDBOT).sendMessage("`" + username + " »` " + message).queue();
        }
    }

    public static void sendToBothDiscordChannels(String message) {
        sendToAdminChannel(message);
        sendToMinecraftChannel(message);
    }

    public static void sendToBothDiscordChannels(String username, String message) {
        sendToAdminChannel(message,username);
        sendToMinecraftChannel(message, username);
    }

    public static void sendJoinOrQuitMessageToDiscord(Player player, boolean isJoining) {
        String			playerUUID = player.getUniqueId().toString().replaceAll("-","");
        String			faceURL = "https://minotar.net/avatar/"+playerUUID+"/25"; //discord wants as string
        String			playerName = player.getPlayerListName();
        int				playerCount = Bukkit.getOnlinePlayers().size();
        final TextChannel     DISCORD_MINECRAFT_CHANNEL = ConfigFile.getMinecraftChannel(getDiscordbot());

        EmbedBuilder joinMessage = new EmbedBuilder();
        joinMessage.setThumbnail(faceURL);
        if(isJoining) {
            joinMessage.setTitle(playerName + " has joined the server");
            joinMessage.setColor(0x42f545);
        } else {
            joinMessage.setTitle(playerName + " has left the server");
            joinMessage.setColor(0xeb4034);
        }

        if(playerCount <= 0) {
            joinMessage.setDescription("No players online");
            assert DISCORD_MINECRAFT_CHANNEL != null;
            DISCORD_MINECRAFT_CHANNEL.sendMessage(joinMessage.build()).queue();
            joinMessage.clear();
            return;
        }

        //Building description
        StringBuilder description = new StringBuilder("Now " + playerCount + " players online: \n");
        for( Player p : Bukkit.getOnlinePlayers()) {
            if(!isJoining && p.getPlayerListName().equals(player.getPlayerListName())) continue; //if they are quitting leave out their name
            description.append("`").append(p.getPlayerListName()).append("`, ");
        }

        description = new StringBuilder(description.substring(0, description.length() - 2)); //removing the space and comma at the end
        joinMessage.setDescription(description.toString());

        assert DISCORD_MINECRAFT_CHANNEL != null;
        DISCORD_MINECRAFT_CHANNEL.sendMessage(joinMessage.build()).queue();

        joinMessage.clear();
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


}