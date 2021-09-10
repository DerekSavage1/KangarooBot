package me.ChewyN.Data;

import me.ChewyN.Main;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.TextChannel;
import org.eclipse.jgit.annotations.NonNull;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.logging.Level;

public class ConfigFile extends AbstractFile{

    public ConfigFile(Main main) {
        super(main, "config.yml");
    }

    public static void setup() {

        if(!config.contains("Discord_Welcome_Channel"))
            config.set("Discord_Welcome_Channel", "");

        if(!config.contains("Discord_Minecraft_Channel"))
            config.set("Discord_Minecraft_Channel", "");

        if(!config.contains("Discord_Admin_Channel"))
            config.set("Discord_Admin_Channel", "");

        if(!config.contains("DiscordBot_ID"))
            config.set("DiscordBot_ID", "");

        if(!config.contains("Debug_Enabled"))
            config.set("Debug_Enabled", "False");

        if(config.getBoolean("Debug_Enabled"))
            instance.getLogger().log(Level.INFO, "debug-mode is enabled in the config.yml," +
                    "debug messages will appear until you set this to false.");

        save();
    }

    @NonNull
    public static TextChannel getMinecraftChannel(JDA discordbot) {
        try{
            return discordbot.getTextChannelById((String) Objects.requireNonNull(config.get("Discord_Minecraft_Channel")));
        } catch(NullPointerException e) {
            Main.getInstance().getLogger().log(Level.SEVERE,"Discord is not configured correctly." +
                    "(is the discordbot field empty?)");
            if(config.getBoolean("Debug_Enabled"))
                e.printStackTrace();
        }
        return null;
    }

    @NonNull
    public static TextChannel getAdminChannel(JDA discordbot) {
        try{
            return discordbot.getTextChannelById((String) Objects.requireNonNull(config.get("Discord_Admin_Channel")));
        } catch(NullPointerException e) {
            e.printStackTrace();
        }
        return null;
    }

    @NonNull
    public static TextChannel getWelcomeChannel(JDA discordbot) {
        try{
            return discordbot.getTextChannelById((String) Objects.requireNonNull(config.get("Discord_Welcome_Channel")));
        } catch(NullPointerException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getDiscordBotID () {
        try {
            return (String) config.get("DiscordBot_ID");
        } catch(NullPointerException e) {
            e.printStackTrace();
        }
        return null;
    }



}
