package me.ChewyN.Data;

import me.ChewyN.Main;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.TextChannel;
import org.bukkit.configuration.file.FileConfiguration;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;

public class ConfigFile extends AbstractFile {


    public ConfigFile(Main main) {
        super(main, "config.yml");
    }

    public static void setup(ConfigFile file) {

        FileConfiguration config = file.getConfig();

        if (!config.contains("Discord_Welcome_Channel")  ||
                config.getString("Discord_Welcome_Channel") == null || Objects.requireNonNull(config.getString("Discord_Welcome_Channel")).isEmpty()) {
            Main.log(Level.WARNING, "Welcome channel is missing in config.yml.");
            Main.log(Level.WARNING, "We don't know which channel to send new players to.");
            Main.log(Level.WARNING, "Disabling /discord command...");
            setDiscordCommand(Main.getConfigFile(), false); // Disables discord command
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


        save(Main.getConfigFile());
    }

    //TODO: Add javadoc comments to all of these methods

    @NotNull
    public static TextChannel getMinecraftChannel(ConfigFile config, JDA discordbot) {

        String minecraftChannelId = config.getConfig().getString("Discord_Minecraft_Channel");

        assert minecraftChannelId != null;
        if (minecraftChannelId.isEmpty()) {
            Main.log(Level.SEVERE, "=============================================================================");
            Main.log(Level.SEVERE, "Discord_Minecraft_Channel is blank in config.yml! This is required for this plugin to function!");
            Main.log(Level.SEVERE, "Discord bot needs a welcome channel to know where to send minecraft messages!");
            Main.log(Level.SEVERE, "Enable developer mode in the discord settings and right click the channel you want the minecraft messages to go");
            Main.log(Level.SEVERE, "Disabling plugin...");
            Main.log(Level.SEVERE, "=============================================================================");
            Main.getInstance().getServer().getPluginManager().disablePlugin(Main.getInstance());
        } else {
            if (discordbot.getTextChannelById(minecraftChannelId) == null) {
                Main.log(Level.SEVERE, "=============================================================================");
                Main.log(Level.SEVERE, "Discord_Minecraft_Channel is filled out, but that channel does not exist or is invalid!");
                Main.log(Level.SEVERE, "Discord bot needs a welcome channel to know where to send minecraft messages!");
                Main.log(Level.SEVERE, "Enable developer mode in the discord settings and right click the channel you want the minecraft messages to go");
                Main.log(Level.SEVERE, "Disabling plugin...");
                Main.log(Level.SEVERE, "=============================================================================");
                Main.getInstance().getServer().getPluginManager().disablePlugin(Main.getInstance());
            }
            TextChannel minecraftChannel = discordbot.getTextChannelById(minecraftChannelId);
            assert minecraftChannel != null;
            return minecraftChannel;
        }
        throw new NullPointerException("No Minecraft Channel Provided!");
    }

    public static TextChannel getAdminChannel(ConfigFile config, JDA discordbot) {
        String channelID = config.getConfig().getString("Discord_Admin_Channel");

        if (channelID == null && config.isAdminChannelEnabled(config)) {
            Main.log(Level.WARNING, "Admin chat not found! Disabling...");
            config.setAdminChannelEnabled(config, false);
        }

        assert channelID != null;
        return discordbot.getTextChannelById(channelID);
    }

    public static TextChannel getWelcomeChannel(ConfigFile config, JDA discordbot) {
        String channelID = config.getConfig().getString("Discord_Welcome_Channel");

        if (channelID == null) {
            Main.log(Level.WARNING, "Discord command disabled! No welcome channel found!");
            return null;
        }

        return discordbot.getTextChannelById(channelID);
    }

    @NotNull
    public static String getDiscordBotID(ConfigFile config) {
        String ID = config.getConfig().getString("DiscordBot_ID");

        if (!check(ID)) {
            Main.log(Level.SEVERE, "=============================================================================");
            Main.log(Level.SEVERE, "DiscordBot_ID is blank in plugin.yml! Shutting down...");
            Main.log(Level.SEVERE, "In order to use this plugin you must create a discord bot.");
            Main.log(Level.SEVERE, "See https://discord.com/developers/applications/me for more information");
            Main.log(Level.SEVERE, "Once you create a developer application, create and customize your bot");
            Main.log(Level.SEVERE, "In the bot tab, copy the Token and put it in the config.yml");
            Main.log(Level.SEVERE, "Do not share your bot token!. Treat it like a password to your discord");
            Main.log(Level.SEVERE, "=============================================================================");
            Main.getInstance().getServer().getPluginManager().disablePlugin(Main.getInstance());
        }

        assert ID != null;
        return ID;
    }

    public static String getBotStatus(ConfigFile config) {
        String status = config.getConfig().getString("Bot_Status");
        if (!check(status)) {
            return "Minecraft!";
        }
        return status;
    }

    public static Boolean backCommandEnabled(ConfigFile config) {
        return config.getConfig().getBoolean("Back_Command");
    }

    public static String getDiscordOnlineMessage(ConfigFile config) {
        try {
            return config.getConfig().getString("Discord_Server_Online_Message");
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getDiscordOfflineMessage(ConfigFile config) {
        try {
            return config.getConfig().getString("Discord_Server_Offline_Message");
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<String> getDeathMessages(ConfigFile config) {
        try {
            return config.getConfig().getStringList("Death_Messages");
        } catch (Exception e) {
            Main.log(Level.SEVERE, "Incorrect config.yml!");
            e.printStackTrace();
            return null;
        }
    }

    @NotNull
    public static String getOnlineRoleName(ConfigFile config) {
        String roleName = config.getConfig().getString("Discord_Online_Role_Name");

        if (roleName == null) {
            Main.log(Level.WARNING, "No Online Role set! Setting to default!");
            return "online in-game";
        }
        return roleName;
    }

    public static Boolean getWelcomeMessageEnabled(ConfigFile config) {
        if (!check(config.getConfig().getBoolean("Welcome_Message_Enabled"))) {
            Main.log(Level.WARNING, "No Welcome Message Option set! Defaulting to false!");
            return false;
        } else {
            return config.getConfig().getBoolean("Welcome_Message_Enabled");
        }
    }

    public static Boolean getJoinMessageEnabled(ConfigFile config) {
        if (!check(config.getConfig().getBoolean("Join_Message_Enabled"))) {
            Main.log(Level.WARNING, "No Join Message Option set! Defaulting to false!");
            return false;
        } else {
            return config.getConfig().getBoolean("Join_Message_Enabled");
        }
    }

    @NotNull
    public static String getWelcomeMessage(ConfigFile config) {
        String m = config.getConfig().getString("Join_Message");
        if (!check(m)) {
            Main.log(Level.WARNING, "No Join Message set! Using Default Message!");
            config.getConfig().set("Join_Message", " has joined the server!");
            m = getWelcomeMessage(config);
        }
        assert m != null;
        return m;
    }

    public boolean centeredDeathMessageEnabled(ConfigFile config) {
        return config.getConfig().getBoolean("Center_Death_Messages");
    }

    public boolean isDebugEnabled(ConfigFile config) {
        return config.getConfig().getBoolean("Debug_Enabled");
    }

    public boolean isAdminChannelEnabled(ConfigFile config) {
        return config.getConfig().getBoolean("Admin_Channel_Enabled");
    }

    public void setAdminChannelEnabled(ConfigFile config, boolean enabled) {
        config.getConfig().set("Admin_Channel_Enabled", enabled);
        save(config);
    }

    public static Boolean discordCommandEnabled(ConfigFile config) {
        boolean enabled = config.getConfig().getBoolean("Discord_Command_Enabled");
        if (!check(enabled)) {
            Main.log(Level.WARNING, "Discord command option not set! Defaulting to true!");
            config.getConfig().set("Discord_Command_Enabled", true);
            enabled = true;
        }
        return enabled;
    }

    public static void setDiscordCommand(ConfigFile config, Boolean enabled) {
        try {
            config.getConfig().set("Discord_Command_Enabled", enabled);
        } catch (NullPointerException e) {
            Main.log(Level.WARNING, "Discord command option does not exist!");
            e.printStackTrace();
        }
    }

    public static Boolean discordJoinLeaveMessagesEnabled(ConfigFile config) {
        boolean enabled = config.getConfig().getBoolean("Discord_Join_Leave_Messages_Enabled");
        if (!check(enabled)) {
            Main.log(Level.WARNING, "Discord Join/Leave message option not set! Defaulting to true!");
            config.getConfig().set("Discord_Join_Leave_Messages_Enabled", true);
            enabled = true;
        }
        return enabled;
    }

    public static String getDiscordPlayerJoinMessage(ConfigFile config) {
        String m = config.getConfig().getString("Discord_Player_Join_Message");
        if (!check(m)) {
            Main.log(Level.WARNING, "Discord join message not set! Setting to default!");
            config.getConfig().set("Discord_Player_Join_Message", " has joined the server");
            m = " has joined the server";
        }
        return m;
    }

    public static String getDiscordPlayerLeaveMessage(ConfigFile config) {
        String m = config.getConfig().getString("Discord_Player_Leave_Message");
        if (!check(m)) {
            Main.log(Level.WARNING, "Discord leave message not set! Setting to default!");
            config.getConfig().set("Discord_Player_Leave_Message", " has left the server");
            m = " has left the server";
        }
        return m;
    }

    /**
     * Gets the discord death message description from the config file.
     * @return The discord death message description.
     */
    public static String getDiscordDeathDescription(ConfigFile config) {
        String m = config.getConfig().getString("Discord_Death_Message_Description");
            if (!check(m)) {
                Main.log(Level.WARNING, "No discord death message description set! Setting to default!");
                config.getConfig().set("Discord_Death_Message_Description", "I forgor :skull:");
                m = "I forgor :skull:";
            }
            return m;
    }

    /**
     * Checks the config to see if the plugin should send death messages to discord.
     * @return True if messages should send. False if they shouldn't.
     */
    public static Boolean sendDeathToDiscord(ConfigFile config) {
        boolean enabled = config.getConfig().getBoolean("Send_Death_Messages_To_Discord");
        if (!check(enabled)) {
            Main.log(Level.WARNING, "No discord death message sending option set! Setting to default!");
            config.getConfig().set("Send_Death_Messages_To_Discord", true);
            enabled = true;
        }
        return enabled;
    }

    /**
     * Checks to config to see if the plugin should log death info in the admin channel as well.
     * @return True if it should log. False if it shouldn't.
     */
    public static Boolean logDeathInAdmin(ConfigFile config) {
        boolean enabled = config.getConfig().getBoolean("Log_Death_Info_In_Admin_Channel");
        if (!check(enabled)) {
            Main.log(Level.WARNING, "No discord death info admin option set! Setting to default!");
            config.getConfig().set("Log_Death_Info_In_Admin_Channel", true);
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
