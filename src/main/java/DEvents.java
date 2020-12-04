import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.bukkit.ChatColor;
import org.jetbrains.annotations.NotNull;

public class DEvents extends ListenerAdapter {



    public void onMessageReceived(@NotNull MessageReceivedEvent e) {
        String      DMessage = e.getMessage().getContentRaw();
        User        user = e.getAuthor();
        String      userName = user.getName();
        TextChannel GAME_TEXT_CHANNEL = Main.discordbot.getTextChannelById("783165153213546537");
        if(e.getChannel().equals(GAME_TEXT_CHANNEL)) {
            if(!user.isBot()) {
                Main.getInstance().getServer().broadcastMessage(ChatColor.GRAY + userName + ": " + ChatColor.WHITE + DMessage);
            }
        }
    }

    public void onGuildJoin(GuildMemberJoinEvent e) {
        e.getUser().getName();
        Main.getDiscordbot().getUserById(e.getUser().getId());//TODO
        e.getUser().getId();
    }


}
