/**
 * Copyright (C) NyanGuyMF (Vasiliy Bely)
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 * Proprietary and confidential.
 *
 * Written by NyanGuyMF nyanguymf@gmail.com, 2019.
 */
package nyanguymf.gruemc.adminlist.yaml;

import static org.bukkit.ChatColor.translateAlternateColorCodes;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.plugin.java.JavaPlugin;

import de.exlll.configlib.configs.yaml.BukkitYamlConfiguration;
import nyanguymf.gruemc.adminlist.AdminListPlugin;

/** @author NyanGuyMF - Vasiliy Bely */
public final class MessagesManager extends BukkitYamlConfiguration {
    private static boolean ignoreCleanLaunch = false;
    private Map<String, String> messages = new HashMap<>();

    public MessagesManager(final JavaPlugin plugin) {
        super(getLocaleFile(plugin), AdminListPlugin.YAML_PROPERTIES);

        messages.put("error.no-permission", "&cУ Вас нет прав на команду &6{0}");
        messages.put("usage.adminlist", "&e/adminlist help");

        if (MessagesManager.ignoreCleanLaunch) {
            super.save();
        }

        super.loadAndSave();
    }

    public String getMessage(final String path, final String...args) {
        return getMessage(path, false, args);
    }

    public String getMessage(final String path, final boolean multiline, final String...args) {
        String message = messages.get(path);

        if (message == null)
            throw new NullPointerException("The key message path " + path +" doesn't exists.");

        if (multiline) {
            message = message.replace("\\n", "\n");
        }

        return colored(insertArguments(message, args));
    }

    /** @see org.bukkit.ChatColor#translateAlternateColorCodes(Chat, String) */
    public static String colored(final String msg) {
        return translateAlternateColorCodes('&', msg);
    }

    public static String insertArguments(String subject, final String...args) {
        if (args.length == 0)
            return subject;

        int c = 0;
        while (c < args.length) {
            subject = subject.replace("{" + c + "}", args[c++]);
        }

        return subject;
    }

    private static Path getLocaleFile(final JavaPlugin plugin) {
        if (!plugin.getDataFolder().exists()) {
            plugin.getDataFolder().mkdir();
        }

        File localeFile = new File(
            plugin.getDataFolder(),
            "messages_" + plugin.getConfig().getString("locale", "ru") + ".yml"
        );

        if (!localeFile.exists()) {
            MessagesManager.ignoreCleanLaunch = true;
            try {
                localeFile.createNewFile();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        return localeFile.toPath();
    }

    protected Map<String, String> getMessages() {
        return messages;
    }

    protected void setMessages(final Map<String, String> messages) {
        this.messages = messages;
    }
}
