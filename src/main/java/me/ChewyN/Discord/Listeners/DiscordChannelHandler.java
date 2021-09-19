package me.ChewyN.Discord.Listeners;

import me.ChewyN.Data.Configuration.PluginConfigYml;
import me.Main;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.GuildChannel;
import net.dv8tion.jda.api.entities.TextChannel;
import org.jetbrains.annotations.NotNull;

import java.util.logging.Level;

public class DiscordChannelHandler {


    @NotNull
    public static TextChannel getDiscordAdminChannel(@NotNull PluginConfigYml pluginConfig, @NotNull JDA discordbot) {
        TextChannel textChannel = discordbot.getTextChannelById(pluginConfig.getConfigApi().getDiscordAdminChannelID(Main.getPluginConfig()));

        if(textChannel == null && Main.getPluginConfigApi().isDiscordAdminChannelEnabled(Main.getPluginConfig())) {
            Main.log(Level.WARNING, "Admin channel is enabled, but admin chat is not found!");
            Main.log(Level.WARNING, "Setting admin channel to disabled...");
            Main.getPluginConfigApi().setDiscordAdminChannelEnabled(false, Main.getPluginConfig());
            Main.getInstance().getServer().getPluginManager().disablePlugin(Main.getInstance());
        }
        assert textChannel!= null;
        return textChannel;
    }

    @NotNull
    public static TextChannel getDiscordMinecraftChannel(@NotNull PluginConfigYml pluginConfig, @NotNull JDA discordbot) {
        TextChannel textChannel = discordbot.getTextChannelById(pluginConfig.getConfigApi().getDiscordMinecraftChannelID(Main.getPluginConfig()));

        if(textChannel == null) {
            Main.log(Level.SEVERE, "You must fill out all mandatory fields in config.yml!");
            Main.log(Level.SEVERE, "Minecraft Channel invalid! Shutting down...");
            Main.getInstance().getServer().getPluginManager().disablePlugin(Main.getInstance());
        }

        assert textChannel != null;
        return textChannel;
    }

    public static GuildChannel getDiscordWelcomeChannel(@NotNull PluginConfigYml pluginConfig, @NotNull JDA discordbot) {
        TextChannel textChannel = discordbot.getTextChannelById(pluginConfig.getConfigApi().getDiscordMinecraftChannelID(Main.getPluginConfig()));

        if(textChannel == null) {
            Main.log(Level.SEVERE, "You must fill out all mandatory fields in config.yml!");
            Main.log(Level.SEVERE, "Welcome Channel invalid! Shutting down...");
            Main.getInstance().getServer().getPluginManager().disablePlugin(Main.getInstance());
        }

        assert textChannel != null;
        return textChannel;
    }
}
