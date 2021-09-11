package me.ChewyN.Minecraft.Commands;

import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public abstract class AbstractCommand implements CommandExecutor {


    @NotNull private    boolean isCommandEnabled;
    private             boolean senderIsConsole;
    private             boolean ConsoleSenderAllowed;
    private             Player player;
    private             CommandSender commandSender;

    public AbstractCommand() {
        isCommandEnabled = true;
    }

    public void setup(CommandSender commandSender, boolean isCommandEnabled, boolean isConsoleSenderAllowed) {
        setCommandSender(commandSender);
        setCommandEnabled(isCommandEnabled);
        setSenderIsConsole(commandSender instanceof Player);
        setConsoleSenderAllowed(isConsoleSenderAllowed);
        if(!senderIsConsole) setPlayer((Player) commandSender);
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
        return player;
    }

    public void setPlayer(Player p) {
        this.player = p;
    }

    public CommandSender getSender() {
        return commandSender;
    }

    /**
     * Sets the command sender to a player. Use only if the sender is a player.
     * @param s The command sender
     */
    public void setSender(CommandSender s) {
        this.s = s;
    public void setCommandSender(CommandSender s) {
        this.commandSender = s;
        if(s instanceof Player)
            setPlayer((Player) s);
    }
}
