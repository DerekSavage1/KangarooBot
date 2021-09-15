package me.ChewyN.Data;

import me.ChewyN.Main;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.TextChannel;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;

import static me.ChewyN.Main.configFile;
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

        if (!config.contains("Discord_Welcome_Channel")  ||
                config.getString("Discord_Welcome_Channel") == null || Objects.requireNonNull(config.getString("Discord_Welcome_Channel")).isEmpty()) {
            log(Level.WARNING, "Welcome channel is missing in config.yml.");
            log(Level.WARNING, "We don't know which channel to send new players to.");
            log(Level.WARNING, "Disabling /discord command...");
            setDiscordCommand(false); // Disables discord command
            config.set("Discord_Welcome_Channel", "");
        }

        //Main.log(Level.INFO, "Welcome Channel Exists: id:" + config.getString("Discord_Welcome_Channel"));

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

        if (!config.contains("Death_Messages")) {
            ArrayList<String> messages = new ArrayList<>();
            messages.add("has passed away");
            messages.add("got lost in the sauce");
            config.set("Death_Messages", messages);
        }

        if (!config.contains("Discord_Death_Message_Description"))
            config.set("Discord_Death_Message_Description", "I forgor :skull:");

        if (!config.contains("Send_Death_Messages_To_Discord"))
            config.set("Send_Death_Messages_To_Discord", true);

        if (!config.contains("Log_Death_Info_In_Admin_Channel"))
            config.set("Log_Death_Info_In_Admin_Channel", true);

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
            config.set("Welcome_Message_Enabled", true);

        if (!config.contains("Join_Message_Enabled"))
            config.set("Join_Message_Enabled", true);

        if (!config.contains("Join_Message"))
            config.set("Join_Message", " has joined the game!");

        if (!config.contains("Discord_Command_Enabled"))
            config.set("Discord_Command_Enabled", true);

        if (!config.contains("Discord_Join_Leave_Messages_Enabled"))
            config.set("Discord_Join_Leave_Messages_Enabled", true);

        if (!config.contains("Discord_Player_Join_Message"))
            config.set("Discord_Player_Join_Message", " has joined the server");

        if (!config.contains("Discord_Player_Leave_Message"))
            config.set("Discord_Player_Leave_Message", " has left the server");


        save();
    }

    //TODO: Add javadoc comments to all of these methods

    @NotNull
    public static TextChannel getMinecraftChannel(JDA discordbot) {

        String minecraftChannelId = config.getString("Discord_Minecraft_Channel");

        assert minecraftChannelId != null;
        if (minecraftChannelId.isEmpty()) {
            log(Level.SEVERE, "=============================================================================");
            log(Level.SEVERE, "Discord_Minecraft_Channel is blank in config.yml! This is required for this plugin to function!");
            log(Level.SEVERE, "Discord bot needs a welcome channel to know where to send minecraft messages!");
            log(Level.SEVERE, "Enable developer mode in the discord settings and right click the channel you want the minecraft messages to go");
            log(Level.SEVERE, "Disabling plugin...");
            log(Level.SEVERE, "=============================================================================");
            instance.getServer().getPluginManager().disablePlugin(instance);
        } else {
            if (discordbot.getTextChannelById(minecraftChannelId) == null) {
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

    public static TextChannel getAdminChannel(JDA discordbot) {
        String channelID = config.getString("Discord_Welcome_Channel");

        if (channelID == null || configFile.isAdminChannelEnabled()) {
            log(Level.WARNING, "Admin chat not found! Disabling...");
        }

        assert channelID != null;
        return discordbot.getTextChannelById(channelID);
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

        if (!check(ID)) {
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
        if (!check(status)) {
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

    @NotNull
    public static String getOnlineRoleName() {
        String roleName = config.getString("Discord_Online_Role_Name");

        if (roleName == null) {
            Main.log(Level.WARNING, "No Online Role set! Setting to default!");
            return "online in-game";
        }
        return roleName;
    }

    public static Boolean getWelcomeMessageEnabled() {
        if (!check(config.getBoolean("Welcome_Message_Enabled"))) {
            Main.log(Level.WARNING, "No Welcome Message Option set! Defaulting to false!");
            return false;
        } else {
            return config.getBoolean("Welcome_Message_Enabled");
        }
    }

    public static Boolean getJoinMessageEnabled() {
        if (!check(config.getBoolean("Join_Message_Enabled"))) {
            Main.log(Level.WARNING, "No Join Message Option set! Defaulting to false!");
            return false;
        } else {
            return config.getBoolean("Join_Message_Enabled");
        }
    }

    @NotNull
    public static String getWelcomeMessage() {
        String m = config.getString("Join_Message");
        if (!check(m)) {
            Main.log(Level.WARNING, "No Join Message set! Using Default Message!");
            config.set("Join_Message", " has joined the server!");
            m = getWelcomeMessage();
        }
        assert m != null;
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

    public static Boolean discordCommandEnabled() {
        boolean enabled = config.getBoolean("Discord_Command_Enabled");
        if (!check(enabled)) {
            Main.log(Level.WARNING, "Discord command option not set! Defaulting to true!");
            config.set("Discord_Command_Enabled", true);
            enabled = true;
        }
        return enabled;
    }

    public static void setDiscordCommand(Boolean enabled) {
        try {
            config.set("Discord_Command_Enabled", enabled);
        } catch (NullPointerException e) {
            Main.log(Level.WARNING, "Discord command option does not exist!");
            e.printStackTrace();
        }
    }

    public static Boolean discordJoinLeaveMessagesEnabled() {
        boolean enabled = config.getBoolean("Discord_Join_Leave_Messages_Enabled");
        if (!check(enabled)) {
            Main.log(Level.WARNING, "Discord Join/Leave message option not set! Defaulting to true!");
            config.set("Discord_Join_Leave_Messages_Enabled", true);
            enabled = true;
        }
        return enabled;
    }

    public static String getDiscordPlayerJoinMessage() {
        String m = config.getString("Discord_Player_Join_Message");
        if (!check(m)) {
            Main.log(Level.WARNING, "Discord join message not set! Setting to default!");
            config.set("Discord_Player_Join_Message", " has joined the server");
            m = " has joined the server";
        }
        return m;
    }

    public static String getDiscordPlayerLeaveMessage() {
        String m = config.getString("Discord_Player_Leave_Message");
        if (!check(m)) {
            Main.log(Level.WARNING, "Discord leave message not set! Setting to default!");
            config.set("Discord_Player_Leave_Message", " has left the server");
            m = " has left the server";
        }
        return m;
    }

    /**
     * Gets the discord death message description from the config file.
     * @return The discord death message description.
     */
    public static String getDiscordDeathDescription() {
        String m = config.getString("Discord_Death_Message_Description");
            if (!check(m)) {
                Main.log(Level.WARNING, "No discord death message description set! Setting to default!");
                config.set("Discord_Death_Message_Description", "I forgor :skull:");
                m = "I forgor :skull:";
            }
            return m;
    }

    /**
     * Checks the config to see if the plugin should send death messages to discord.
     * @return True if messages should send. False if they shouldn't.
     */
    public static Boolean sendDeathToDiscord() {
        boolean enabled = config.getBoolean("Send_Death_Messages_To_Discord");
        if (!check(enabled)) {
            Main.log(Level.WARNING, "No discord death message sending option set! Setting to default!");
            config.set("Send_Death_Messages_To_Discord", true);
            enabled = true;
        }
        return enabled;
    }

    /**
     * Checks to config to see if the plugin should log death info in the admin channel as well.
     * @return True if it should log. False if it shouldn't.
     */
    public static Boolean logDeathInAdmin() {
        boolean enabled = config.getBoolean("Log_Death_Info_In_Admin_Channel");
        if (!check(enabled)) {
            Main.log(Level.WARNING, "No discord death info admin option set! Setting to default!");
            config.set("Log_Death_Info_In_Admin_Channel", true);
            enabled = true;
        }
        return enabled;
    }

    /**
     * Helper method to check if a Boolean from the config is null.
     * @param b The Boolean to check.
     * @return The result. True if the Boolean is not null, false if it is.
     */
    private static Boolean check(Boolean b) {
        return b != null;
    }

    /**
     * Helper method to check if a string from the config is empty or null.
     * @param s The string to check.
     * @return The result. True if the string is not empty or null, false if it is.
     */
    private static Boolean check(String s) {
        return s != null && !s.isEmpty();
    }

    //TODO: Create Reload Function


}
