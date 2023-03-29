package marumasa.track;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Transformation;
import org.bukkit.util.Vector;

import java.util.HashMap;
import java.util.Map;

public class test extends BukkitRunnable {

    private final ItemDisplay body;

    private final Interaction interaction_0;
    private final Horse seat_0;

    private final Interaction interaction_1;
    private final ItemDisplay seat_1;

    private final Interaction interaction_2;
    private final ItemDisplay seat_2;

    public test(Horse horse) {
        horse.setAI(false);
        horse.setSilent(true);
        horse.setTamed(true);
        horse.getInventory().setSaddle(new ItemStack(Material.SADDLE));
        horse.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, -1, 0, true, false));

        final World world = horse.getWorld();
        final Location location = horse.getLocation();

        body = (ItemDisplay) world.spawnEntity(location, EntityType.ITEM_DISPLAY);
        horse.addPassenger(body);
        final ItemStack bodyItem = new ItemStack(Material.DISC_FRAGMENT_5);
        body.setItemStack(bodyItem);
        ItemMeta bodyMeta = bodyItem.getItemMeta();
        bodyMeta.setCustomModelData(1);


        Transformation transform = body.getTransformation();
        body.setTransformation(new Transformation(
                transform.getTranslation().add(0, 0, -1),
                transform.getLeftRotation(),
                transform.getScale(),
                transform.getRightRotation()));

        interaction_0 = (Interaction) world.spawnEntity(location, EntityType.INTERACTION);
        seat_0 = horse;
        database.seatList.put(interaction_0, seat_0);

        interaction_1 = (Interaction) world.spawnEntity(location, EntityType.INTERACTION);
        seat_1 = (ItemDisplay) world.spawnEntity(location, EntityType.ITEM_DISPLAY);
        database.seatList.put(interaction_1, seat_1);

        interaction_2 = (Interaction) world.spawnEntity(location, EntityType.INTERACTION);
        seat_2 = (ItemDisplay) world.spawnEntity(location, EntityType.ITEM_DISPLAY);
        database.seatList.put(interaction_2, seat_2);

    }

    @Override
    public void run() {
        final Location location = seat_0.getLocation();

        interaction_0.teleport(seat_0);

        body.setRotation(location.getYaw(), 0);
        seat_0.setAge(-1024);
        /*
        Vector test = body.getLocation().getDirection();
        test.rotateAroundY(location.getYaw());
        body.setItemDisplayTransform(test);*/

        Map<Entity, Entity> passengerData;

        passengerData = removePassenger(seat_1);
        seatTeleport_1(location.clone());
        addPassenger(passengerData);

        passengerData = removePassenger(seat_2);
        seatTeleport_2(location.clone());
        addPassenger(passengerData);

        //vector.rotateAroundY(45);
    }

    private static Map<Entity, Entity> removePassenger(Entity entity) {
        final Map<Entity, Entity> passengerData = new HashMap<>();
        for (Entity ride : entity.getPassengers()) {
            entity.removePassenger(ride);
            passengerData.put(entity, ride);
            passengerData.putAll(removePassenger(ride));
        }
        return passengerData;
    }

    private static void addPassenger(Map<Entity, Entity> passengerData) {
        for (Entity key : passengerData.keySet()) {
            key.addPassenger(passengerData.get(key));
            passengerData.remove(key);
        }
    }

    private void seatTeleport_1(Location location) {
        location.setPitch(0);
        final Vector vector = location.getDirection();

        vector.multiply(-2);
        vector.setY(1);
        location.add(vector);

        interaction_1.teleport(location);
        seat_1.teleport(location);
    }

    private void seatTeleport_2(Location location) {
        location.setPitch(0);
        final Vector vector = location.getDirection();

        vector.multiply(-3);
        vector.setY(1);
        location.add(vector);

        interaction_2.teleport(location);
        seat_2.teleport(location);
    }

    public void remove() {
        database.seatList.remove(interaction_0);
        interaction_0.remove();

        database.seatList.remove(interaction_1);
        interaction_1.remove();
        seat_1.remove();

        database.seatList.remove(interaction_2);
        interaction_2.remove();
        seat_2.remove();
    }
}
