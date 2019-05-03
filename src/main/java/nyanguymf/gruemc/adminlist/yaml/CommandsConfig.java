/**
 * Copyright (C) NyanGuyMF (Vasiliy Bely)
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 * Proprietary and confidential.
 *
 * Written by NyanGuyMF nyanguymf@gmail.com, 2019.
 */
package nyanguymf.gruemc.adminlist.yaml;

import static java.util.Arrays.asList;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

import de.exlll.configlib.annotation.ElementType;
import de.exlll.configlib.configs.yaml.BukkitYamlConfiguration;
import nyanguymf.gruemc.adminlist.AdminListPlugin;

/** @author NyanGuyMF - Vasiliy Bely */
public final class CommandsConfig extends BukkitYamlConfiguration {
    private static boolean ignoreCleanLaunch = false;
    @ElementType(CustomCommandConfig.class)
    private Map<String, CustomCommandConfig> commands = new HashMap<>();

    public CommandsConfig(final File commandsFile) {
        super(handleCommandsFile(commandsFile), AdminListPlugin.YAML_PROPERTIES);

        CustomCommandConfig customConfig = new CustomCommandConfig(
            "helper", asList("helper"), asList("Hello!")
        );

        commands.put(customConfig.getCommandName(), customConfig);

        if (CommandsConfig.ignoreCleanLaunch) {
            super.save();
        }

        super.loadAndSave();
    }

    /** @return the commands */
    public Map<String, CustomCommandConfig> getCommands() {
        return commands;
    }

    /** Sets commands */
    public void setCommands(final Map<String, CustomCommandConfig> commands) {
        this.commands = commands;
    }

    private static Path handleCommandsFile(final File file) {
        CommandsConfig.ignoreCleanLaunch = !file.exists();

        if (CommandsConfig.ignoreCleanLaunch) {
            try {
                file.createNewFile();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        return file.toPath();
    }
}
