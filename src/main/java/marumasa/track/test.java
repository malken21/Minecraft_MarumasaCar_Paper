package marumasa.track;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Transformation;
import org.bukkit.util.Vector;
import org.joml.Quaternionf;

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

    private final Interaction interaction_3;
    private final ItemDisplay seat_3;

    public test(Horse horse) {
        horse.setAI(false);
        horse.setSilent(true);
        horse.setTamed(true);
        horse.getInventory().setSaddle(new ItemStack(Material.SADDLE));
        horse.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, -1, 0, true, false));
        final AttributeInstance maxHealth = horse.getAttribute(Attribute.GENERIC_MAX_HEALTH);
        if (maxHealth != null) maxHealth.setBaseValue(20);
        horse.setHealth(20);
        horse.setRemoveWhenFarAway(true);

        final World world = horse.getWorld();
        final Location location = horse.getLocation();

        body = (ItemDisplay) world.spawnEntity(location, EntityType.ITEM_DISPLAY);
        final ItemStack bodyItem = new ItemStack(Material.DISC_FRAGMENT_5);
        ItemMeta bodyMeta = bodyItem.getItemMeta();
        bodyMeta.setCustomModelData(1);
        bodyItem.setItemMeta(bodyMeta);
        body.setItemStack(bodyItem);

        body.setBrightness(new Display.Brightness(15, 15));


                Transformation transform = body.getTransformation();
        Quaternionf quaternionf = transform.getLeftRotation();

        body.setTransformation(new Transformation(
                transform.getTranslation().add(-0.1f, 1.1f, -1),
                quaternionf,
                transform.getScale().add(1.5f, 2.1f, 2.1f),
                transform.getRightRotation()
        ));

        interaction_0 = (Interaction) world.spawnEntity(location, EntityType.INTERACTION);
        seat_0 = horse;
        database.seatList.put(interaction_0, seat_0);

        interaction_1 = (Interaction) world.spawnEntity(location, EntityType.INTERACTION);
        seat_1 = (ItemDisplay) world.spawnEntity(location, EntityType.ITEM_DISPLAY);
        database.seatList.put(interaction_1, seat_1);

        interaction_2 = (Interaction) world.spawnEntity(location, EntityType.INTERACTION);
        seat_2 = (ItemDisplay) world.spawnEntity(location, EntityType.ITEM_DISPLAY);
        database.seatList.put(interaction_2, seat_2);

        interaction_3 = (Interaction) world.spawnEntity(location, EntityType.INTERACTION);
        seat_3 = (ItemDisplay) world.spawnEntity(location, EntityType.ITEM_DISPLAY);
        database.seatList.put(interaction_3, seat_3);

    }

    @Override
    public void run() {
        Location location = seat_0.getLocation();

        seatTeleport_0(location.clone());

        bodyTeleport(location.clone());

        seat_0.setAge(-1024);

        Map<Entity, Entity> passengerData;

        passengerData = removePassenger(seat_1);
        seatTeleport_1(location.clone());
        addPassenger(passengerData);

        passengerData = removePassenger(seat_2);
        seatTeleport_2(location.clone());
        addPassenger(passengerData);

        passengerData = removePassenger(seat_3);
        seatTeleport_3(location.clone());
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

    private void seatTeleport_0(Location location) {
        location.setPitch(0);

        //---------
        location.add(move(location.getDirection(), 0));
        //---------

        interaction_0.teleport(location);
        //seat_0.teleport(location);
    }

    private void bodyTeleport(Location location) {
        location.setPitch(0);
        location.setYaw(location.getYaw() - 45);

        //---------
        location.add(move(location.getDirection(), (float) -Math.pow(1.125f, 2)));
        //---------

        location.setYaw(location.getYaw() - 135);
        body.teleport(location);
    }

    private void seatTeleport_1(Location location) {
        location.setPitch(0);

        //---------
        location.add(move(location.getDirection(), -1.5f));
        //---------

        interaction_1.teleport(location);
        seat_1.teleport(location);
    }

    private void seatTeleport_2(Location location) {
        location.setPitch(0);
        location.setYaw(location.getYaw() - 90);

        //---------
        location.add(move(location.getDirection(), -1.5f));
        //---------

        interaction_2.teleport(location);
        seat_2.teleport(location);
    }

    private void seatTeleport_3(Location location) {
        location.setPitch(0);
        location.setYaw(location.getYaw() - 45);

        //---------
        location.add(move(location.getDirection(), (float) -Math.pow(1.5f, 2)));
        //---------

        interaction_3.teleport(location);
        seat_3.teleport(location);
    }

    private Vector move(Vector vector, float value) {
        vector.normalize();
        vector.setX(vector.getX() * value);
        vector.setY(0.5);
        vector.setZ(vector.getZ() * value);
        return vector;
    }

    public void remove() {

        body.remove();

        database.seatList.remove(interaction_0);
        interaction_0.remove();

        database.seatList.remove(interaction_1);
        interaction_1.remove();
        seat_1.remove();

        database.seatList.remove(interaction_2);
        interaction_2.remove();
        seat_2.remove();

        database.seatList.remove(interaction_3);
        interaction_3.remove();
        seat_3.remove();
    }
}
