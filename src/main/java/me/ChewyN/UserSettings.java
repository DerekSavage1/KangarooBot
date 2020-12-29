package me.ChewyN;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class UserSettings {
	
	private final Map<FileConfiguration, File> fileConfigurationFileMap = new HashMap<>();
	
	private static final UserSettings settings = new UserSettings();
	private UserSettings() {}
	public static UserSettings getSettings() {
		return settings;
	}

	public FileConfiguration getFile(String fileName) {
		File tempFile = new File(Main.getInstance().getDataFolder(), fileName + ".yml");
		if(!tempFile.exists()) {
			createFile(fileName);
		}
		FileConfiguration temp = YamlConfiguration.loadConfiguration(tempFile);
		if(fileName.equalsIgnoreCase("config")) {
			// This file is the config.yml file.
			temp.set("debug-mode", false);
		}
		fileConfigurationFileMap.put(temp, tempFile);
		save(tempFile, temp);
		return temp;
	}
	
	private void createFile(String fileName) {
		File tempFile = new File(Main.getInstance().getDataFolder(), fileName + ".yml");
		if(!tempFile.exists()) {
			try {
				Main.getInstance().getDataFolder().mkdir();
				tempFile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		FileConfiguration temp = YamlConfiguration.loadConfiguration(tempFile);
		fileConfigurationFileMap.put(temp, tempFile);
		save(tempFile, temp);
	}
	
	public void save(FileConfiguration fileConfiguration) {
		save(fileConfigurationFileMap.get(fileConfiguration), fileConfiguration);
	}
	
	
	private void save(File file, FileConfiguration fileConfiguration) {
		try {
			fileConfiguration.save(file);
		} catch (IOException e) {
			System.out.println("An internal error occured while using TPermissions, and saving files. StackTrace:");
			e.printStackTrace();
		}
		
	}
}
