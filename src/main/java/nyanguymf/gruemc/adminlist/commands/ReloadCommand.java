/**
 * Copyright (C) NyanGuyMF (Vasiliy Bely)
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 * Proprietary and confidential.
 *
 * Written by NyanGuyMF nyanguymf@gmail.com, 2019.
 */
package nyanguymf.gruemc.adminlist.commands;

import static nyanguymf.gruemc.adminlist.AdminListPlugin.getMessagesManager;

import java.util.Map;

import org.bukkit.command.CommandSender;

import nyanguymf.gruemc.adminlist.AdminListPlugin;
import nyanguymf.gruemc.adminlist.yaml.CommandsConfig;
import nyanguymf.gruemc.adminlist.yaml.CustomCommandConfig;
import nyanguymf.gruemc.commons.commands.AbstractSubCommand;
import nyanguymf.gruemc.commons.commands.SubCommand;
import nyanguymf.gruemc.commons.commands.SubCommandExecutor;
import nyanguymf.gruemc.commons.commands.SubCommandManager;

/** @author NyanGuyMF - Vasiliy Bely */
final class ReloadCommand extends AbstractSubCommand implements SubCommandExecutor {
    private Map<String, CustomBukkitCommand> commands;

    public ReloadCommand(final Map<String, CustomBukkitCommand> commands) {
        super("reload", new String[] {"r", "re", "rel"}, "adminlist.reload");

        this.commands = commands;
        super.setExecutor(this);
    }

    @Override public String getUsage() {
        return getMessagesManager().getMessage("usage." + super.getName());
    }

    @Override public SubCommand register(final SubCommandManager manager) {
        manager.addSubCommand(this);
        return this;
    }

    @Override public boolean onCommand(
        final CommandSender sender, final AbstractSubCommand subCommand,
        final String[] args
    ) {
        CommandsConfig config = AdminListPlugin.getCommandsConfig();
        config.load();

        for (CustomCommandConfig cmdConfig : config.getCommands().values()) {
            CustomBukkitCommand customCmd = commands.get(cmdConfig.getCommandName());

            if (customCmd == null) {
                customCmd = new CustomBukkitCommand(cmdConfig);
                AdminListPlugin.addCustomCommand(customCmd);
                commands.put(customCmd.getName(), customCmd);
            } else {
                customCmd.setConfig(cmdConfig);
            }
        }

        return true;
    }
}
