package marumasa.track;

import marumasa.track.config.Config;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.world.EntitiesLoadEvent;
import org.bukkit.event.world.EntitiesUnloadEvent;

import java.util.Set;

public class Events implements Listener {

    private final Config cfg;
    private final minecraft mc;

    public Events(Config config, minecraft minecraft) {
        cfg = config;
        mc = minecraft;
    }

    @EventHandler
    private void onEntitySpawn(EntitySpawnEvent event) {
        Load(event.getEntity());
    }

    @EventHandler
    private void onEntityDeath(EntityDeathEvent event) {
        Unload(event.getEntity());
    }

    @EventHandler
    private void onPlayerInteractEntity(PlayerInteractEntityEvent event) {
        Ride(event.getRightClicked(), event.getPlayer());
    }

    @EventHandler
    private void onEntityLoad(EntitiesLoadEvent event) {
        for (Entity entity : event.getEntities())
            Load(entity);
    }

    @EventHandler
    private void onEntitiesUnload(EntitiesUnloadEvent event) {
        for (Entity entity : event.getEntities())
            Unload(entity);
    }

    private void Ride(Entity entity, Player player) {
        if (entity instanceof Interaction interaction) {
            final Entity seat = database.seatList.get(interaction);
            if (seat == null) return;
            for (Entity rider : seat.getPassengers())
                if (rider.getType() != EntityType.ITEM_DISPLAY) return;
            seat.addPassenger(player);
        }
    }

    private void Load(Entity entity) {
        if (entity instanceof LivingEntity livingEntity) {

            final Set<String> tags = entity.getScoreboardTags();
            if (tags.contains("marumasa.test")) {

                if (livingEntity instanceof Horse horse) {
                    final test test = new test(horse);
                    database.vehicleList.put(horse, test);
                    test.runTaskTimer(mc, 0, 0);
                }
            }
        }
    }

    private void Unload(Entity entity) {
        if (entity instanceof LivingEntity livingEntity) {

            final test test = database.vehicleList.get(livingEntity);
            if (test == null) return;
            test.cancel();
            test.remove();
            database.vehicleList.remove(livingEntity);
        }
    }
}
