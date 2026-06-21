package fr.redsavant.utils;

import lombok.experimental.UtilityClass;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;

/**
 * Classe utilitaire pour colorizer les text en minimessages (components)
 */

@UtilityClass
public class Colorize {
    private static final MiniMessage minimessage = MiniMessage.miniMessage();
    public static Component colorize(String message) {
        return minimessage.deserialize(message);
    }
}