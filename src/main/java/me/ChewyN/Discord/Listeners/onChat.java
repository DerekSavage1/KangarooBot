package me.ChewyN.Discord.Listeners;

import me.ChewyN.Data.ConfigFile;
import me.ChewyN.Main;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;

import static me.ChewyN.Main.discordbot;
import static me.ChewyN.Main.getInstance;

public class onChat extends ListenerAdapter {

    public void onMessageReceived(@NotNull MessageReceivedEvent e) {
        Message     discordMessage = e.getMessage();
        User        user = e.getAuthor();
        String      userName = user.getName();

        if(user.isBot())
            return;

        if(e.getChannel().equals(ConfigFile.getMinecraftChannel(discordbot))) {
            String formattedMessage = formatMessage(e.getMessage());

            getInstance().getServer().broadcastMessage("[" + ChatColor.AQUA + "Discord" + ChatColor.WHITE + "] " + ChatColor.AQUA + userName + ": " + ChatColor.WHITE + formattedMessage);

            if(!Main.getConfigFile().isAdminChannelEnabled() && ConfigFile.getAdminChannel(discordbot) != null) {
                ConfigFile.getAdminChannel(discordbot).sendMessage("[Discord] " + userName + ": " + formattedMessage).queue();
            }

        }
        if(!Main.getConfigFile().isAdminChannelEnabled() && ConfigFile.getAdminChannel(discordbot) != null) {
            if(e.getChannel().equals(ConfigFile.getAdminChannel(discordbot))) {
                for(Player p : getInstance().getServer().getOnlinePlayers()) {
                    if(p.hasPermission("adminChat.see")) {
                        p.sendMessage("[" + ChatColor.RED + "Admin" + ChatColor.WHITE + "] " + ChatColor.RED + userName + ": " + ChatColor.WHITE + DiscordMessageHandler.formatMessage(discordMessage));
                    }
                }
            }
        }
    }

    private String formatMessage(Message message) {

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
