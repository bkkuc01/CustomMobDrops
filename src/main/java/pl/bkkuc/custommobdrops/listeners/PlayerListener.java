package pl.bkkuc.custommobdrops.listeners;

import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import pl.bkkuc.custommobdrops.Plugin;
import pl.bkkuc.custommobdrops.manager.Mob;
import pl.bkkuc.custommobdrops.manager.MobItem;
import pl.bkkuc.custommobdrops.utilities.Utility;

import java.util.concurrent.ThreadLocalRandom;

public class PlayerListener implements Listener {

    @EventHandler
    private void onEntityDeath(EntityDeathEvent e){
        EntityType entityType = e.getEntityType();
        Mob mob = Plugin.getInstance().getMobManager().getMobByEntityType(entityType);
        if(mob == null) return;
        if(mob.getDisabledWorlds().contains(e.getEntity().getWorld().getName())) return;

        for(MobItem mobItem: mob.getMobItems()) {
            if(mobItem.getChance() >= ThreadLocalRandom.current().nextInt(100)) {
                e.getDrops().add(Plugin.getInstance().getMobManager().getItemStackByName(mobItem.getName()));
                Utility.doActions(mobItem.getActions(), e.getEntity().getLocation());
            }
        }
    }
}
