package me.ChewyN.Discord.Util;

import net.dv8tion.jda.api.entities.TextChannel;

public class TextChannels {

    public static TextChannel GAME_TEXT_CHANNEL;
    public static TextChannel ADMIN_TEXT_CHANNEL;

    public static void sendToDiscord(String username, String message) {
        GAME_TEXT_CHANNEL.sendMessage("`" + username + " »` " + message).queue();
        ADMIN_TEXT_CHANNEL.sendMessage("`" + username + " »` " + message).queue();
    }

    public static void sendToAdminDiscord(String username, String message) {
        ADMIN_TEXT_CHANNEL.sendMessage("`" + username + " »` " + message).queue();
    }


}
