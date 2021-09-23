package me;

import com.google.inject.Inject;
import me.ChewyN.Data.Configuration.PluginConfigAPI;
import me.ChewyN.Data.Configuration.PluginConfigYml;
import me.ChewyN.Data.ErrorHandling.ApacheTest;
import me.ChewyN.Discord.Listeners.DiscordMessageHandler;
import me.ChewyN.Discord.Listeners.onChat;
import me.ChewyN.Discord.Listeners.onGuildJoin;
import me.ChewyN.Minecraft.Commands.DiscordCommand;
import me.ChewyN.Minecraft.Commands.ExceptionCommand;
import me.ChewyN.Minecraft.Commands.GrapplingHook;
import me.ChewyN.Minecraft.Listeners.GrappleListener;
import me.ChewyN.Minecraft.Listeners.Player.JoinAndQuit;
import me.ChewyN.Minecraft.Listeners.Player.PlayerChat;
import me.ChewyN.Minecraft.Listeners.Player.PlayerDeath;
import me.ChewyN.Minecraft.Listeners.Player.PlayerSpy;
import me.Skyla.Minecraft.Commands.BackCommand;
import me.Skyla.Minecraft.Commands.FunCommand;
import me.Skyla.Minecraft.Commands.ReloadCommand;
import me.Skyla.Minecraft.Commands.TrashcanCommand;
import me.Skyla.Minecraft.Listeners.CommandListener;
import me.Skyla.Minecraft.Listeners.ServerCommandListener;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.requests.GatewayIntent;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import javax.security.auth.login.LoginException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import static org.apache.logging.log4j.LogManager.getRootLogger;

public class Main extends JavaPlugin {



    //bad quote nerd
    private static JDA discordbot;
    private static Main instance1;
    private static PluginConfigYml pluginConfig;

    public static PluginConfigYml getPluginConfig() {
        return pluginConfig;
    }

    public static PluginConfigAPI getPluginConfigApi(){
        return pluginConfig.getConfigApi();
    }


    @Inject protected PlayerSpy playerSpy;
    @Inject protected PlayerDeath playerDeath;
    @Inject protected onGuildJoin onGuildJoin;


    public static Main getInstance() {
        return instance1;
    }

    @Override
    public void onEnable() {

        instance1 = this;

        if (!getDataFolder().exists())
            getDataFolder().mkdir();

        pluginConfig = new PluginConfigYml(instance1,"config.yml");

        awakenTheKangaroo();

        ((org.apache.logging.log4j.core.Logger) getRootLogger()).addFilter(new ApacheTest());

        //listeners
        List<Listener> listeners = new ArrayList<>();
        listeners.add(new JoinAndQuit());
        listeners.add(new PlayerChat());
        listeners.add(this.playerDeath);
        listeners.add(this.playerSpy);
        listeners.add(new GrappleListener());
        listeners.add(new CommandListener(this));
        listeners.add(new ServerCommandListener());

        for(Listener event : listeners) {
            getServer().getPluginManager().registerEvents(event, this);
        }

        //commands
        this.getCommand("discord").setExecutor(new DiscordCommand());
        this.getCommand("grapplinghook").setExecutor(new GrapplingHook());
        this.getCommand("back").setExecutor(new BackCommand());
        this.getCommand("trashcan").setExecutor(new TrashcanCommand());
        this.getCommand("kgrl").setExecutor(new ReloadCommand());
        this.getCommand("weiner").setExecutor(new FunCommand());
        this.getCommand("exception").setExecutor(new ExceptionCommand());


        // THIS STATEMENT NEEDS TO REMAIN AT THE END OF THE METHOD
        sendStartStopMessageToDiscord(true);
    }

    @Override
    public void onDisable() {
        try {
            clearOnlineRole();
        } catch(NullPointerException e) {
            this.getLogger().log(Level.SEVERE, "Was unable to clear online role, server did not boot correctly!");
        }

        if(discordbot != null) {
           discordbot.shutdownNow();
        }

        instance1 = null;
    }

    private void clearOnlineRole() {
        //Check if role exists
        String onlineRoleName = getPluginConfigApi().getDiscordOnlineRoleName(getPluginConfig());
        //fetch members
        List<Member> members = getGuild().getMembers();
        try {
            Role onlineRole = getGuild().getRolesByName(onlineRoleName, true).get(0);
            if (onlineRole != null) {
                for (Member user : members) {
                    if (user.getRoles().contains(onlineRole)) {
                        getGuild().removeRoleFromMember(user, onlineRole).complete();
                    }
                }
            }
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
        }
    }

    private void awakenTheKangaroo() {
        List<GatewayIntent> gatewayIntents = new ArrayList<>();
        gatewayIntents.add(GatewayIntent.GUILD_MEMBERS);
        gatewayIntents.add(GatewayIntent.GUILD_PRESENCES);

        JDABuilder jdaBuilder = JDABuilder.createDefault(getPluginConfigApi().getDiscordBotToken(getPluginConfig()));


        jdaBuilder.enableIntents(gatewayIntents);
        jdaBuilder.addEventListeners(this.onGuildJoin);
        jdaBuilder.addEventListeners(new onChat());

        jdaBuilder.setActivity(Activity.playing(getPluginConfigApi().getCustomDiscordBotStatus(getPluginConfig())));

        try {
            discordbot = jdaBuilder.build();
        } catch (LoginException e) {
            e.printStackTrace();
        }
        try {
            discordbot.awaitReady();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public static JDA getDiscordbot() {
        return discordbot;
    }

    public static Guild getGuild() {
        return discordbot.getGuilds().get(0);
        //This bot will only be used on my discord server.
    }

    /**
     * Void method to send a start/stop method to the discord channel once the server has started/stopped.
     *
     * @param isStarting If the server is starting or stopping
     */
    public static void sendStartStopMessageToDiscord(boolean isStarting) {

        EmbedBuilder message = new EmbedBuilder();
        if (isStarting) {
            message.setTitle("Server Online!");
            message.setColor(0x42f545);
            message.setDescription(Main.getPluginConfigApi().getCustomDiscordMessageOnStartup(getPluginConfig()));
        } else {
            message.setTitle("Server Offline.");
            message.setColor(0xeb4034);
            message.setDescription(Main.getPluginConfigApi().getCustomDiscordMessageOnShutdown(getPluginConfig()));
        }

        DiscordMessageHandler.sendToBothDiscordChannels(message.build());

        message.clear();


    }

    public static void log(Level level, String message) {
        instance1.getServer().getLogger().log(level, "[KangarooBot] " + message);
    }

    public static void debug(String message) {
        if (getPluginConfigApi().isDebugEnabled(getPluginConfig())) {
            instance1.getServer().getLogger().log(Level.INFO, "[KangarooBot] Debug: " + message);
        }
    }


}
