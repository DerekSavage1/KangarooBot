import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class onGuildJoin extends ListenerAdapter {

    public void onGuildJoin(GuildMemberJoinEvent e) {
        User user = e.getUser();
        user.openPrivateChannel().queue((channel) ->
        {
            channel.sendMessage("Hello There").queue();
        });
        Main.getDiscordbot().getUserById(e.getUser().getId());//TODO
        e.getUser().getId();
    }
}
