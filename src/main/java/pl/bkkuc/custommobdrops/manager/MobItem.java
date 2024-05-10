package pl.bkkuc.custommobdrops.manager;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MobItem {

    String name;
    int chance;
    List<String> actions;

    public MobItem(String name, int chance, List<String> actions) {
        this.name = name;
        this.chance = chance;
        this.actions = actions;
    }
}
