package pl.bkkuc.custommobdrops.commands;

import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import pl.bkkuc.custommobdrops.Plugin;
import pl.bkkuc.custommobdrops.manager.Mob;
import pl.bkkuc.custommobdrops.manager.MobItem;
import pl.bkkuc.custommobdrops.utilities.FileUtility;
import pl.bkkuc.purutils.ColorUtility;
import pl.bkkuc.purutils.commands.AbstractCommand;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MainCommand extends AbstractCommand {


    public MainCommand(JavaPlugin javaPlugin, String commandName) {
        super(javaPlugin, commandName);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(!sender.isOp()) {
            sender.sendMessage(ColorUtility.colorize("&cYou don't have permission."));
            return;
        }

        if(args.length == 0){
            sender.sendMessage(ColorUtility.colorize("&6[!] &fUsage&8: &6/mobdrops add [entitytype] [chance]"));
            return;
        }

        if(args[0].equalsIgnoreCase("add")) {
            if(!(sender instanceof Player)) return;
            if(args.length < 3){
                sender.sendMessage(ColorUtility.colorize("&6[!] &fUsage&8: &6/mobdrops add [entitytype] [chance]"));
                return;
            }

            EntityType entityType;
            try {
                entityType = EntityType.valueOf(args[1].toUpperCase());
            } catch (IllegalArgumentException e){
                sender.sendMessage(ColorUtility.colorize("&cEntity type &4'" + args[1].toUpperCase() + "' &cis not found."));
                return;
            }

            int chance;

            try {
                chance = Integer.parseInt(args[2]);
            } catch (NumberFormatException e){
                sender.sendMessage(ColorUtility.colorize("&cChance must be a number!"));
                return;
            }

            ItemStack item = ((Player)sender).getInventory().getItemInMainHand();

            if(item == null || item.getType() == Material.AIR){
                sender.sendMessage(ColorUtility.colorize("&cHold item in your main hand!"));
                return;
            }

            String uuid = UUID.randomUUID().toString();
            Plugin.getInstance().getItems().set("saved-items." + uuid, item);
            FileUtility.save(Plugin.getInstance().getItems(), "items.yml");

            Plugin.getInstance().getMobs().set(entityType.name() + ".items." + uuid + ".chance", chance);
            Plugin.getInstance().getMobs().set(entityType.name() + ".items." + uuid + ".actions", new ArrayList<>());
            FileUtility.save(Plugin.getInstance().getMobs(), "mobs.yml");
            sender.sendMessage(ColorUtility.colorize("&aSuccessfully!"));

            Mob mob = Plugin.getInstance().getMobManager().getMobByEntityType(entityType);
            if(mob != null){
                MobItem mobItem = new MobItem(uuid, chance, new ArrayList<>());
                mob.getMobItems().add(mobItem);
            }
            else {
                new Mob(entityType);
            }

            return;
        }
    }

    @Override
    public List<String> tab(CommandSender sender, String[] args) {
        if(!sender.isOp()) return Collections.emptyList();

        if(args.length == 1) {
            return Stream.of("add").collect(Collectors.toList());
        }
        else if(args.length == 2) {
            return Arrays.stream(EntityType.values())
                    .filter(EntityType::isSpawnable)
                    .map(EntityType::name)
                    .collect(Collectors.toList());
        }

        return Collections.emptyList();
    }
}
