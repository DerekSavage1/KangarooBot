package me.ChewyN.Discord.Util;

import net.dv8tion.jda.api.entities.TextChannel;

public class TextChannels {

    public static TextChannel getGameTextChannel() {
        return GAME_TEXT_CHANNEL;
    }

    public static void setGameTextChannel(TextChannel gameTextChannel) {
        GAME_TEXT_CHANNEL = gameTextChannel;
    }

    private static TextChannel GAME_TEXT_CHANNEL;

    public static TextChannel getAdminTextChannel() {
        return ADMIN_TEXT_CHANNEL;
    }

    public static void setAdminTextChannel(TextChannel adminTextChannel) {
        ADMIN_TEXT_CHANNEL = adminTextChannel;
    }

    private static TextChannel ADMIN_TEXT_CHANNEL;

    public static void sendToDiscord(String username, String message) {
        GAME_TEXT_CHANNEL.sendMessage("`" + username + " »` " + message).queue();
        ADMIN_TEXT_CHANNEL.sendMessage("`" + username + " »` " + message).queue();
    }

    public static void sendToAdminDiscord(String username, String message) {
        ADMIN_TEXT_CHANNEL.sendMessage("`" + username + " »` " + message).queue();
    }


}
