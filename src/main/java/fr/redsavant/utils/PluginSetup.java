package fr.redsavant.utils;

import fr.redsavant.utils.annotations.CommandInfo;
import lombok.experimental.UtilityClass;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.TabCompleter;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Classe utilitaire pour enregistrer automatiquement
 * les listeners et les commandes d'un plugin.
 */
@UtilityClass
public class PluginSetup {

    /**
     * Enregistre une liste d'objets (listeners, commandes, tab completers)
     * auprès du plugin fourni.
     *
     * @param plugin l'instance du plugin (généralement {@code this} dans onEnable)
     * @param objs   les objets à enregistrer (listeners, commandes, etc.)
     */
    public static void setup(JavaPlugin plugin, Object... objs) {
        for (Object obj : objs) {

            // Enregistre l'objet comme listener s'il implémente Listener
            if (obj instanceof Listener listener) {
                Bukkit.getPluginManager().registerEvents(listener, plugin);
            }

            // Gère l'enregistrement des commandes et tab completers
            if (obj instanceof CommandExecutor || obj instanceof TabCompleter) {

                // Récupère l'annotation @CommandInfo pour connaître le nom de la commande
                CommandInfo info = obj.getClass().getAnnotation(CommandInfo.class);
                if (info == null) {
                    // L'annotation est obligatoire pour savoir à quelle commande se rattacher
                    throw new IllegalStateException(
                            "Missing @CommandInfo on " + obj.getClass().getSimpleName()
                    );
                }

                // Récupère la commande déclarée dans le plugin.yml
                PluginCommand command = plugin.getCommand(info.value());
                if (command == null) {
                    // La commande doit être déclarée dans le plugin.yml pour être trouvée
                    throw new IllegalStateException(
                            "Command '" + info.value() + "' not found in plugin.yml"
                    );
                }

                // Associe l'executor si l'objet en implémente un
                if (obj instanceof CommandExecutor exec) {
                    command.setExecutor(exec);
                }

                // Associe le tab completer si l'objet en implémente un
                if (obj instanceof TabCompleter tc) {
                    command.setTabCompleter(tc);
                }
            }
        }
    }
}