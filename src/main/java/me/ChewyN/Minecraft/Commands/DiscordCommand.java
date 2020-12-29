package me.ChewyN.Minecraft.Commands;

import me.ChewyN.Main;
import net.dv8tion.jda.api.entities.GuildChannel;
import net.dv8tion.jda.api.entities.Invite;
import net.dv8tion.jda.api.events.guild.invite.GuildInviteCreateEvent;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

public class DiscordCommand implements CommandExecutor {

    public static HashMap<String, String> playerInvite = new HashMap<>();

    @Override
    public boolean onCommand(@NotNull CommandSender s, @NotNull Command cmd, @NotNull String label, String[] args) {
        //creates discord link
        GuildChannel welcomeChannel =  Main.getDiscordbot().getGuildChannelById("784154167467966495");
        assert welcomeChannel != null;
        Invite invite = welcomeChannel.createInvite().setMaxAge(100).setMaxUses(1).setUnique(true).setTemporary(true).complete();

        GuildInviteCreateEvent guildInvite = new GuildInviteCreateEvent(Main.getDiscordbot(),5, invite, welcomeChannel);
        String inviteCode = guildInvite.getInvite().getCode();
        String discordLink = guildInvite.getUrl();

        //sends discord link
        TextComponent message = new TextComponent("Click here to join our discord!");
        message.setColor(ChatColor.AQUA);
        message.setUnderlined(true);
        message.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, discordLink));

        if(s instanceof Player) {
            Player p = (Player) s;
            playerInvite.put(inviteCode, p.getPlayerListName());
            p.spigot().sendMessage(message);
            return true;
        } else {
            s.sendMessage("Discord link is: " + discordLink);
        }

        return false;
    }

    public static HashMap<String, String> getPlayerInvite() {
        return playerInvite;
    }

    public static void removeFromPlayerInvite(String key) {
        playerInvite.remove(key);
    }

}
