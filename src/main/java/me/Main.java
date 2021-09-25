package me;

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
import org.apache.commons.lang.exception.ExceptionUtils;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import javax.security.auth.login.LoginException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;

import static org.apache.logging.log4j.LogManager.getRootLogger;

public class Main extends JavaPlugin {

    //Quote of the day: What one developer can get done in 2 days, 2 developers can get done in 4!
    private static JDA discordbot;
    private static Main instance1;
    private static PluginConfigYml pluginConfig;

    public static PluginConfigYml getPluginConfig() {
        return pluginConfig;
    }

    public static PluginConfigAPI getPluginConfigApi(){
        return pluginConfig.getConfigApi();
    }

    public static Main getInstance() {
        return instance1;
    }

    @Override
    public void onEnable() {

        try {

            instance1 = this;

            if (!getDataFolder().exists())
                getDataFolder().mkdir();

            pluginConfig = new PluginConfigYml(instance1, "config.yml");

            awakenTheKangaroo();

            ((org.apache.logging.log4j.core.Logger) getRootLogger()).addFilter(new ApacheTest());


            //listeners
            List<Listener> listeners = new ArrayList<>();
            listeners.add(new JoinAndQuit());
            listeners.add(new PlayerChat());
            listeners.add(new PlayerDeath());
            listeners.add(new PlayerSpy());
            listeners.add(new GrappleListener());
            listeners.add(new CommandListener(this));
            listeners.add(new ServerCommandListener());

            for (Listener event : listeners) {
                getServer().getPluginManager().registerEvents(event, this);
            }

            //commands
            Objects.requireNonNull(this.getCommand("discord")).setExecutor(new DiscordCommand());
            Objects.requireNonNull(this.getCommand("grapplinghook")).setExecutor(new GrapplingHook());
            Objects.requireNonNull(this.getCommand("back")).setExecutor(new BackCommand());
            Objects.requireNonNull(this.getCommand("trashcan")).setExecutor(new TrashcanCommand());
            Objects.requireNonNull(this.getCommand("kgrl")).setExecutor(new ReloadCommand());
            Objects.requireNonNull(this.getCommand("weiner")).setExecutor(new FunCommand());
            Objects.requireNonNull(this.getCommand("exception")).setExecutor(new ExceptionCommand());


            // THIS STATEMENT NEEDS TO REMAIN AT THE END OF THE METHOD
            sendStartStopMessageToDiscord(true);
        }catch(IllegalStateException ignored) {}
    }

    @Override
    public void onDisable() {

        sendStartStopMessageToDiscord(false);

        try {
            clearOnlineRole();
        } catch(NullPointerException e) {
            debug("Was unable to clear online role, server did not boot correctly!");
        }

        if(discordbot != null)
           discordbot.shutdownNow();

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

        String token = getPluginConfigApi().getDiscordBotToken(getPluginConfig());
        if(token.equals("")) {
            log(Level.SEVERE, "Welcome to Kangaroo bot! Please fill out the config.yml located in plugins/KangarooBot/config.yml");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        JDABuilder jdaBuilder = JDABuilder.createDefault(token);
        jdaBuilder.enableIntents(gatewayIntents);
        jdaBuilder.addEventListeners(new onGuildJoin());
        jdaBuilder.addEventListeners(new onChat());

        jdaBuilder.setActivity(Activity.playing(getPluginConfigApi().getCustomDiscordBotStatus(getPluginConfig())));

        try {
            discordbot = jdaBuilder.build();
            discordbot.awaitReady();
        } catch (LoginException e) {
            log(Level.SEVERE, "Discord bot was unable to login.");
            debug(ExceptionUtils.getMessage(e));
            getServer().getPluginManager().disablePlugin(this);
        } catch (InterruptedException e) {
            log(Level.SEVERE, "Discord bot was interrupted!");
            debug(ExceptionUtils.getMessage(e));
            getServer().getPluginManager().disablePlugin(this);
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
