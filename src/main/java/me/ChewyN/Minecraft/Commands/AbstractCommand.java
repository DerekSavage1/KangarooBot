package me.ChewyN.Minecraft.Commands;

public abstract class AbstractCommand {
    protected boolean enabled;
    protected boolean isSenderConsole;

    public AbstractCommand() {
        enabled = true;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }



}
