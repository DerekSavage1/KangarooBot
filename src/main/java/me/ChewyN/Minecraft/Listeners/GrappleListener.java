package me.ChewyN.Minecraft.Listeners;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.Vector;

public class GrappleListener implements Listener {

    @EventHandler
    public void onGrapple(PlayerFishEvent e) {
        Player p = e.getPlayer();

        if(isHoldingGrapple(p)) {

            if(e.getState().equals(PlayerFishEvent.State.IN_GROUND)) {

                if(p.isGliding()) {
                    p.sendMessage("It is too difficult to grapple while flying!");
                    return;
                }
                if(p.getLocation().distance(e.getHook().getLocation()) < 6) {
                    pullPlayerSlightly(p, e.getHook().getLocation());
                } else {
                    pullPlayerToLocation(p, e.getHook().getLocation());
                }
            }
        }
    }

    private boolean isHoldingGrapple(Player p) {

        String fishingRodLore = "Explore the world with ease";


        ItemMeta mainHandMeta = p.getInventory().getItemInMainHand().getItemMeta();
        assert mainHandMeta != null;

        ItemMeta offHandMeta = p.getInventory().getItemInOffHand().getItemMeta();
        assert offHandMeta != null;


        if(mainHandMeta.hasLore()) {
            assert mainHandMeta.getLore() != null;
            return mainHandMeta.getLore().contains(fishingRodLore);
        }

        if(!offHandMeta.hasLore()) {
            assert offHandMeta.getLore() != null;
            return offHandMeta.getLore().contains(fishingRodLore);
        }

        return false;
    }

    private void pullPlayerSlightly(Player p, Location loc){
        if(loc.getY() > p.getLocation().getY()){
            p.setVelocity(new Vector(0,0.25,0));
            return;
        }

        Location playerLoc = p.getLocation();

        Vector vector = loc.toVector().subtract(playerLoc.toVector());
        p.setVelocity(vector);
    }

    private void pullPlayerToLocation(Player p, Location loc) {
        Location playerLoc = p.getLocation();


        double g = -0.08;
        double t = loc.distance(playerLoc);
        double v_x = (1.0 + 0.07 * t) * (loc.getX() - playerLoc.getX()) / t;
        double v_y = (1.0 + 0.03 * t) * (loc.getY() - playerLoc.getY()) / t - 0.5 * g * t;
        double v_z = (1.0 + 0.07 * t) * (loc.getZ() - playerLoc.getZ()) / t;

        Vector v = p.getVelocity();
        v.setX(v_x);
        v.setY(v_y);
        v.setZ(v_z);
        p.setVelocity(v);

    }

}
