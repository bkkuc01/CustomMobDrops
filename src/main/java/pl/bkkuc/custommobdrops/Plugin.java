package pl.bkkuc.custommobdrops;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;
import pl.bkkuc.custommobdrops.commands.MainCommand;
import pl.bkkuc.custommobdrops.listeners.PlayerListener;
import pl.bkkuc.custommobdrops.manager.MobManager;
import pl.bkkuc.custommobdrops.utilities.FileUtility;

@Getter
public final class Plugin extends JavaPlugin {

    @Getter
    private static Plugin instance;

    private FileConfiguration items;
    private FileConfiguration mobs;

    private MobManager mobManager;

    @Override
    public void onEnable() {
        instance = this;

        items = FileUtility.get("items.yml");
        mobs = FileUtility.get("mobs.yml");

        mobManager = new MobManager();
        mobManager.init();

        Bukkit.getPluginManager().registerEvents(new PlayerListener(), this);

        new MainCommand(this, "mobdrops");
    }

    @Override
    public void onDisable() {
        HandlerList.unregisterAll(this);
    }
}
