package marumasa.track;

import marumasa.track.config.Config;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.world.EntitiesLoadEvent;

public class events implements Listener {

    private final Config cfg;
    private final minecraft mc;

    public events(Config config, minecraft minecraft) {
        cfg = config;
        mc = minecraft;
    }

    @EventHandler
    private void onEntitySpawn(EntitySpawnEvent event) {
        if (event.getEntity() instanceof LivingEntity livingEntity) {
            if (livingEntity.getType() == EntityType.HORSE) {
                new test().runTaskTimer(mc, 1, 1);
            }
        }
    }

    @EventHandler
    private void onEntityLoad(EntitiesLoadEvent event) {

        for (Entity entity : event.getEntities())
            if (entity instanceof LivingEntity livingEntity) {
                if (livingEntity.getType() == EntityType.HORSE) {
                    new test().runTaskTimer(mc, 1, 1);
                }
            }
    }
}
