package marumasa.track.config;

import marumasa.track.minecraft;
import org.bukkit.configuration.file.FileConfiguration;

public class Config {

    public Config(final minecraft plugin) {
        plugin.saveDefaultConfig();
        FileConfiguration config = plugin.getConfig();
    }
}
