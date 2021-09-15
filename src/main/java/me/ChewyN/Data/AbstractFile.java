package me.ChewyN.Data;

import me.ChewyN.Main;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class AbstractFile {

    private File file;
    private FileConfiguration config;

    public AbstractFile(Main _main, String fileName) {
        file = new File(_main.getDataFolder(), fileName);

        if(!file.exists()) {
            try {
                file.createNewFile();
                generateDefaultFile();
            } catch(IOException e) {
                e.printStackTrace();
            }
        }

        config = YamlConfiguration.loadConfiguration(file);

    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public FileConfiguration getConfig() {
        return config;
    }

    public void setGetConfig(FileConfiguration getConfig) {
        this.config = getConfig;
    }

    public static void save(ConfigFile file) {
        try {
            file.getConfig().save(file.getFile());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // TODO: MAKE THIS
    /**
     * Generates a new file with defaults if one does not exist
     */
    public static void generateDefaultFile() {

    }






}
