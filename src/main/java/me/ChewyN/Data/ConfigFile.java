package me.ChewyN.Data;

import me.ChewyN.Main;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.TextChannel;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;

import static me.ChewyN.Main.log;

public class ConfigFile extends AbstractFile {

    final private static Main instance = Main.getInstance();

    public ConfigFile(Main main) {
        super(main, "config.yml");
    }

    /**
     * Sets up the config file. Checks and makes sure the config contains everything that it needs. If it doesn't, fixes the config and sets defaults.
     */
    public static void setup() {

        if (!config.contains("Discord_Welcome_Channel") ||
                config.getString("Discord_Welcome_Channel") == null) {
            log(Level.WARNING, "Welcome channel is missing in config.yml.");
            log(Level.WARNING, "We don't know which channel to send new players to.");
            log(Level.WARNING, "Disabling /discord command..."); //TODO: disable discord command
            config.set("Discord_Welcome_Channel", null);
        }

        if (!config.contains("Discord_Minecraft_Channel"))
            config.set("Discord_Minecraft_Channel", "");

        if (!config.contains("Discord_Admin_Channel"))
            config.set("Discord_Admin_Channel", "");

        if (!config.contains("DiscordBot_Token"))
            config.set("DiscordBot_Token", null);

        if (!config.contains("Bot_Status"))
            config.set("Bot_Status", "Jumping Simulator");

        if (!config.contains("Back_Command"))
            config.set("Back_Command", true);

        if (!config.contains("Debug_Enabled"))
            config.set("Debug_Enabled", true);

        if (config.getBoolean("Debug_Enabled"))
            Main.log(Level.INFO, "debug-mode is enabled in the config.yml," +
                    "debug messages will appear until you set this to false.");

        //TODO: set default death messages (List<String>)
        if (!config.contains("Death_Messages"))
            config.set("Death_Messages", "");

        if (!config.contains("Center_Death_Messages"))
            config.set("Center_Death_Messages", true);

        if (!config.contains("Discord_Server_Online_Message"))
            config.set("Discord_Server_Online_Message", "Server online! Join now!");

        if (!config.contains("Discord_Server_Offline_Message"))
            config.set("Discord_Server_Offline_Message", "Server Stopped.");

        if (!config.contains("Admin_Channel_Enabled"))
            config.set("Admin_Channel_Enabled", false);

        if (!config.contains("Discord_Online_Role_Name"))
            config.set("Discord_Online_Role_Name", "online in-game");

        if (!config.contains("Welcome_Message_Enabled"))
            config.set("Welcome_Message_Enabled", false);

        if (!config.contains("Join_Message_Enabled"))
            config.set("Join_Message-Enabled", false);

        if (!config.contains("Join_Message"))
            config.set("Join_Message", " has joined the game!");

        save();
    }

    // TODO: MAKE THIS??
    // Idk if this is needed btw -Skyla
    /**
     * Generates a new config file with defaults if one does not exist
     */
    public static void generateConfig() {

    }

    @NotNull
    public static TextChannel getMinecraftChannel(JDA discordbot) {

        String minecraftChannelId = config.getString("Discord_Minecraft_Channel");

        if(minecraftChannelId.isEmpty()) {
            log(Level.SEVERE, "=============================================================================");
            log(Level.SEVERE, "Discord_Minecraft_Channel is blank in config.yml! This is required for this plugin to function!");
            log(Level.SEVERE, "Discord bot needs a welcome channel to know where to send minecraft messages!");
            log(Level.SEVERE, "Enable developer mode in the discord settings and right click the channel you want the minecraft messages to go");
            log(Level.SEVERE, "Disabling plugin...");
            log(Level.SEVERE, "=============================================================================");
            instance.getServer().getPluginManager().disablePlugin(instance);
        } else {
            if(discordbot.getTextChannelById(minecraftChannelId) == null) {
                log(Level.SEVERE, "=============================================================================");
                log(Level.SEVERE, "Discord_Minecraft_Channel is filled out, but that channel does not exist or is invalid!");
                log(Level.SEVERE, "Discord bot needs a welcome channel to know where to send minecraft messages!");
                log(Level.SEVERE, "Enable developer mode in the discord settings and right click the channel you want the minecraft messages to go");
                log(Level.SEVERE, "Disabling plugin...");
                log(Level.SEVERE, "=============================================================================");
                instance.getServer().getPluginManager().disablePlugin(instance);
            }
            TextChannel minecraftChannel = discordbot.getTextChannelById(minecraftChannelId);
            assert minecraftChannel != null;
            return minecraftChannel;
        }
        throw new NullPointerException("No Minecraft Channel Provided!");
    }

