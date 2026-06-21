package fr.redsavant.utils;

import fr.redsavant.utils.annotations.CommandInfo;
import lombok.experimental.UtilityClass;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.TabCompleter;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

@UtilityClass
public class PluginSetup {

    public static void setup(JavaPlugin plugin, Object... objs) {
        for (Object obj : objs) {
            if (obj instanceof Listener listener) {
                Bukkit.getPluginManager().registerEvents(listener, plugin);
            }

            if (obj instanceof CommandExecutor || obj instanceof TabCompleter) {
                CommandInfo info = obj.getClass().getAnnotation(CommandInfo.class);
                if (info == null) {
                    throw new IllegalStateException(
                            "Missing @CommandInfo on " + obj.getClass().getSimpleName()
                    );
                }

                PluginCommand command = plugin.getCommand(info.value());
                if (command == null) {
                    throw new IllegalStateException(
                            "Command '" + info.value() + "' not found in plugin.yml"
                    );
                }

                if (obj instanceof CommandExecutor exec) {
                    command.setExecutor(exec);
                }
                if (obj instanceof TabCompleter tc) {
                    command.setTabCompleter(tc);
                }
            }
        }
    }
}