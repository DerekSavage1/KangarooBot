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
import me.Skyla.Minecraft.Commands.TrashcanCommand;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.requests.GatewayIntent;
import org.bukkit.plugin.java.JavaPlugin;

import javax.security.auth.login.LoginException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class Main extends JavaPlugin {

    //Inspirational quote of the day: what one developer can do in two days, two developers can do in four
    public static JDA discordbot;
    public static Main instance;
    public static ConfigFile configFile;


    @Override
    public void onEnable() {

        instance = this;



        if(!getDataFolder().exists())
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


        //commands
        Objects.requireNonNull(this.getCommand("discord")).setExecutor(new DiscordCommand());
        Objects.requireNonNull(this.getCommand("grapplinghook")).setExecutor(new GrapplingHook());
        Objects.requireNonNull(this.getCommand("back")).setExecutor(new BackCommand());
        Objects.requireNonNull(this.getCommand("trashcan")).setExecutor(new TrashcanCommand());
    }

    @Override
    public void onDisable() {
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



}