    //TODO: Make getAdminChannel @Notnull
    public static TextChannel getAdminChannel(JDA discordbot) {
        try {
            return discordbot.getTextChannelById((String) Objects.requireNonNull(config.get("Discord_Admin_Channel")));
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static TextChannel getWelcomeChannel(JDA discordbot) {
        String channelID = config.getString("Discord_Welcome_Channel");

        if (channelID == null) {
            log(Level.WARNING, "Discord command disabled! No welcome channel found!");

            return null;
        }

        return discordbot.getTextChannelById(channelID);

    }

    @NotNull
    public static String getDiscordBotID() {
        String ID = config.getString("DiscordBot_ID");

        if (ID == null) {
            log(Level.SEVERE, "=============================================================================");
            log(Level.SEVERE, "DiscordBot_ID is blank in plugin.yml! Shutting down...");
            log(Level.SEVERE, "In order to use this plugin you must create a discord bot.");
            log(Level.SEVERE, "See https://discord.com/developers/applications/me for more information");
            log(Level.SEVERE, "Once you create a developer application, create and customize your bot");
            log(Level.SEVERE, "In the bot tab, copy the Token and put it in the config.yml");
            log(Level.SEVERE, "Do not share your bot token!. Treat it like a password to your discord");
            log(Level.SEVERE, "=============================================================================");
            instance.getServer().getPluginManager().disablePlugin(instance);
        }

        assert ID != null;
        return ID;
    }

    public static String getBotStatus() {
        String status = config.getString("Bot_Status");
        if (status == null) {
            return "Minecraft!";
        }
        return status;
    }

    public static Boolean backCommandEnabled() {
        return config.getBoolean("Back_Command");
    }

    public static String getDiscordOnlineMessage() {
        try {
            return config.getString("Discord_Server_Online_Message");
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getDiscordOfflineMessage() {
        try {
            return config.getString("Discord_Server_Offline_Message");
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<String> getDeathMessages() {
        try {
            return config.getStringList("Death_Messages");
        } catch (Exception e) {
            Main.log(Level.SEVERE, "Incorrect config.yml!");
            e.printStackTrace();
            return null;
        }
    }

    @Nullable
    public static String getOnlineRoleName() {
        if(config.getString("Discord_Online_Role_Name") == null) {
            Main.log(Level.SEVERE, "[KangarooBot] No Online Role set! Setting to default!");
            return "online in-game";
        } else {
            return config.getString("Discord_Online_Role_Name");
        }
    }

    public static Boolean getWelcomeMessageEnabled() {
        if ((Boolean) config.getBoolean("Welcome_Message_Enabled") == null) {
            Main.log(Level.SEVERE, "[KangarooBot] No Welcome Message Option set! Defaulting to false!");
            return false;
        } else {
            return config.getBoolean("Welcome_Message_Enabled");
        }
    }

    public static Boolean getJoinMessageEnabled() {
        if ((Boolean) config.getBoolean("Join_Message_Enabled") == null) {
            Main.log(Level.SEVERE, "[KangarooBot] No Join Message Option set! Defaulting to false!");
            return false;
        } else {
            return config.getBoolean("Join_Message_Enabled");
        }
    }

    @NotNull
    public static String getWelcomeMessage() {
        String m = config.getString("Join_Message");
        if (m == null) {
            Main.log(Level.SEVERE, "[KangarooBot] No Join Message set! Using Default Message!");
            config.set("Join_Message", " has joined the server!");
            m = getWelcomeMessage();
        }
        return m;
    }

    public boolean centeredDeathMessageEnabled() {
        return config.getBoolean("Center_Death_Messages");
    }

    public boolean isDebugEnabled() {
        return config.getBoolean("Debug_Enabled");
    }

    public boolean isAdminChannelEnabled() {
        return config.getBoolean("Admin_Channel_Enabled");
    }

    public void setAdminChannelEnabled(boolean enabled) {
        config.set("Admin_Channel_Enabled", enabled);
        save();
    }

    //TODO: Create Reload Function


}
