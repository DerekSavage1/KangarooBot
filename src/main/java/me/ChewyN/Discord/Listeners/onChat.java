package me.ChewyN.Discord.Listeners;

import me.Main;
import me.ChewyN.Minecraft.Util.MinecraftMessageHandler;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;

import static me.Main.getInstance;
import static me.Main.getPluginConfig;

public class onChat extends ListenerAdapter {

    public void onMessageReceived(@NotNull MessageReceivedEvent e) {
        Message     discordMessage = e.getMessage();
        User        user = e.getAuthor();
        String      userName = user.getName();
        JDA discordbot = Main.getDiscordbot();

        if(user.isBot())
            return;

        if(e.getChannel().equals(DiscordChannelHandler.getDiscordMinecraftChannel(Main.getPluginConfig(), discordbot))) {
            String formattedMessage = formatMessage(e.getMessage());

            MinecraftMessageHandler.broadcastMessage("[" + ChatColor.AQUA + "Discord" + ChatColor.WHITE + "] " + ChatColor.AQUA + userName + ": " + ChatColor.WHITE + formattedMessage);

            DiscordMessageHandler.sendToAdminChannel("[Discord] " + userName + ": " + formattedMessage);

        }

        if(Main.getPluginConfigApi().isDiscordAdminChannelEnabled(getPluginConfig())) {
            if(e.getChannel().equals(DiscordChannelHandler.getDiscordAdminChannel(Main.getPluginConfig(), discordbot))) {
                for(Player p : getInstance().getServer().getOnlinePlayers()) {
                    if(p.hasPermission("adminChat.see")) {
                        p.sendMessage("[" + ChatColor.RED + "Admin Discord" + ChatColor.WHITE + "] " + ChatColor.RED + userName + ": " + ChatColor.WHITE + DiscordMessageHandler.formatMessage(discordMessage));
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
