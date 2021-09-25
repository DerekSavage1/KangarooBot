package me.Skyla.Minecraft.Commands;

import me.ChewyN.Discord.Listeners.DiscordMessageHandler;
import me.ChewyN.Minecraft.Commands.AbstractCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class SayCommand extends AbstractCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) throws NullPointerException {

        if(sender instanceof Player)
            return true;

        String consoleMessage = "";
        for (String string: strings) {
            consoleMessage = consoleMessage.concat(" " + string);
        }

        DiscordMessageHandler.sendToBothDiscordChannels("Console", consoleMessage);

        return true;
    }
}
