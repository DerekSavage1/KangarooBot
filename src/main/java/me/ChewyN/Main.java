package me.ChewyN;

import me.ChewyN.DListeners.onChat;
import me.ChewyN.DListeners.onGuildJoin;
import me.ChewyN.MCommands.DiscordCommand;
import me.ChewyN.MCommands.TPermissionCommand;
import me.ChewyN.MListeners.PlayerChat;
import me.ChewyN.MListeners.PlayerListener;
import me.ChewyN.managers.PermissionsManager;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.requests.GatewayIntent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import javax.security.auth.login.LoginException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;


public class Main extends JavaPlugin {

    public static JDA discordbot;
    public static Main instance;
    public static TextChannel GAME_TEXT_CHANNEL;
    public static TextChannel ADMIN_TEXT_CHANNEL;

    public static void sendToDiscord(String username, String message) {
        GAME_TEXT_CHANNEL.sendMessage("`" + username + " »` " + message).queue();
        ADMIN_TEXT_CHANNEL.sendMessage("`" + username + " »` " + message).queue();
    }

    public static void sendToAdminDiscord(String username, String message) {
        ADMIN_TEXT_CHANNEL.sendMessage("`" + username + ": ` " + message).queue();
    }

    public static void addUser(String name) {
        List<User> targetUsers = discordbot.getUsersByName(name,false);
        ADMIN_TEXT_CHANNEL.sendMessage(targetUsers.toString()).queue();

    }

    @Override
    public void onEnable() {

        awakenTheKangaroo();

//        debug(Level.INFO, "debug-mode is enabled in the config.yml, debug messages will appear until you set this to false.");

        getCommand("tpermissions").setExecutor(new TPermissionCommand());

        getServer().getPluginManager().registerEvents(new PlayerListener(), this);

        for(Player players : Bukkit.getOnlinePlayers()) {
            PermissionsManager.getPermissionsManager().reload(players);
        }

        instance = this;
        super.onEnable();


//        discordbot.addEventListener(new DEvents());
//        discordbot.addEventListener(new onGuildJoin());
        Bukkit.getServer().getPluginManager().registerEvents(new PlayerChat(), this);
        this.getCommand("discord").setExecutor(new DiscordCommand());
    }

    @Override
    public void onDisable() {
        for(Player players : Bukkit.getOnlinePlayers()) {
            PermissionsManager.getPermissionsManager().clear(players);
        }
        clearOnlineRole();
        discordbot.shutdown();
        instance = null;
        //FIXME this breaks
    }

    private void clearOnlineRole() {
        Member[] members = (Member[]) getGuild().getMembers().toArray();
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

        GAME_TEXT_CHANNEL = discordbot.getTextChannelById("783165153213546537");
        ADMIN_TEXT_CHANNEL = discordbot.getTextChannelById("783165108859174972");
    }

    public static Main getInstance() {
        return instance;
    }

    public static JDA getDiscordbot() {
        return discordbot;
    }

    public void debug(Level level, String message) {
//		if(UserSettings.getSettings().isDebugEnabled()) {
        getLogger().log(level, message);
//		}
    }

    public static Guild getGuild() {
        return discordbot.getGuildById("767668284559851560");
    }

}
