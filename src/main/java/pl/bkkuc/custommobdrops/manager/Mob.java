package pl.bkkuc.custommobdrops.manager;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.EntityType;
import pl.bkkuc.custommobdrops.Plugin;

import java.util.ArrayList;
import java.util.List;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class Mob {

    EntityType entityType;
    List<String> disabledWorlds;

    List<MobItem> mobItems;

    public Mob(EntityType entityType) {
        this.entityType = entityType;
        this.disabledWorlds = Plugin.getInstance().getMobs().getStringList(entityType.name() + ".disabled-worlds");
        this.mobItems = new ArrayList<>();

        ConfigurationSection section = Plugin.getInstance().getMobs().getConfigurationSection(entityType.name() + ".items");
        if(section != null){
            for(String name: section.getKeys(false)) {
                MobItem mobItem = new MobItem(name, section.getInt(name + ".chance", 100), section.getStringList(name + ".actions"));
                mobItems.add(mobItem);
            }
        }

        Plugin.getInstance().getMobManager().getMobs().add(this);
        Plugin.getInstance().getLogger().info("Loaded mob type '" + entityType.name() + "' with " + mobItems.size() + " custom drops.");
    }
}
