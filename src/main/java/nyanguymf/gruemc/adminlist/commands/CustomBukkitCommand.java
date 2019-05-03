/**
 * Copyright (C) NyanGuyMF (Vasiliy Bely)
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 * Proprietary and confidential.
 *
 * Written by NyanGuyMF nyanguymf@gmail.com, 2019.
 */
package nyanguymf.gruemc.adminlist.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.command.defaults.BukkitCommand;

import nyanguymf.gruemc.adminlist.AdminListPlugin;
import nyanguymf.gruemc.adminlist.yaml.CustomCommandConfig;
import nyanguymf.gruemc.adminlist.yaml.MessagesManager;
import ru.tehkode.permissions.PermissionUser;
import ru.tehkode.permissions.bukkit.PermissionsEx;

/** @author NyanGuyMF - Vasiliy Bely */
public final class CustomBukkitCommand extends BukkitCommand {
    private CustomCommandConfig config;

    public CustomBukkitCommand(final CustomCommandConfig config) {
        super(config.getCommandName());
        this.config = config;
    }

    @Override public boolean execute(final CommandSender sender, final String commandLabel, final String[] args) {
        if (!hasPermission(sender, config)) {
            AdminListPlugin.getMessagesManager().getMessage("error.no-permission", commandLabel);
            return true;
        }

        for (String messagePart : config.getMessage()) {
            sender.sendMessage(MessagesManager.colored(messagePart));
        }

        return true;
    }

    @SuppressWarnings("deprecation")
    private static boolean hasPermission(final CommandSender sender, final CustomCommandConfig cfg) {
        if (sender instanceof ConsoleCommandSender)
            return true;

        boolean isPermissioned = false;

        PermissionUser user = PermissionsEx.getUser(sender.getName());

        if (user == null)
            return false;

        for (String userGroup : user.getGroupNames()) {
            if (cfg.getGroups().contains(userGroup)) {
                isPermissioned = true;
                break;
            }
        }

        return isPermissioned;
    }

    protected void setConfig(final CustomCommandConfig config) {
        this.config = config;
    }
}
