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

    public String getDiscordWelcomeChannel(@NotNull PluginConfigYml pluginConfig) {
        return pluginConfig.getYamlConfig().getString("getDiscordWelcomeChannel");
    }

    public void setDiscordWelcomeChannel(String discordWelcomeChannel, @NotNull PluginConfigYml pluginConfig) {
        pluginConfig.getYamlConfig().set("discordWelcomeChannel", discordWelcomeChannel);
        AbstractFile.save(pluginConfig);
    }

    public String getDiscordMinecraftChannel(@NotNull PluginConfigYml pluginConfig) {
        return pluginConfig.getYamlConfig().getString("getDiscordMinecraftChannel");
    }

    public void setDiscordMinecraftChannel(String discordMinecraftChannel, @NotNull PluginConfigYml pluginConfig) {
        pluginConfig.getYamlConfig().set("discordMinecraftChannel", discordMinecraftChannel);
        AbstractFile.save(pluginConfig);
    }

    public String getDiscordBotToken(@NotNull PluginConfigYml pluginConfig) {
        return pluginConfig.getYamlConfig().getString("getDiscordBotToken");
    }

    public void setDiscordBotToken(String discordBotToken, @NotNull PluginConfigYml pluginConfig) {
        pluginConfig.getYamlConfig().set("discordBotToken", discordBotToken);
        AbstractFile.save(pluginConfig);
    }

    public String getDiscordAdminChannel(@NotNull PluginConfigYml pluginConfig) {
        return pluginConfig.getYamlConfig().getString("getDiscordAdminChannel");
    }

    public void setDiscordAdminChannel(String discordAdminChannel, @NotNull PluginConfigYml pluginConfig) {
        pluginConfig.getYamlConfig().set("discordAdminChannel", discordAdminChannel);
        AbstractFile.save(pluginConfig);
    }

    public List<String> getDeathMessages(@NotNull PluginConfigYml pluginConfig) {
        return pluginConfig.getYamlConfig().getStringList("getDeathMessages");
    }

    public void setDeathMessages(List<String> deathMessages, @NotNull PluginConfigYml pluginConfig) {
        pluginConfig.getYamlConfig().set("deathMessages", deathMessages);
        AbstractFile.save(pluginConfig);
    }

    public String getDiscordOnlineRoleName(@NotNull PluginConfigYml pluginConfig) {
        return pluginConfig.getYamlConfig().getString("getDiscordOnlineRoleName");
    }

    public void setDiscordOnlineRoleName(String discordOnlineRoleName, @NotNull PluginConfigYml pluginConfig) {
        pluginConfig.getYamlConfig().set("discordOnlineRoleName", discordOnlineRoleName);
        AbstractFile.save(pluginConfig);
    }

    public boolean isLogDeathInfoInAdminChannel(@NotNull PluginConfigYml pluginConfig) {
        return pluginConfig.getYamlConfig().getBoolean("isLogDeathInfoInAdminChannel");
    }

    public void setLogDeathInfoInAdminChannel(boolean logDeathInfoInAdminChannel, @NotNull PluginConfigYml pluginConfig) {
        pluginConfig.getYamlConfig().set("logDeathInfoInAdminChannel", logDeathInfoInAdminChannel);
        AbstractFile.save(pluginConfig);
    }

    public boolean isMinecraftJoinMessageEnabled(@NotNull PluginConfigYml pluginConfig) {
        return pluginConfig.getYamlConfig().getBoolean("isMinecraftJoinMessageEnabled");
    }

    public void setMinecraftJoinMessageEnabled(boolean minecraftJoinMessageEnabled, @NotNull PluginConfigYml pluginConfig) {
        pluginConfig.getYamlConfig().set("minecraftJoinMessageEnabled", minecraftJoinMessageEnabled);
        AbstractFile.save(pluginConfig);
    }

    public boolean isDiscordAdminChannelEnabled(@NotNull PluginConfigYml pluginConfig) {
        return pluginConfig.getYamlConfig().getBoolean("isDiscordAdminChannelEnabled");
    }

    public void setDiscordAdminChannelEnabled(boolean discordAdminChannelEnabled, @NotNull PluginConfigYml pluginConfig) {
        pluginConfig.getYamlConfig().set("discordAdminChannelEnabled", discordAdminChannelEnabled);
        AbstractFile.save(pluginConfig);
    }

    public boolean isSendDeathMessagesToDiscord(@NotNull PluginConfigYml pluginConfig) {
        return pluginConfig.getYamlConfig().getBoolean("isSendDeathMessagesToDiscord");
    }

    public void setSendDeathMessagesToDiscord(boolean sendDeathMessagesToDiscord, @NotNull PluginConfigYml pluginConfig) {
        pluginConfig.getYamlConfig().set("sendDeathMessagesToDiscord", sendDeathMessagesToDiscord);
        AbstractFile.save(pluginConfig);
    }

    public boolean isDiscordCommandEnabled(@NotNull PluginConfigYml pluginConfig) {
        return pluginConfig.getYamlConfig().getBoolean("isDiscordCommandEnabled");
    }

    public void setDiscordCommandEnabled(boolean discordCommandEnabled, @NotNull PluginConfigYml pluginConfig) {
        pluginConfig.getYamlConfig().set("discordCommandEnabled", discordCommandEnabled);
        AbstractFile.save(pluginConfig);
    }

    public boolean isDiscordJoinLeaveMessagesEnabled(@NotNull PluginConfigYml pluginConfig) {
        return pluginConfig.getYamlConfig().getBoolean("isDiscordJoinLeaveMessagesEnabled");
    }

    public void setDiscordJoinLeaveMessagesEnabled(boolean discordJoinLeaveMessagesEnabled, @NotNull PluginConfigYml pluginConfig) {
        pluginConfig.getYamlConfig().set("discordJoinLeaveMessagesEnabled", discordJoinLeaveMessagesEnabled);
        AbstractFile.save(pluginConfig);
    }

    public String getCustomDiscordBotStatus(@NotNull PluginConfigYml pluginConfig) {
        return pluginConfig.getYamlConfig().getString("getCustomDiscordBotStatus");
    }

    public void setCustomDiscordBotStatus(String customDiscordBotStatus, @NotNull PluginConfigYml pluginConfig) {
        pluginConfig.getYamlConfig().set("customDiscordBotStatus", customDiscordBotStatus);
        AbstractFile.save(pluginConfig);
    }

    public String getCustomDiscordDeathMessageDescription(@NotNull PluginConfigYml pluginConfig) {
        return pluginConfig.getYamlConfig().getString("getCustomDiscordDeathMessageDescription");
    }

    public void setCustomDiscordDeathMessageDescription(String customDiscordDeathMessageDescription, @NotNull PluginConfigYml pluginConfig) {
        pluginConfig.getYamlConfig().set("customDiscordDeathMessageDescription", customDiscordDeathMessageDescription);
        AbstractFile.save(pluginConfig);
    }

    public String getCustomDiscordMessageOnStartup(@NotNull PluginConfigYml pluginConfig) {
        return pluginConfig.getYamlConfig().getString("getCustomDiscordMessageOnStartup");
    }

    public void setCustomDiscordMessageOnStartup(String customDiscordMessageOnStartup, @NotNull PluginConfigYml pluginConfig) {
        pluginConfig.getYamlConfig().set("customDiscordMessageOnStartup", customDiscordMessageOnStartup);
        AbstractFile.save(pluginConfig);
    }

    public String getCustomDiscordMessageOnShutdown(@NotNull PluginConfigYml pluginConfig) {
        return pluginConfig.getYamlConfig().getString("getCustomDiscordMessageOnShutdown");
    }

    public void setCustomDiscordMessageOnShutdown(String customDiscordMessageOnShutdown, @NotNull PluginConfigYml pluginConfig) {
        pluginConfig.getYamlConfig().set("customDiscordMessageOnShutdown", customDiscordMessageOnShutdown);
        AbstractFile.save(pluginConfig);
    }

    public String getCustomDiscordPlayerJoinMessage(@NotNull PluginConfigYml pluginConfig) {
        return pluginConfig.getYamlConfig().getString("getCustomDiscordPlayerJoinMessage");
    }

    public void setCustomDiscordPlayerJoinMessage(String customDiscordPlayerJoinMessage, @NotNull PluginConfigYml pluginConfig) {
        pluginConfig.getYamlConfig().set("customDiscordPlayerJoinMessage", customDiscordPlayerJoinMessage);
        AbstractFile.save(pluginConfig);
    }

    public String getCustomDiscordPlayerLeaveMessage(@NotNull PluginConfigYml pluginConfig) {
        return pluginConfig.getYamlConfig().getString("getCustomDiscordPlayerLeaveMessage");
    }

    public void setCustomDiscordPlayerLeaveMessage(String customDiscordPlayerLeaveMessage, @NotNull PluginConfigYml pluginConfig) {
        pluginConfig.getYamlConfig().set("customDiscordPlayerLeaveMessage", customDiscordPlayerLeaveMessage);
        AbstractFile.save(pluginConfig);
    }

    public String getMinecraftJoinMessage(@NotNull PluginConfigYml pluginConfig) {
        return pluginConfig.getYamlConfig().getString("getMinecraftJoinMessage");
    }

    public void setMinecraftJoinMessage(String minecraftJoinMessage, @NotNull PluginConfigYml pluginConfig) {
        pluginConfig.getYamlConfig().set("minecraftJoinMessage", minecraftJoinMessage);
        AbstractFile.save(pluginConfig);
    }

    public boolean isMinecraftBackCommandEnabled(@NotNull PluginConfigYml pluginConfig) {
        return pluginConfig.getYamlConfig().getBoolean("isMinecraftBackCommandEnabled");
    }

    public void setMinecraftBackCommandEnabled(boolean minecraftBackCommandEnabled, @NotNull PluginConfigYml pluginConfig) {
        pluginConfig.getYamlConfig().set("minecraftBackCommandEnabled", minecraftBackCommandEnabled);
        AbstractFile.save(pluginConfig);
    }

    public boolean isMinecraftCenterDeathMessages(@NotNull PluginConfigYml pluginConfig) {
        return pluginConfig.getYamlConfig().getBoolean("isMinecraftCenterDeathMessages");
    }

    public void setMinecraftCenterDeathMessages(boolean minecraftCenterDeathMessages, @NotNull PluginConfigYml pluginConfig) {
        pluginConfig.getYamlConfig().set("minecraftCenterDeathMessages", minecraftCenterDeathMessages);
        AbstractFile.save(pluginConfig);
    }

    public boolean isMinecraftWelcomeMessageEnabled(@NotNull PluginConfigYml pluginConfig) {
        return pluginConfig.getYamlConfig().getBoolean("isMinecraftWelcomeMessageEnabled");
    }

    public void setMinecraftWelcomeMessageEnabled(boolean minecraftWelcomeMessageEnabled, @NotNull PluginConfigYml pluginConfig) {
        pluginConfig.getYamlConfig().set("minecraftWelcomeMessageEnabled", minecraftWelcomeMessageEnabled);
        AbstractFile.save(pluginConfig);
    }

    public boolean isDebugEnabled(@NotNull PluginConfigYml pluginConfig) {
        return pluginConfig.getYamlConfig().getBoolean("isDebugEnabled");
    }

    public void setDebugEnabled(boolean debugEnabled, @NotNull PluginConfigYml pluginConfig) {
        pluginConfig.getYamlConfig().set("debugEnabled", debugEnabled);
        AbstractFile.save(pluginConfig);
    }

}
