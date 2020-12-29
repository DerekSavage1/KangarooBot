package me.ChewyN.Discord.Listeners;

import me.ChewyN.Minecraft.Commands.DiscordCommand;
import me.ChewyN.Main;
import net.dv8tion.jda.api.entities.Invite;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.*;

import static me.ChewyN.Main.getGuild;

public class onGuildJoin extends ListenerAdapter {



    @Override
    public void onGuildMemberJoin(GuildMemberJoinEvent e) {


        //Making activeCode array from discord
        e.getMember().getEffectiveName();
        List<Invite> activeInvites = e.getGuild().retrieveInvites().complete();

        String[] activeCodes = new String[activeInvites.size()];
        for(int j = 0; j<activeInvites.size(); j++) {
            activeCodes[j] = activeInvites.get(j).getCode();
        }

        //Making sentCode array from minecraft
        HashMap<String, String> playerInvite = DiscordCommand.getPlayerInvite();
        String[] sentCodes = playerInvite.keySet().toArray(new String[0]);


        //Checking if any codes that were sent my kangaroo are now missing.
        //Is an invite is missing it is because the user just joined.
        List<String> usedCode = getUsedCode(sentCodes, activeCodes);
        if(usedCode.size() > 1) {
            Main.getInstance().getLogger().warning("OnGuildJoin: getUsedCode returned more than one value!");
        }
        String mcName = playerInvite.get(usedCode.get(0));
        e.getMember().modifyNickname(mcName).complete();


        Role verified = e.getGuild().getRolesByName("Verified", true).get(0);
        e.getGuild().addRoleToMember(e.getMember(), verified).queue();
        DiscordCommand.removeFromPlayerInvite(usedCode.get(0));
        setOnlineRole(e.getMember());
    }

    private static List<String> getUsedCode(String [] first, String [] second) {
        List<String> missing = new ArrayList<>(new HashSet<>(Arrays.asList(first)));
        for (String num : second) {
            missing.remove(num);
        }
        return missing;
    }

    private void setOnlineRole(Member member) {
        Role		onlineRole = getGuild().getRolesByName("online in-game", true).get(0);

        getGuild().addRoleToMember(member, onlineRole).complete();
    }

    private void verifyNewMember() {

    }

}
