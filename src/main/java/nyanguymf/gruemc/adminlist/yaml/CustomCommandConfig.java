/**
 * Copyright (C) NyanGuyMF (Vasiliy Bely)
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 * Proprietary and confidential.
 *
 * Written by NyanGuyMF nyanguymf@gmail.com, 2019.
 */
package nyanguymf.gruemc.adminlist.yaml;

import java.util.ArrayList;
import java.util.List;

import de.exlll.configlib.annotation.ConfigurationElement;

/** @author NyanGuyMF - Vasiliy Bely */
@ConfigurationElement
public final class CustomCommandConfig {
    private String commandName = "";

    private List<String> groups = new ArrayList<>();

    private List<String> message = new ArrayList<>();

    protected CustomCommandConfig() {}

    protected CustomCommandConfig(final String commandName, final List<String> groups, final List<String> message) {
        this.commandName = commandName;
        this.groups = groups;
        this.message = message;
    }

    /** @return the commandName */
    public String getCommandName() {
        return commandName;
    }

    /** Sets commandName */
    public void setCommandName(final String commandName) {
        this.commandName = commandName;
    }

    /** @return the groups */
    public List<String> getGroups() {
        return groups;
    }

    /** Sets groups */
    public void setGroups(final List<String> groups) {
        this.groups = groups;
    }

    /** @return the message */
    public List<String> getMessage() {
        return message;
    }

    /** Sets message */
    public void setMessage(final List<String> message) {
        this.message = message;
    }
}
