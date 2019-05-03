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

import java.util.Arrays;
import java.util.Map;

import org.bukkit.plugin.java.JavaPlugin;

import nyanguymf.gruemc.commons.commands.AbstractSubCommandManager;
import nyanguymf.gruemc.commons.commands.SubCommandManager;

/** @author NyanGuyMF - Vasiliy Bely */
public final class AdminListCommand extends AbstractSubCommandManager {
    public AdminListCommand(final Map<String, CustomBukkitCommand> commands) {
        super("adminlist", getMessagesManager().getMessage("usage.adminlist"));
        super.addSubCommand(new ReloadCommand(commands).register(this));
    }

    @Override public SubCommandManager register(final JavaPlugin plugin) {
        plugin.getCommand(super.getName()).setExecutor(this);
        plugin.getCommand("adminlist").setAliases(Arrays.asList("al"));
        return this;
    }
}
