package me.ChewyN.Data;

import me.ChewyN.Main;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class AbstractFile {

    protected static Main instance;
    protected static File file;
    protected static FileConfiguration config;

    public AbstractFile(Main _main, String fileName) {
        instance = _main;
        file = new File(instance.getDataFolder(), fileName);

        if(!file.exists()) {
            try {
                file.createNewFile();
            } catch(IOException e) {
                e.printStackTrace();
            }
        }

        config = YamlConfiguration.loadConfiguration(file);

    }

    public static void save() {
        try {
            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }





}
