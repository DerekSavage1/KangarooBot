package me.ChewyN.Data.Configuration;

import me.ChewyN.Data.AbstractFile;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class PluginConfigAPI {

    /**
     *     This is encapsulation if you are interested...
     *     Getters return unprocessed data only!
     *
     *     If you want processed data, you better get a handle on it. (Get it? Handler?)
     *
     *     To add getters or setters, make sure they are in the same order as they are here
     *     If you don't know how, just ask me to do it instead
     *     It keeps it pretty, Thank you :-)
     *
     *
     *          ORDER OF GETTERS AND SETTERS
     *
     *         =====Mandatory===
     *         private static String discordMinecraftChannel;
     *         private static String discordBotToken;
     *
     *         ====Extra functionality====
     *         private static String discordWelcomeChannel;
     *         private static String discordAdminChannel;
     *         private static List<String> deathMessages;
     *         private static String discordOnlineRoleName;
     *         private static boolean logDeathInfoInAdminChannel;
     *
     *
     *         ====Toggleable====
     *         private static boolean discordAdminChannelEnabled;
     *         private static boolean sendDeathMessagesToDiscord;
     *         private static boolean discordCommandEnabled;
     *         private static boolean discordJoinLeaveMessagesEnabled;
     *
     *         ====Customization====
     *         private static String customDiscordBotStatus;
     *         private static String customDiscordDeathMessageDescription;
     *         private static String customDiscordMessageOnStartup;
     *         private static String customDiscordMessageOnShutdown;
     *         private static String customDiscordPlayerJoinMessage;
     *         private static String customDiscordPlayerLeaveMessage;
     *
     *         ====Should probably be in a different plugin====
     *         private static boolean minecraftJoinMessageEnabled;
     *         private static String minecraftJoinMessage;
     *         private static boolean minecraftBackCommandEnabled;
     *         private static boolean minecraftCenterDeathMessages;
     *         private static boolean minecraftWelcomeMessageEnabled;
     *
     *         ====Dev tools====
     *         private boolean debugEnabled;
     */

    public String getDiscordWelcomeChannelID(@NotNull PluginConfigYml pluginConfig) {
        return pluginConfig.getYamlConfig().getString("discordWelcomeChannelID");
    }

    public void setDiscordWelcomeChannelID(String discordWelcomeChannelID, @NotNull PluginConfigYml pluginConfig) {
        pluginConfig.getYamlConfig().set("discordWelcomeChannelID", discordWelcomeChannelID);
        AbstractFile.save(pluginConfig);
    }

    public String getDiscordMinecraftChannelID(@NotNull PluginConfigYml pluginConfig) {
        return pluginConfig.getYamlConfig().getString("discordMinecraftChannelID");
    }

    public void setDiscordMinecraftChannelID(String discordMinecraftChannelID, @NotNull PluginConfigYml pluginConfig) {
        pluginConfig.getYamlConfig().set("discordMinecraftChannelID", discordMinecraftChannelID);
        AbstractFile.save(pluginConfig);
    }

    public String getDiscordBotToken(@NotNull PluginConfigYml pluginConfig) {
        return pluginConfig.getYamlConfig().getString("discordBotToken");
    }

    public void setDiscordBotToken(String discordBotToken, @NotNull PluginConfigYml pluginConfig) {
        pluginConfig.getYamlConfig().set("discordBotToken", discordBotToken);
        AbstractFile.save(pluginConfig);
    }

    public String getDiscordAdminChannelID(@NotNull PluginConfigYml pluginConfig) {
        return pluginConfig.getYamlConfig().getString("discordAdminChannelID");
    }

    public void setDiscordAdminChannelID(String discordAdminChannelID, @NotNull PluginConfigYml pluginConfig) {
        pluginConfig.getYamlConfig().set("discordAdminChannelID", discordAdminChannelID);
        AbstractFile.save(pluginConfig);
    }

    public List<String> getDeathMessages(@NotNull PluginConfigYml pluginConfig) {
        return pluginConfig.getYamlConfig().getStringList("deathMessages");
    }

    public void setDeathMessages(List<String> deathMessages, @NotNull PluginConfigYml pluginConfig) {
        pluginConfig.getYamlConfig().set("deathMessages", deathMessages);
        AbstractFile.save(pluginConfig);
    }

    public String getDiscordOnlineRoleName(@NotNull PluginConfigYml pluginConfig) {
        return pluginConfig.getYamlConfig().getString("discordOnlineRoleName");
    }

    public void setDiscordOnlineRoleName(String discordOnlineRoleName, @NotNull PluginConfigYml pluginConfig) {
        pluginConfig.getYamlConfig().set("discordOnlineRoleName", discordOnlineRoleName);
        AbstractFile.save(pluginConfig);
    }

    public boolean isLogDeathInfoInAdminChannel(@NotNull PluginConfigYml pluginConfig) {
        return pluginConfig.getYamlConfig().getBoolean("logDeathInfoInAdminChannel");
    }

    public void setLogDeathInfoInAdminChannel(boolean logDeathInfoInAdminChannel, @NotNull PluginConfigYml pluginConfig) {
        pluginConfig.getYamlConfig().set("logDeathInfoInAdminChannel", logDeathInfoInAdminChannel);
        AbstractFile.save(pluginConfig);
    }

    public boolean isMinecraftJoinMessageEnabled(@NotNull PluginConfigYml pluginConfig) {
        return pluginConfig.getYamlConfig().getBoolean("minecraftJoinMessageEnabled");
    }

    public void setMinecraftJoinMessageEnabled(boolean minecraftJoinMessageEnabled, @NotNull PluginConfigYml pluginConfig) {
        pluginConfig.getYamlConfig().set("minecraftJoinMessageEnabled", minecraftJoinMessageEnabled);
        AbstractFile.save(pluginConfig);
    }

    public boolean isDiscordAdminChannelEnabled(@NotNull PluginConfigYml pluginConfig) {
        return pluginConfig.getYamlConfig().getBoolean("discordAdminChannelEnabled");
    }

    public void setDiscordAdminChannelEnabled(boolean discordAdminChannelEnabled, @NotNull PluginConfigYml pluginConfig) {
        pluginConfig.getYamlConfig().set("discordAdminChannelEnabled", discordAdminChannelEnabled);
        AbstractFile.save(pluginConfig);
    }

    public boolean isSendDeathMessagesToDiscordEnabled(@NotNull PluginConfigYml pluginConfig) {
        return pluginConfig.getYamlConfig().getBoolean("sendDeathMessagesToDiscord");
    }

    public void setSendDeathMessagesToDiscordEnabled(boolean sendDeathMessagesToDiscord, @NotNull PluginConfigYml pluginConfig) {
        pluginConfig.getYamlConfig().set("sendDeathMessagesToDiscord", sendDeathMessagesToDiscord);
        AbstractFile.save(pluginConfig);
    }

    public boolean isDiscordCommandEnabled(@NotNull PluginConfigYml pluginConfig) {
        return pluginConfig.getYamlConfig().getBoolean("discordCommandEnabled");
    }

    public void setDiscordCommandEnabled(boolean discordCommandEnabled, @NotNull PluginConfigYml pluginConfig) {
        pluginConfig.getYamlConfig().set("discordCommandEnabled", discordCommandEnabled);
        AbstractFile.save(pluginConfig);
    }

    public boolean isDiscordJoinLeaveMessagesEnabled(@NotNull PluginConfigYml pluginConfig) {
        return pluginConfig.getYamlConfig().getBoolean("discordJoinLeaveMessagesEnabled");
    }

    public void setDiscordJoinLeaveMessagesEnabled(boolean discordJoinLeaveMessagesEnabled, @NotNull PluginConfigYml pluginConfig) {
        pluginConfig.getYamlConfig().set("discordJoinLeaveMessagesEnabled", discordJoinLeaveMessagesEnabled);
        AbstractFile.save(pluginConfig);
    }

    public String getCustomDiscordBotStatus(@NotNull PluginConfigYml pluginConfig) {
        return pluginConfig.getYamlConfig().getString("customDiscordBotStatus");
    }

    public void setCustomDiscordBotStatus(String customDiscordBotStatus, @NotNull PluginConfigYml pluginConfig) {
        pluginConfig.getYamlConfig().set("customDiscordBotStatus", customDiscordBotStatus);
        AbstractFile.save(pluginConfig);
    }

    public String getCustomDiscordDeathMessageDescription(@NotNull PluginConfigYml pluginConfig) {
        return pluginConfig.getYamlConfig().getString("customDiscordDeathMessageDescription");
    }

    public void setCustomDiscordDeathMessageDescription(String customDiscordDeathMessageDescription, @NotNull PluginConfigYml pluginConfig) {
        pluginConfig.getYamlConfig().set("customDiscordDeathMessageDescription", customDiscordDeathMessageDescription);
        AbstractFile.save(pluginConfig);
    }

    public String getCustomDiscordMessageOnStartup(@NotNull PluginConfigYml pluginConfig) {
        return pluginConfig.getYamlConfig().getString("customDiscordMessageOnStartup");
    }

    public void setCustomDiscordMessageOnStartup(String customDiscordMessageOnStartup, @NotNull PluginConfigYml pluginConfig) {
        pluginConfig.getYamlConfig().set("customDiscordMessageOnStartup", customDiscordMessageOnStartup);
        AbstractFile.save(pluginConfig);
    }

    public String getCustomDiscordMessageOnShutdown(@NotNull PluginConfigYml pluginConfig) {
        return pluginConfig.getYamlConfig().getString("customDiscordMessageOnShutdown");
    }

    public void setCustomDiscordMessageOnShutdown(String customDiscordMessageOnShutdown, @NotNull PluginConfigYml pluginConfig) {
        pluginConfig.getYamlConfig().set("customDiscordMessageOnShutdown", customDiscordMessageOnShutdown);
        AbstractFile.save(pluginConfig);
    }

    public String getCustomDiscordPlayerJoinMessage(@NotNull PluginConfigYml pluginConfig) {
        return pluginConfig.getYamlConfig().getString("customDiscordPlayerJoinMessage");
    }

    public void setCustomDiscordPlayerJoinMessage(String customDiscordPlayerJoinMessage, @NotNull PluginConfigYml pluginConfig) {
        pluginConfig.getYamlConfig().set("customDiscordPlayerJoinMessage", customDiscordPlayerJoinMessage);
        AbstractFile.save(pluginConfig);
    }

    public String getCustomDiscordPlayerLeaveMessage(@NotNull PluginConfigYml pluginConfig) {
        return pluginConfig.getYamlConfig().getString("customDiscordPlayerLeaveMessage");
    }

    public void setCustomDiscordPlayerLeaveMessage(String customDiscordPlayerLeaveMessage, @NotNull PluginConfigYml pluginConfig) {
        pluginConfig.getYamlConfig().set("customDiscordPlayerLeaveMessage", customDiscordPlayerLeaveMessage);
        AbstractFile.save(pluginConfig);
    }

    public String getMinecraftJoinMessage(@NotNull PluginConfigYml pluginConfig) {
        return pluginConfig.getYamlConfig().getString("minecraftJoinMessage");
    }

    public void setMinecraftJoinMessage(String minecraftJoinMessage, @NotNull PluginConfigYml pluginConfig) {
        pluginConfig.getYamlConfig().set("minecraftJoinMessage", minecraftJoinMessage);
        AbstractFile.save(pluginConfig);
    }

    public boolean isMinecraftBackCommandEnabled(@NotNull PluginConfigYml pluginConfig) {
        return pluginConfig.getYamlConfig().getBoolean("minecraftBackCommandEnabled");
    }

    public void setMinecraftBackCommandEnabled(boolean minecraftBackCommandEnabled, @NotNull PluginConfigYml pluginConfig) {
        pluginConfig.getYamlConfig().set("minecraftBackCommandEnabled", minecraftBackCommandEnabled);
        AbstractFile.save(pluginConfig);
    }

    public boolean isMinecraftCenterDeathMessages(@NotNull PluginConfigYml pluginConfig) {
        return pluginConfig.getYamlConfig().getBoolean("minecraftCenterDeathMessages");
    }

    public void setMinecraftCenterDeathMessages(boolean minecraftCenterDeathMessages, @NotNull PluginConfigYml pluginConfig) {
        pluginConfig.getYamlConfig().set("minecraftCenterDeathMessages", minecraftCenterDeathMessages);
        AbstractFile.save(pluginConfig);
    }

    public boolean isMinecraftWelcomeMessageEnabled(@NotNull PluginConfigYml pluginConfig) {
        return pluginConfig.getYamlConfig().getBoolean("minecraftWelcomeMessageEnabled");
    }

    public void setMinecraftWelcomeMessageEnabled(boolean minecraftWelcomeMessageEnabled, @NotNull PluginConfigYml pluginConfig) {
        pluginConfig.getYamlConfig().set("minecraftWelcomeMessageEnabled", minecraftWelcomeMessageEnabled);
        AbstractFile.save(pluginConfig);
    }

    public boolean isDebugEnabled(@NotNull PluginConfigYml pluginConfig) {
        return pluginConfig.getYamlConfig().getBoolean("debugEnabled");
    }

    public void setDebugEnabled(boolean debugEnabled, @NotNull PluginConfigYml pluginConfig) {
        pluginConfig.getYamlConfig().set("debugEnabled", debugEnabled);
        AbstractFile.save(pluginConfig);
    }

    public String getDiscordDebugGuildID(PluginConfigYml pluginConfig) {
        return pluginConfig.getYamlConfig().getString("discordDebugGuildID");
    }

    public String getDiscordDebugChannelID(PluginConfigYml pluginConfig) {
        return pluginConfig.getYamlConfig().getString("discordDebugChannelID");
    }

}
