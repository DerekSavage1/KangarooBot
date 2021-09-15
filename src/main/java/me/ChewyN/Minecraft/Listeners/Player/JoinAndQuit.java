package me.ChewyN.Minecraft.Listeners.Player;


//import me.ChewyN.Minecraft.Packets.packet;

import me.ChewyN.Discord.Listeners.DiscordMessageHandler;
import me.ChewyN.Minecraft.Util.MinecraftMessageHandler;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.TextChannel;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.List;
import java.util.logging.Level;

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

    //TODO: Put in discord message handler
    private void sendJoinOrQuitMessageToDiscord(Player player, boolean isJoining) {
        String playerUUID = player.getUniqueId().toString().replaceAll("-", "");
        String faceURL = "https://minotar.net/avatar/" + playerUUID + "/25"; //discord wants as string
        String playerName = player.getPlayerListName();
        int playerCount = Bukkit.getOnlinePlayers().size();
        final TextChannel DISCORD_MINECRAFT_CHANNEL = ConfigFile.getMinecraftChannel(discordbot);

        EmbedBuilder joinMessage = new EmbedBuilder();
        joinMessage.setThumbnail(faceURL);
        if (isJoining) {
            joinMessage.setTitle(playerName + ConfigFile.getDiscordPlayerJoinMessage());
            joinMessage.setColor(0x42f545);

        } else { //isleaving
            joinMessage.setTitle(playerName + ConfigFile.getDiscordPlayerLeaveMessage());
            joinMessage.setColor(0xeb4034);
            playerCount--; //I removed this thinking it was unnecessary and it is very necessary
        }

        if (playerCount <= 0) {
            joinMessage.setDescription("No players online");
            DiscordMessageHandler.sendToBothDiscordChannels(joinMessage.build());
            joinMessage.clear();
            return;
        }

        //Building description
        StringBuilder description = new StringBuilder("Now " + playerCount + " players online: \n");
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (!isJoining && p.getPlayerListName().equals(player.getPlayerListName()))
                continue; //if they are quitting leave out their name
            description.append("`").append(p.getPlayerListName()).append("`, ");
        }

        description = new StringBuilder(description.substring(0, description.length() - 2)); //removing the space and comma at the end
        joinMessage.setDescription(description.toString());

        DiscordMessageHandler.sendToBothDiscordChannels(joinMessage.build());

        joinMessage.clear();

    } //discord message

}
