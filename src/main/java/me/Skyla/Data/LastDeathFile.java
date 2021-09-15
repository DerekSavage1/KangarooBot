package me.Skyla.Data;

import me.ChewyN.Data.AbstractFile;
import me.ChewyN.Main;
import me.ChewyN.Minecraft.Listeners.Player.PlayerDeath;
import me.Skyla.Minecraft.Objects.DeathStatus;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

//TODO transition over to a player data file

/**
 * File to store last death location of players so players can still tp back if they log off or the server shuts down.
 *
 * @author Skyla
 */
public class LastDeathFile {

    protected static Main instance;
    protected static File file;
    protected static FileConfiguration death;

    public LastDeathFile(Main main) {
        instance = main;
        file = new File(instance.getDataFolder(), "deathData.yml");

        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        death = YamlConfiguration.loadConfiguration(file);
    }

    public static void save() {
        try {
            death.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void loadDeathInfoFromFile() {
        Set<String> UUIDset = death.getKeys(false);
        List<String> UUIDs = new ArrayList<>(UUIDset);
        List<Player> players = new ArrayList<>();
        List<DeathStatus> deathStatuses = new ArrayList<>();

        for (String UUID : UUIDs) {
            Player p = (Player) Bukkit.getOfflinePlayer(UUID);
            players.add(p);
        }
        for (String uuid : UUIDs) {
            Location l = (Location) death.get(uuid + ".location");
            Boolean tpStatus = (Boolean) death.get(uuid + ".tpStatus");
            deathStatuses.add(new DeathStatus(tpStatus, l));
        }
        for (int i = 0; i < players.size(); i++) {
            PlayerDeath.setDeathInfo(players.get(i), deathStatuses.get(i));
        }

        UUIDs.clear();
        UUIDset.clear();
        players.clear();
        deathStatuses.clear();
    }

    public static void saveDeath(String UUID, Location location, Boolean tpBack) {
        death.set(UUID + ".location", location);
        death.set(UUID + ".tpStatus", tpBack);
        save();
    }

    public static void saveTPStatus(String UUID, Boolean tpBack) {
        death.set(UUID + ".tpStatus", tpBack);
        save();
    }

}
