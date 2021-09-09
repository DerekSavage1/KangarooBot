package me.ChewyN;

import me.ChewyN.Discord.Listeners.onChat;
import me.ChewyN.Discord.Listeners.onGuildJoin;
import me.ChewyN.Minecraft.Commands.DiscordCommand;
import me.ChewyN.Minecraft.Commands.GrapplingHook;
import me.ChewyN.Minecraft.Listeners.GrappleListener;
import me.ChewyN.Minecraft.Listeners.Player.JoinAndQuit;
import me.ChewyN.Minecraft.Listeners.Player.PlayerChat;
import me.ChewyN.Minecraft.Listeners.Player.PlayerDeath;
import me.ChewyN.Minecraft.Listeners.Player.PlayerSpy;
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

    public static JDA discordbot;
    public static Main instance;





    @Override
    public void onEnable() {

        awakenTheKangaroo();

//        debug(Level.INFO, "debug-mode is enabled in the config.yml, debug messages will appear until you set this to false.");


        instance = this;
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
    }

    @Override
    public void onDisable() {
        clearOnlineRole();
        discordbot.shutdown();
        instance = null;
        //FIXME this breaks
    }

    private void clearOnlineRole() {
        //fetch members
        List<Member> members = getGuild().getMembers();
        Role onlineRole = getGuild().getRolesByName("online in-game", true).get(0);
        for(Member user : members) {
            if(user.getRoles().contains(onlineRole)) {
                getGuild().removeRoleFromMember(user, onlineRole).complete();
            }
        }
    }

    private void awakenTheKangaroo() {
        List<GatewayIntent> gatewayIntents = new ArrayList<>();
        gatewayIntents.add(GatewayIntent.GUILD_MEMBERS);
        gatewayIntents.add(GatewayIntent.GUILD_PRESENCES);
        JDABuilder jdaBuilder = JDABuilder.createDefault("NzgwNTQ4NTQyNDgxMzAxNTI2.X7wseg.t1CXGxEgE86R6K7COxpzR5_9Rxo");
        jdaBuilder.enableIntents(gatewayIntents);
        jdaBuilder.addEventListeners(new onGuildJoin());
        jdaBuilder.addEventListeners(new onChat());
        jdaBuilder.setActivity(Activity.playing("Jumping simulator"));

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



        me.ChewyN.Discord.Util.TextChannels.setGameTextChannel(discordbot.getTextChannelById("883249426062266409"));
        me.ChewyN.Discord.Util.TextChannels.setAdminTextChannel(discordbot.getTextChannelById("883249317111013397"));
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
