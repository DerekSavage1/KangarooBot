package me.Skyla.Minecraft.Objects;


import org.bukkit.Location;

public class DeathStatus {

    private Location l;
    private Boolean hasTeleported = false;

    public DeathStatus (Location loc) {
        l = loc;
    }

    public Location getLocation() {
        return l;
    }

    public Boolean getTPStatus() {
        return hasTeleported;
    }

    public void setTPStatus(Boolean status) {
        hasTeleported = status;
    }

}
