/**
 * Copyright (C) NyanGuyMF (Vasiliy Bely)
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 * Proprietary and confidential.
 *
 * Written by NyanGuyMF nyanguymf@gmail.com, 2019.
 */
package nyanguymf.gruemc.adminlist;

import java.io.File;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.plugin.java.JavaPlugin;

import de.exlll.configlib.configs.yaml.BukkitYamlConfiguration.BukkitYamlProperties;
import nyanguymf.gruemc.adminlist.commands.AdminListCommand;
import nyanguymf.gruemc.adminlist.commands.CustomBukkitCommand;
import nyanguymf.gruemc.adminlist.yaml.CommandsConfig;
import nyanguymf.gruemc.adminlist.yaml.CustomCommandConfig;
import nyanguymf.gruemc.adminlist.yaml.MessagesManager;

/** @author NyanGuyMF - Vasiliy Bely */
public final class AdminListPlugin extends JavaPlugin {
    private static CommandsConfig commandsConfig;
    private static MessagesManager messagesManager;
    private static Map<String, CustomBukkitCommand> commands = new HashMap<>();

    public static final BukkitYamlProperties YAML_PROPERTIES = BukkitYamlProperties.builder()
            .setFormatter(fn -> {
                StringBuilder builder = new StringBuilder(fn.length());
                for (char c : fn.toCharArray()) {
                    if (Character.isLowerCase(c)) {
                        builder.append(c);
                    } else if (Character.isUpperCase(c)) {
                        c = Character.toLowerCase(c);
                        builder.append('-').append(c);
                    }
                }
                return builder.toString();
            })
            .addFilter(field -> !field.getName().startsWith("ignore"))
            .build();

    @Override public void onLoad() {
        AdminListPlugin.messagesManager = new MessagesManager(this);

        if (!new File(super.getDataFolder(), "config.yml").exists()) {
            super.saveDefaultConfig();
        }

        File commandsConfigFile = new File(super.getDataFolder(), "commands.yml");
        AdminListPlugin.commandsConfig = new CommandsConfig(commandsConfigFile);
    }

    @Override public void onEnable() {
        for (CustomCommandConfig cmdConfig : AdminListPlugin.commandsConfig.getCommands().values()) {
            CustomBukkitCommand customCmd = new CustomBukkitCommand(cmdConfig);
            addCustomCommand(customCmd);
            AdminListPlugin.commands.put(customCmd.getName(), customCmd);
        }

        new AdminListCommand(AdminListPlugin.commands).register(this);
    }

    /** Adds given command to Bukkit server. */
    public static void addCustomCommand(final BukkitCommand cmd) {
        // avoid NMS by using reflection
        /*
         * Теорема Эскобара:
         *      При безальтернативном выборе из двух
         *      противоположных сущностей обе будут
         *      являться исключительной хуйней.
         */
        try {
            Field bukkitCommandMap = Bukkit.getServer().getClass().getDeclaredField("commandMap");
            bukkitCommandMap.setAccessible(true);
            CommandMap commandMap = (CommandMap) bukkitCommandMap.get(Bukkit.getServer());

            if (!commandMap.register("adminlist", cmd)) {
                System.err.printf("Command %s already exists!\n", cmd.getName());
            }
        } catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException ex) {
            System.err.printf("Unable to add %s command: %s.\n", cmd.getName(), ex.getLocalizedMessage());
        }
    }

    /** @return the customCommandConfig */
    public static CommandsConfig getCommandsConfig() {
        return AdminListPlugin.commandsConfig;
    }

    /** @return the messagesManager */
    public static MessagesManager getMessagesManager() {
        return AdminListPlugin.messagesManager;
    }
}
