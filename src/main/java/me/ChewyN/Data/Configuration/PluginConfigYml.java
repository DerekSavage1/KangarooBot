package me.ChewyN.Data.Configuration;

import me.ChewyN.Data.AbstractFile;
import me.Main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PluginConfigYml extends AbstractFile {

    public PluginConfigAPI getConfigApi() {
        return configApi;
    }

    private final PluginConfigAPI configApi;

    public PluginConfigYml(Main _main, String fileName) {
        super(_main, fileName);
        setup(this);
        configApi = new PluginConfigAPI();
    }

    public static void setup(PluginConfigYml pluginConfig) {


//      When adding new config sections this is the format:
//      writeToConfig("pluginConfig, "path", "value);
//      These terms are in the same order as readConfigFile so please keep that in mind too


        Map<Integer, String> comments = new HashMap<>(); //integer is the line number
        comments.put(0,"=====Mandatory===");
        comments.put(4,"====Extra Functionality====");
        comments.put(9,"=====Toggleable===");
        comments.put(14,"=====Customization===");
        comments.put(20,"=====Should probably be in a different plugin===");
        comments.put(26,"=====Dev Tools===");

        writeToConfig(pluginConfig, "discordMinecraftChannelID", "");
        writeToConfig(pluginConfig, "discordBotToken", "");

        writeToConfig(pluginConfig, "discordWelcomeChannelID", "");
        writeToConfig(pluginConfig, "discordAdminChannelID", "");
        writeToConfig(pluginConfig, "discordOnlineRoleName", "online in-game");
        writeToConfig(pluginConfig, "logDeathInfoInAdminChannel", true);

        writeToConfig(pluginConfig, "discordAdminChannelEnabled", false);
        writeToConfig(pluginConfig, "sendDeathMessagesToDiscord", true);
        writeToConfig(pluginConfig, "discordCommandEnabled", true);
        writeToConfig(pluginConfig, "discordJoinLeaveMessagesEnabled", true);

        writeToConfig(pluginConfig, "customDiscordBotStatus", "Minecraft! #Kanagroobot is playing Minecraft!");
        writeToConfig(pluginConfig, "customDiscordDeathMessageDescription", null);
        writeToConfig(pluginConfig, "customDiscordMessageOnStartup", "Server online! Join now!");
        writeToConfig(pluginConfig, "customDiscordMessageOnShutdown", "Server Stopped.");
        writeToConfig(pluginConfig, "customDiscordPlayerJoinMessage", " has joined the server");
        writeToConfig(pluginConfig, "customDiscordPlayerLeaveMessage", " has left the server");

        writeToConfig(pluginConfig, "minecraftJoinMessageEnabled", false);
        writeToConfig(pluginConfig, "minecraftJoinMessage", " has joined the game!");
        writeToConfig(pluginConfig, "minecraftBackCommandEnabled", false);
        writeToConfig(pluginConfig, "minecraftCenterDeathMessages", false);
        writeToConfig(pluginConfig, "minecraftWelcomeMessageEnabled", false);

        writeToConfig(pluginConfig, "debugEnabled", false);
        writeToConfig(pluginConfig, "discordDebugGuildID", "");
        writeToConfig(pluginConfig, "discordDebugChannelID", "");

        ArrayList<String> messages = new ArrayList<>();
        messages.add("is no longer with us");
        messages.add("passed away");
        messages.add("slipped in bbq sauce");
        messages.add("ate way too much rice");
        writeToConfig(pluginConfig, "deathMessages", messages);

        YamlCommenter.addComments(pluginConfig.getFile(), comments);
        YamlCommenter.saveCommented(pluginConfig.getYamlConfig(), pluginConfig.getFile());
    }

    private static <Object> void writeToConfig(PluginConfigYml pluginConfig, String path, Object value) {
        if (!pluginConfig.getYamlConfig().contains(path))
            pluginConfig.getYamlConfig().set(path, value);
        AbstractFile.save(pluginConfig);
    }

    
}
