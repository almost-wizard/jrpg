package ru.rsreu.gorobchenko.project.entity.item;

import ru.rsreu.gorobchenko.project.entity.item.potion.list.*;
import ru.rsreu.gorobchenko.project.entity.item.weapon.list.DefaultSword;

import java.util.List;

public enum ItemType {
    WEAPON {
        @Override
        public List<Item> getAllTradable() {
            return List.of(new DefaultSword());
        }
    }, POTION {
        @Override
        public List<Item> getAllTradable() {
            return List.of(
                    new MariborForest(),
                    new Owl(),
                    new RaffardTheWhite(),
                    new Smallow(),
                    new Thunder(),
                    new WhiteHoney()
            );
        }
    }, MUTAGEN {
        @Override
        public List<Item> getAllTradable() {
            return List.of();
        }
    }, REMNANT {
        @Override
        public List<Item> getAllTradable() {
            return List.of();
        }
    }, COIN {
        @Override
        public List<Item> getAllTradable() {
            return List.of();
        }
    };

    public abstract List<Item> getAllTradable();
}
