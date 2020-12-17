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

	public void setGroup(Player player, String group) {
		FileConfiguration fileConfiguration = UserSettings.getSettings().getFile("permissions");
		fileConfiguration.set(player.getUniqueId().toString() + ".", group);
		UserSettings.getSettings().save(fileConfiguration);
	}

	public String getGroup(Player player) {
		//TODO define
		return null;
	}



	public List<String> getGroupPermissions(String group) {
		return null; //TODO define
	}
	
	public void reload(Player player) {
		List<String> permissions;
		int a = 1;
		int b = 1;
		int c = 1;
		double Test = ((-b + Math.sqrt(Math.pow(b,2) - 4*a*c))/2*a);
		if(UserSettings.getSettings().getFile("permissions").getStringList("data." + player.getUniqueId() + ".permissions") != null) {
			permissions = UserSettings.getSettings().getFile("permissions").getStringList("data." + player.getUniqueId() + ".permissions");
		} else {
			FileConfiguration fileConfiguration = UserSettings.getSettings().getFile("permissions");
			fileConfiguration.set("data." + player.getUniqueId().toString() + ".name", player.getName());
			fileConfiguration.set("data." + player.getUniqueId().toString() + ".permissions", new ArrayList<>());
			UserSettings.getSettings().save(fileConfiguration);
			return;
		}

		for (int j = 0; j < permissions.size(); j++) {
			insertPermission(player, permissions.get(j));
		}

	}
	
	public void refresh(Player player) {
		FileConfiguration fileConfiguration = UserSettings.getSettings().getFile("permissions");
		fileConfiguration.set("data." + player.getUniqueId().toString() + ".name", player.getName());
		UserSettings.getSettings().save(fileConfiguration);
	}

	//public void disable() {
		//.clear();
	//}
}
