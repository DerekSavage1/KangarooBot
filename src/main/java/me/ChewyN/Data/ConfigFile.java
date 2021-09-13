package me.ChewyN.Data;

import me.ChewyN.Main;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.TextChannel;
import org.apache.commons.lang.ObjectUtils;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;

import static me.ChewyN.Main.log;

public class ConfigFile extends AbstractFile{

    final private static Main instance = Main.getInstance();

    public ConfigFile(Main main) {
        super(main, "config.yml");
    }

    public static void setup() {

        if(!config.contains("Discord_Welcome_Channel") ||
                config.getString("Discord_Welcome_Channel") == null) {
            log(Level.WARNING, "Welcome channel is missing in config.yml.");
            log(Level.WARNING, "We don't know which channel to send new players to.");
            log(Level.WARNING, "Disabling /discord command..."); //TODO: disable discord command
            config.set("Discord_Welcome_Channel", null);
        }

        if(!config.contains("Discord_Minecraft_Channel"))
            config.set("Discord_Minecraft_Channel", "");

        if(!config.contains("Discord_Admin_Channel"))
            config.set("Discord_Admin_Channel", "");

        if(!config.contains("DiscordBot_Token"))
            config.set("DiscordBot_Token", null);

        if(!config.contains("Bot_Status"))
            config.set("Bot_Status", "Jumping Simulator");

        if(!config.contains("Back_Command"))
            config.set("Back_Command", true);

        if(!config.contains("Debug_Enabled"))
            config.set("Debug_Enabled", true);

        if(config.getBoolean("Debug_Enabled"))
            Main.log(Level.INFO, "debug-mode is enabled in the config.yml," +
                    "debug messages will appear until you set this to false.");

        //TODO: set default death messages (List<String>)
        if(!config.contains("Death_Messages"))
            config.set("Death_Messages", "");

        if(!config.contains("Center_Death_Messages"))
            config.set("Center_Death_Messages", true);

        if(!config.contains("Discord_Server_Online_Message"))
            config.set("Discord_Server_Online_Message", "Server online! Join now!");

        if(!config.contains("Discord_Server_Offline_Message"))
            config.set("Discord_Server_Offline_Message", "Server Stopped.");

        if(!config.contains("Admin_Channel_Enabled"))
            config.set("Admin_Channel_Enabled", false);

        if (!config.contains("Discord_Online_Role_Name"))
            config.set("Discord_Online_Role_Name", "online in-game");

        save();
    }

    //TODO: Make getMinecraftChannel @Notnull
    public static TextChannel getMinecraftChannel(JDA discordbot) {
        try{
            return discordbot.getTextChannelById((String) Objects.requireNonNull(config.get("Discord_Minecraft_Channel")));
        } catch(NullPointerException e) {
            Main.log(Level.SEVERE,"Discord is not configured correctly." +
                    "(is the discordbot field empty?)");
            if(config.getBoolean("Debug_Enabled"))
                e.printStackTrace();
        }
        return null;
    }

    //TODO: Make getAdminChannel @Notnull
    public static TextChannel getAdminChannel(JDA discordbot) {
        try{
            return discordbot.getTextChannelById((String) Objects.requireNonNull(config.get("Discord_Admin_Channel")));
        } catch(NullPointerException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static TextChannel getWelcomeChannel(JDA discordbot) {
        String channelID = config.getString("Discord_Welcome_Channel");

        if(channelID == null) {
            log(Level.WARNING, "Discord command disabled! No welcome channel found!");

            return null;
        }

        return discordbot.getTextChannelById(channelID);

    }

    @NotNull
    public static String getDiscordBotID () {
        String ID = config.getString("DiscordBot_ID");

        if(ID == null) {
            log(Level.SEVERE, "=============================================================================");
            log(Level.SEVERE,"DiscordBot_ID is blank in plugin.yml! Shutting down...");
            log(Level.SEVERE,"In order to use this plugin you must create a discord bot.");
            log(Level.SEVERE,"See https://discord.com/developers/applications/me for more information");
            log(Level.SEVERE,"Once you create a developer application, create and customize your bot");
            log(Level.SEVERE,"In the bot tab, copy the Token and put it in the config.yml");
            log(Level.SEVERE,"Do not share your bot token!. Treat it like a password to your discord");
            log(Level.SEVERE,"=============================================================================");
            instance.getServer().getPluginManager().disablePlugin(instance);
        }

        assert ID != null;
        return ID;
    }

    public static String getBotStatus () {
        String status = config.getString("Bot_Status");
        if(status == null) {
            return "Minecraft!";
        }
        return status;
    }

    public static Boolean backCommandEnabled() {
        return config.getBoolean("Back_Command");
    }

    public static String getDiscordOnlineMessage() {
        try {
            return (String) config.get("Discord_Server_Online_Message");
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getDiscordOfflineMessage() {
        try {
            return (String) config.get("Discord_Server_Offline_Message");
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
        try {
            return config.getString("Discord_Online_Role_Name");
            } catch (NullPointerException e) {
            Main.log(Level.SEVERE, "No Online Role set! Setting to default!");
            return "online in-game";
        }
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
        config.set("Admin_Channel_Enabled",enabled);
        save();
    }

    //TODO: Create Reload Function




}
