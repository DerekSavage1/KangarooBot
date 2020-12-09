package me.ChewyN.managers;

import me.ChewyN.Main;
import me.ChewyN.UserSettings;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachment;

import java.util.*;
import java.util.logging.Level;


public class PermissionsManager {

	private static PermissionsManager permissionsManager = new PermissionsManager();
	private PermissionsManager() {}
	public static PermissionsManager getPermissionsManager() { return permissionsManager; }

	private Map<UUID, PermissionAttachment> permissionsDataMap = new HashMap<>();
	
	public PermissionAttachment getPermissionData(Player player) {
		UUID uuid = player.getUniqueId();
		if(permissionsDataMap.containsKey(uuid)) {
			return permissionsDataMap.get(uuid);
		} else {
			PermissionAttachment permissionAttachment = player.addAttachment(Main.getInstance());
			permissionsDataMap.put(uuid,  permissionAttachment);
			return permissionsDataMap.get(uuid);
		}
	}
	
	public void insertPermission(Player player, String permission) {
		getPermissionData(player).setPermission(permission, true);
		
		Main.getInstance().debug(Level.INFO, "Added permission " + permission + " to " + player.getName() + " for this runtime.");
	}
	
	public void clear(Player player) {
		permissionsDataMap.remove(player.getUniqueId());
		Main.getInstance().debug(Level.INFO, "Cleared permissions for " + player.getName() + " for this runtime.");
	}
	
	public void reload(Player player) {
		List<String> permissions;
		UserSettings.getSettings().getFile("permissions").getStringList("data." + player.getUniqueId() + ".permissions");
		permissions = UserSettings.getSettings().getFile("permissions").getStringList("data." + player.getUniqueId() + ".permissions");

		for (String permission : permissions) {
			insertPermission(player, permission);
		}
	}
	
	public void refresh(Player player) {
		FileConfiguration fileConfiguration = UserSettings.getSettings().getFile("permissions");
		fileConfiguration.set("data." + player.getUniqueId().toString() + ".name", player.getName());
		UserSettings.getSettings().save(fileConfiguration);
	}
}
