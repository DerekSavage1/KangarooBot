package me.ChewyN.Minecraft.Commands;

import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public abstract class AbstractCommand implements CommandExecutor {


    @NotNull private    boolean isCommandEnabled;
    private             boolean isSenderConsole;
    private             boolean isConsoleSenderAllowed;
    private             Player p;
    private             CommandSender s;

    public AbstractCommand() {
        isCommandEnabled = true;
    }

    public void setup(CommandSender _s) {
        s = _s;
        isCommandEnabled = true;
        isSenderConsole = _s instanceof Player;
        isConsoleSenderAllowed = true;
    }

    public void setup(CommandSender _s, boolean _isCommandEnabled) {
        s = _s;
        isCommandEnabled = _isCommandEnabled;
        isSenderConsole = _s instanceof Player;
        isConsoleSenderAllowed = true;
    }

    public void setup(CommandSender _s, boolean _isCommandEnabled, boolean _isConsoleSenderAllowed) {
        s = _s;
        isCommandEnabled = _isCommandEnabled;
        isSenderConsole = _s instanceof Player;
        isConsoleSenderAllowed = _isConsoleSenderAllowed;
    }

    public boolean isCommandEnabled() {
        return isCommandEnabled;
    }

    public void setCommandEnabled(boolean commandEnabled) {
        isCommandEnabled = commandEnabled;
    }

    public boolean isSenderConsole() {
        return isSenderConsole;
    }

    public void setSenderConsole(boolean senderConsole) {
        isSenderConsole = senderConsole;
    }

    public boolean isConsoleSenderAllowed() {
        return isConsoleSenderAllowed;
    }

    public void setConsoleSenderAllowed(boolean consoleSenderAllowed) {
        isConsoleSenderAllowed = consoleSenderAllowed;
    }

    public Player getPlayer() {
        return p;
    }

    public void setPlayer(Player p) {
        this.p = p;
    }

    public CommandSender getSender() {
        return s;
    }

    public void setSender(CommandSender s) {
        this.s = s;
        if(s instanceof Player)
            setPlayer((Player) s);
    }
}
