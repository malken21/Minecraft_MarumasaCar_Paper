package marumasa.track;

import marumasa.track.config.Config;
import org.bukkit.Server;
import org.bukkit.plugin.java.JavaPlugin;

public final class minecraft extends JavaPlugin {

    @Override
    public void onEnable() {
        Config config = new Config(this);
        final Server server = getServer();
        server.getPluginManager().registerEvents(new events(config, this), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
