package me.ChewyN.Minecraft.Commands;

import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public abstract class AbstractCommand implements CommandExecutor {


    @NotNull private    boolean isCommandEnabled;
    private             boolean senderIsConsole;
    private             boolean ConsoleSenderAllowed;
    private             Player p;
    private             CommandSender s;

    public AbstractCommand() {
        isCommandEnabled = true;
    }

    public void setup(CommandSender s, boolean isCommandEnabled, boolean isConsoleSenderAllowed) {
        setSender(s);
        setCommandEnabled(isCommandEnabled);
        setSenderIsConsole(s instanceof Player);
        setConsoleSenderAllowed(isConsoleSenderAllowed);
        if(!senderIsConsole) setPlayer((Player) s);
    }

    public boolean isCommandEnabled() {
        return isCommandEnabled;
    }

    public void setCommandEnabled(boolean commandEnabled) {
        isCommandEnabled = commandEnabled;
    }

    public boolean isSenderIsConsole() {
        return senderIsConsole;
    }

    public void setSenderIsConsole(boolean senderIsConsole) {
        this.senderIsConsole = senderIsConsole;
    }

    public boolean isConsoleSenderAllowed() {
        return ConsoleSenderAllowed;
    }

    public void setConsoleSenderAllowed(boolean consoleSenderAllowed) {
        ConsoleSenderAllowed = consoleSenderAllowed;
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

    /**
     * Sets the command sender to a player. Use only if the sender is a player.
     * @param s The command sender
     */
    public void setSender(CommandSender s) {
        this.s = s;
        if(s instanceof Player)
            setPlayer((Player) s);
    }
}
