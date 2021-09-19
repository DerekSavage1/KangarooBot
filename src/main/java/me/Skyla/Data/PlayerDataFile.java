package me.Skyla.Data;

import me.Main;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;

public class PlayerDataFile {

    protected static Main instance;
    protected static File file;
    protected static FileConfiguration player;

    public PlayerDataFile(Player p, Main main) {
        String UUID = p.getUniqueId().toString();
        instance = main;
        file = new File(instance.getDataFolder() + "/playerData", UUID + ".yml");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        player = YamlConfiguration.loadConfiguration(file);
    }

    public static void save() {
        try {
            player.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void setPlayerName(Player p) {
        String UUID = p.getUniqueId().toString();
        file = setupFile(UUID);
        player = YamlConfiguration.loadConfiguration(file);
        player.set("name", p.getPlayerListName());
        save();
    }

    private static File setupFile(String UUID) {
        return new File(instance.getDataFolder() + "/playerData", UUID + ".yml");
    }

}
