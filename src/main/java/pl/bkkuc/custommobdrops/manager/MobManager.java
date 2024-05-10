package pl.bkkuc.custommobdrops.manager;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;
import pl.bkkuc.custommobdrops.Plugin;
import pl.bkkuc.purutils.inventory.item.ItemBuilder;

import java.util.ArrayList;
import java.util.List;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MobManager {

    List<Mob> mobs;

    public MobManager() {
        this.mobs = new ArrayList<>();
    }

    public void init(){
        for(String entityTypeName: Plugin.getInstance().getMobs().getKeys(false)){
            entityTypeName = entityTypeName.toUpperCase();
            EntityType entityType;
            try {
                entityType = EntityType.valueOf(entityTypeName);
            } catch (IllegalArgumentException e){
                Plugin.getInstance().getLogger().warning("Entity type '" + entityTypeName + "' is not found.");
                continue;
            }
            new Mob(entityType);
        }
    }

    public ItemStack getItemStackByName(String name){
        ConfigurationSection section = Plugin.getInstance().getItems().getConfigurationSection("items");
        ConfigurationSection savedSections = Plugin.getInstance().getItems().getConfigurationSection("saved-items");

        if(section != null && section.get(name) != null) {
            return ItemBuilder.fromConfiguration(Plugin.getInstance().getItems());
        }
        else if(savedSections != null && savedSections.get(name) != null){
            return savedSections.getItemStack(name);
        }
        return null;
    }

    public @Nullable Mob getMobByEntityType(EntityType entityType){
        return mobs.stream().filter(mob -> mob.getEntityType() == entityType).findFirst().orElse(null);
    }
}
