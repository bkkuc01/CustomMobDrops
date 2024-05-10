package pl.bkkuc.custommobdrops.utilities;

import com.destroystokyo.paper.ParticleBuilder;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import pl.bkkuc.custommobdrops.Plugin;

import java.util.List;

public class Utility {

    public static void doActions(List<String> actions, Location location){
        if(actions == null || actions.isEmpty()) return;

        for(String action: actions) {
            String[] params = action.split(" ");
            switch (params[0].toLowerCase()) {
                case "[particle]": {
                    Particle particle;

                    try {
                        particle = Particle.valueOf(params[1].toUpperCase());
                    } catch (IllegalArgumentException e){
                        Plugin.getInstance().getLogger().warning("Particle '" + params[1].toUpperCase() + "' is not found.");
                        break;
                    }
                    int count = params.length >= 3 ? Integer.parseInt(params[2]) : 1;
                    float extra = params.length >= 4 ? Float.parseFloat(params[3]) : 1.0f;

                    ParticleBuilder particleBuilder = new ParticleBuilder(particle).location(location).count(count).extra(extra);
                    particleBuilder.spawn();
                    break;
                }
                case "[sound]": {
                    Sound sound;

                    try {
                        sound = Sound.valueOf(params[1].toUpperCase());
                    } catch (IllegalArgumentException e){
                        Plugin.getInstance().getLogger().warning("Sound '" + params[1].toUpperCase() + "' is not found.");
                        break;
                    }

                    float volume = params.length >= 3 ? Float.parseFloat(params[2]) : 1.0f;
                    float pitch = params.length >= 4 ? Float.parseFloat(params[3]) : 1.0f;

                    location.getWorld().playSound(location, sound, volume, pitch);
                    break;
                }
            }
        }
    }
}
