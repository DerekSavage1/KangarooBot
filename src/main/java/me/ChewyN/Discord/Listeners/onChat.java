package me.ChewyN.Discord.Listeners;

import me.ChewyN.Main;
import me.ChewyN.Discord.Util.TextChannels;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.bukkit.ChatColor;
import org.jetbrains.annotations.NotNull;

public class onChat extends ListenerAdapter {

    public void onMessageReceived(@NotNull MessageReceivedEvent e) {
        String      DMessage = e.getMessage().getContentRaw();
        User        user = e.getAuthor();
        String      userName = user.getName();
        TextChannel GAME_TEXT_CHANNEL = me.ChewyN.Discord.Util.TextChannels.getGameTextChannel();
        if(e.getChannel().equals(GAME_TEXT_CHANNEL)) {
            if(!user.isBot()) {
                Main.getInstance().getServer().broadcastMessage("[" + ChatColor.AQUA + "Discord" + ChatColor.WHITE + "] " + ChatColor.AQUA + userName + ": " + ChatColor.WHITE + DMessage);

            }
        }
    }

}
