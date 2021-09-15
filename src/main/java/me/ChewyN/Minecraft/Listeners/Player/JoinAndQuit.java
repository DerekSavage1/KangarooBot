package me.ChewyN.Minecraft.Listeners.Player;


//import me.ChewyN.Minecraft.Packets.packet;

import me.ChewyN.Data.ConfigFile;
import me.ChewyN.Minecraft.Util.MinecraftMessageHandler;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.List;
import java.util.logging.Level;

import static me.ChewyN.Discord.Listeners.DiscordMessageHandler.sendJoinOrQuitMessageToDiscord;
import static me.ChewyN.Main.*;

public class JoinAndQuit implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();

        modifyJoinMessage(e);
        if (ConfigFile.discordJoinLeaveMessagesEnabled(getConfigFile()))
            sendJoinOrQuitMessageToDiscord(p, true);
        setDiscordOnlineRole(p.getPlayerListName(), true);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        Player p = e.getPlayer();

        setDiscordOnlineRole(p.getPlayerListName(), false);
        if(ConfigFile.discordJoinLeaveMessagesEnabled(getConfigFile()))
            sendJoinOrQuitMessageToDiscord(p, false);
    }

    private void modifyJoinMessage(PlayerJoinEvent e) {
        e.setJoinMessage("");

        Player p = e.getPlayer();
        if (!p.hasPlayedBefore()) {
            if (ConfigFile.getWelcomeMessageEnabled(getConfigFile())) {
                MinecraftMessageHandler.broadcastCenteredMessage(ChatColor.AQUA + "Please welcome " + p.getName() + " to our Chewy's Hub!"); //TODO: make this customizable
                MinecraftMessageHandler.broadcastCenteredMessage(ChatColor.AQUA + "This is their first time playing!"); //TODO: make this customizable
            }
        }

        if (ConfigFile.getJoinMessageEnabled(getConfigFile()))
            MinecraftMessageHandler.broadcastCenteredMessage(ChatColor.YELLOW + p.getName() + ChatColor.AQUA + ConfigFile.getWelcomeMessage(getConfigFile()));
    }

    //TODO put in discord handler
    public void setDiscordOnlineRole(String nickname, boolean setOnline) {
        String onlineRoleName = ConfigFile.getOnlineRoleName(getConfigFile());
        List<Member> members = getGuild().loadMembers().get();

        if(getGuild().getRolesByName(onlineRoleName, true).get(0) == null) {
            log(Level.WARNING, "No role found with name " + onlineRoleName);
        }
        Role onlineRole = getGuild().getRolesByName(onlineRoleName, true).get(0);

        Member match = null;

        for (Member member : members) {
            if (member.getNickname() == null) continue;

            if (member.getNickname().equalsIgnoreCase(nickname)) {
                match = member;
                break;
            }
        }

        if (match == null) {
            debug("No match found!");
            return;
        }

        if (setOnline) {
            getGuild().addRoleToMember(match, onlineRole).complete();
        } else {
            getGuild().removeRoleFromMember(match, onlineRole).complete();
        }
    }
}
