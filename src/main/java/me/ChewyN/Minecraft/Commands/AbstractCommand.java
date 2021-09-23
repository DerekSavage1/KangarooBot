package me.ChewyN.Minecraft.Commands;

import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public abstract class AbstractCommand implements CommandExecutor {

    private boolean isCommandEnabled;
    private boolean senderIsConsole;
    private Player  player;


    public AbstractCommand() {
        isCommandEnabled = true;
    }

    public void setup(CommandSender commandSender, boolean isCommandEnabled) {
        setCommandSender(commandSender);
        setCommandEnabled(isCommandEnabled);
        setSenderIsConsole(commandSender instanceof Player);
        if(!senderIsConsole) {
            assert commandSender instanceof Player;
            setPlayer((Player) commandSender);
        }
    }

    public boolean isCommandEnabled() {
        return !isCommandEnabled;
    }

    public void setCommandEnabled(boolean commandEnabled) {
        isCommandEnabled = commandEnabled;
    }

    public void setSenderIsConsole(boolean senderIsConsole) {
        this.senderIsConsole = senderIsConsole;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player p) {
        this.player = p;
    }

    /**
     * Sets the command sender to a player. Use only if the sender is a player.
     * @param s The command sender
     */
    public void setCommandSender(CommandSender s) {
        if(s instanceof Player)
            setPlayer((Player) s);
    }
}
