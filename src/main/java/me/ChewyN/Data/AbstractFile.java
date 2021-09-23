package me.ChewyN.Data;

import me.Main;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class AbstractFile {

    private final File file;
    private final FileConfiguration config;

    public AbstractFile(Main _main, String fileName) {
        file = new File(_main.getDataFolder(), fileName);

        if(!file.exists()) {
            try {
                file.createNewFile();
            } catch(IOException e) {
                e.printStackTrace();
            }
        }

        config = YamlConfiguration.loadConfiguration(file);

    }

    public File getFile() {
        return file;
    }

    public FileConfiguration getYamlConfig() {
        return config;
    }

    public static void save(AbstractFile file) {
        try {
            file.getYamlConfig().save(file.getFile());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
