package me.ChewyN;

import me.ChewyN.Data.ConfigFile;
import me.ChewyN.Discord.Listeners.onChat;
import me.ChewyN.Discord.Listeners.onGuildJoin;
import me.ChewyN.Minecraft.Commands.DiscordCommand;
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
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.requests.GatewayIntent;
import org.bukkit.plugin.java.JavaPlugin;

import javax.security.auth.login.LoginException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;


public class Main extends JavaPlugin {

    //Inspirational quote of the day: what one developer can do in two days, two developers can do in four
    public static JDA discordbot;
    public static Main instance;
    public static ConfigFile configFile;


    @Override
    public void onEnable() {

        instance = this;


        if (!getDataFolder().exists())
            getDataFolder().mkdir();

        configFile = new ConfigFile(instance);
        ConfigFile.setup();

        awakenTheKangaroo();


        super.onEnable();

        //listeners
        getServer().getPluginManager().registerEvents(new JoinAndQuit(), this);
        getServer().getPluginManager().registerEvents(new PlayerChat(), this);
        getServer().getPluginManager().registerEvents(new PlayerDeath(), this);
        getServer().getPluginManager().registerEvents(new PlayerSpy(), this);
        getServer().getPluginManager().registerEvents(new GrappleListener(), this);
        getServer().getPluginManager().registerEvents(new CommandListener(), this);
        getServer().getPluginManager().registerEvents(new ServerCommandListener(), this);


        //commands
        Objects.requireNonNull(this.getCommand("discord")).setExecutor(new DiscordCommand());
        Objects.requireNonNull(this.getCommand("grapplinghook")).setExecutor(new GrapplingHook());
        Objects.requireNonNull(this.getCommand("back")).setExecutor(new BackCommand());
        Objects.requireNonNull(this.getCommand("trashcan")).setExecutor(new TrashcanCommand());
        Objects.requireNonNull(this.getCommand("kgrl")).setExecutor(new ReloadCommand());
        Objects.requireNonNull(this.getCommand("wiener")).setExecutor(new FunCommand());

        // THIS STATEMENT NEEDS TO REMAIN AT THE END OF THE METHOD
        sendStartStopMessageToDiscord(true);
    }

    @Override
    public void onDisable() {
        // THIS STATEMENT NEEDS TO BE AT THE BEGINNING OF THE METHOD
        //sendStartStopMessageToDiscord(false);
        clearOnlineRole();
        discordbot.shutdownNow();
        instance = null;
        //FIXME this breaks
    }

    private void clearOnlineRole() {
        //fetch members
        List<Member> members = getGuild().getMembers();
        try {
            Role onlineRole = getGuild().getRolesByName("online in-game", true).get(0);
            for (Member user : members) {
                if (user.getRoles().contains(onlineRole)) {
                    getGuild().removeRoleFromMember(user, onlineRole).complete();
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

        JDABuilder jdaBuilder = JDABuilder.createDefault(ConfigFile.getDiscordBotID());



        jdaBuilder.enableIntents(gatewayIntents);
        jdaBuilder.addEventListeners(new onGuildJoin());
        jdaBuilder.addEventListeners(new onChat());

        jdaBuilder.setActivity(Activity.playing(ConfigFile.getBotStatus()));

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

    public static Main getInstance() {
        return instance;
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
        final TextChannel DISCORD_MINECRAFT_CHANNEL = ConfigFile.getMinecraftChannel(discordbot);

        EmbedBuilder message = new EmbedBuilder();
        if (isStarting) {
            message.setTitle("Server Online!");
            message.setColor(0x42f545);
            message.setDescription(ConfigFile.getDiscordOnlineMessage());
        } else {
            message.setTitle("Server Offline.");
            message.setColor(0xeb4034);
            message.setDescription(ConfigFile.getDiscordOfflineMessage());
        }

        assert DISCORD_MINECRAFT_CHANNEL != null;
        Objects.requireNonNull(DISCORD_MINECRAFT_CHANNEL.sendMessage(message.build())).queue();

        message.clear();


    }

    public static void log(Level level, String message) {
        instance.getServer().getLogger().log(level, message);
    }

    public static void debug(String message) {
        if(configFile.isDebugEnabled()) {
            instance.getServer().getLogger().log(Level.INFO, "Debug: " + message);
        }
    }


}
