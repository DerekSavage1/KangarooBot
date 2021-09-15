package me.ChewyN.Data.Configuration;

import me.ChewyN.Data.AbstractFile;
import me.ChewyN.Main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PluginConfigYml extends AbstractFile {

    public PluginConfigAPI getConfigApi() {
        return configApi;
    }

    PluginConfigAPI configApi;

    public PluginConfigYml(Main _main, String fileName) {
        super(_main, fileName);
        setup(this);
        configApi = new PluginConfigAPI();
    }

    public static void setup(PluginConfigYml pluginConfig) {
        Map<String,Object> params = new HashMap<>();


//      When adding new config sections this is the format:
//      params.put("path", defaultValueObject);
//      These terms are in the same order as readConfigFile so please keep that in mind too

//            =====Mandatory===
        params.put("discordMinecraftChannelID", null);
        params.put("discordBotToken", null);

//            ====Extra functionality====
        params.put("discordWelcomeChannelID", null);
        params.put("discordAdminChannelID", null);
        params.put("deathMessages", new ArrayList<>().add("is no longer with us"));
        params.put("discordOnlineRoleName", "online in-game");
        params.put("logDeathInfoInAdminChannel", true);


//            ====Toggleable====
        params.put("discordAdminChannelEnabled", false);
        params.put("sendDeathMessagesToDiscord", true);
        params.put("discordCommandEnabled", true);
        params.put("discordJoinLeaveMessagesEnabled", true);

//            ====Customization====
        params.put("customDiscordBotStatus", "Minecraft!");
        params.put("customDiscordDeathMessageDescription", null);
        params.put("customDiscordMessageOnStartup", "Server online! Join now!");
        params.put("customDiscordMessageOnShutdown", "Server Stopped.");
        params.put("customDiscordPlayerJoinMessage", " has joined the server");
        params.put("customDiscordPlayerLeaveMessage", " has left the server");


//            Should probably be in a different plugin
        params.put("minecraftJoinMessageEnabled", true);
        params.put("minecraftJoinMessage", " has joined the game!");
        params.put("minecraftBackCommandEnabled", true);
        params.put("minecraftCenterDeathMessages", false);
        params.put("minecraftWelcomeMessageEnabled", true);

//            ====Dev tools====
        params.put("debugEnabled", true);
        params.put("DiscordDebugGuildID", "");
        params.put("DiscordDebugChannelID", "");


        writeDefaultsToConfig(pluginConfig, params);

        AbstractFile.save(pluginConfig);
    }

    private static <Object> void writeDefaultsToConfig(PluginConfigYml pluginConfig, Map<String,Object> hash) {
        for ( Map.Entry<String, Object> entry : hash.entrySet()) {
            String path = entry.getKey();
            Object value = entry.getValue();

            if (!pluginConfig.getYamlConfig().contains(path))
                pluginConfig.getYamlConfig().set(path, value);
        }
    }
}
