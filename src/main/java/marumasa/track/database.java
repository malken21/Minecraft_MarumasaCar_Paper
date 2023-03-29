package marumasa.track;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Interaction;
import org.bukkit.entity.LivingEntity;

import java.util.HashMap;
import java.util.Map;

public class database {
    public static Map<LivingEntity, test> vehicleList = new HashMap<>();
    public static Map<Interaction, Entity> seatList = new HashMap<>();
}
