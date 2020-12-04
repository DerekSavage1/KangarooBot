import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import javax.security.auth.login.LoginException;
import java.util.List;


public class Main extends JavaPlugin {

    public static JDA discordbot;
    public static Main instance;

    public static void sendToDiscord(String username, String message) {
        TextChannel GAME_TEXT_CHANNEL = discordbot.getTextChannelById("783165153213546537");
        TextChannel ADMIN_TEXT_CHANNEL = discordbot.getTextChannelById("783165108859174972");
        GAME_TEXT_CHANNEL.sendMessage(username + " » " + message).queue();
        ADMIN_TEXT_CHANNEL.sendMessage(username + " » " + message).queue();
    }

    public static void sendToAdminDiscord(String username, String message) {
        TextChannel ADMIN_TEXT_CHANNEL = discordbot.getTextChannelById("783165108859174972");
        ADMIN_TEXT_CHANNEL.sendMessage(username + ": " + message).queue();
    }

    public static void addUser(String name) {
        TextChannel ADMIN_TEXT_CHANNEL = discordbot.getTextChannelById("783165108859174972");
        List<User> targetUsers = discordbot.getUsersByName(name,false);
        ADMIN_TEXT_CHANNEL.sendMessage(targetUsers.toString()).queue();

    }

    @Override
    public void onEnable() {
        instance = this;
        super.onEnable();
        try {
            discordbot = JDABuilder.createDefault("NzgwNTQ4NTQyNDgxMzAxNTI2.X7wseg.t1CXGxEgE86R6K7COxpzR5_9Rxo").build();
        } catch (LoginException e) {
            e.printStackTrace();
        }


        discordbot.addEventListener(new DEvents());
        discordbot.addEventListener(new onGuildJoin());
        Bukkit.getServer().getPluginManager().registerEvents(new MEvents(), this);
        this.getCommand("discord").setExecutor(new MCommands());
        //TODO add event listener
    }

    @Override
    public void onDisable() {
        discordbot.shutdown();
        //FIXME this breaks
    }

    public static Main getInstance() {
        return instance;
    }

    public static JDA getDiscordbot() {
        return discordbot;
    }

}
