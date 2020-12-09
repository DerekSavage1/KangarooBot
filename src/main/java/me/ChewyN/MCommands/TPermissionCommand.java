package me.ChewyN.MCommands;

import me.ChewyN.Main;
import me.ChewyN.UserSettings;
import me.ChewyN.managers.PermissionsManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

public class TPermissionCommand implements CommandExecutor{

	@Override
	public boolean onCommand(@NotNull CommandSender s,@NotNull Command cmd,@NotNull String label,@NotNull String[] args) {
		
		if(s instanceof Player) {
			Player player = (Player) s;
			
			String targetPermission = args[0];
			List<String> permissions;
			FileConfiguration fileConfiguration = UserSettings.getSettings().getFile("permissions");
			
			if(fileConfiguration.getStringList("data." + player.getUniqueId() + ".permissions") != null) {
				permissions = UserSettings.getSettings().getFile("permissions").getStringList("data." + player.getUniqueId() + ".permissions");
			} else {
				fileConfiguration.set("data." + player.getUniqueId().toString() + ".name", player.getName());
				fileConfiguration.set("data." + player.getUniqueId().toString() + ".permissions", new ArrayList<>());
				permissions = new ArrayList<>();
			}
			
			permissions.add(targetPermission);
			fileConfiguration.set("data." + player.getUniqueId().toString() + ".permissions", permissions);
			UserSettings.getSettings().save(fileConfiguration);
			PermissionsManager.getPermissionsManager().reload(player);
			
			Main.getInstance().debug(Level.INFO, "Added new permission to " + player.getName() + ", permission is " + targetPermission);
			
		}
		
		return false;
	}

}
