package me.Skyla.Minecraft.Objects;


import org.bukkit.Location;


/**
 * Class that holds the death information of a player. Holds the location of the death and if the player has teleported back or not.
 *
 * @Author Skyla
 */
public class DeathStatus {

    private final Location l;
    private Boolean hasTeleported;

    /**
     * Constructor.
     *
     * @param loc A player's death location
     */
    public DeathStatus(Location loc) {
        l = loc;
        hasTeleported = false;
    }

    /**
     * Gets a player's death location
     *
     * @return the player's death location
     */
    public Location getLocation() {
        return l;
    }

    /**
     * Get the teleportation status of a player
     *
     * @return A player's tp status
     */
    public Boolean getTPStatus() {
        return hasTeleported;
    }

    /**
     * Sets the tp status of a player
     *
     * @param status true if the player has teleported, false if not
     */
    public void setTPStatus(Boolean status) {
        hasTeleported = status;
    }

}
