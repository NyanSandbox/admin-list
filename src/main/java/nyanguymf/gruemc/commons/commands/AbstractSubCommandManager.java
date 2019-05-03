/**
 * This file is the part of King Of The Ladder plug-in.
 *
 * Copyright (c) 2019 Vasily
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package nyanguymf.gruemc.commons.commands;

import static nyanguymf.gruemc.adminlist.AdminListPlugin.getMessagesManager;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

/** @author NyanGuyMF - Vasiliy Bely */
public abstract class AbstractSubCommandManager implements SubCommandManager {
    /**
     * Dictionary of sub commands. Key is sub command
     * name; values are sub commands themselves.
     */
    private HashMap<String, SubCommand> subCommands;

    private String name;

    private String usage;

    public AbstractSubCommandManager(final String name, final String usage) {
        this.name = name;
        this.usage = usage;
        subCommands = new HashMap<>();
    }

    @Override public boolean onCommand(
        final CommandSender sender, final Command command,
        final String label, final String[] args
    ) {
        if (args.length == 0) {
            if (usage != null) {
                sender.sendMessage(usage);
            }
            return true;
        }

        String subCommandName = args[0].toLowerCase();
        String[] subCommandArgs = args.length == 1
                ? new String[0]
                : Arrays.copyOfRange(args, 1, args.length);

        boolean isFound = false;
        // try  to find command in dictionary
        if (subCommands.containsKey(subCommandName)) {
            SubCommand subCommand = subCommands.get(subCommandName);

            executeCommand(sender, subCommand, subCommandArgs);

            isFound = true;
        }

        if (isFound)
            return isFound;

        // try to find command in aliases.
        aliasesLoop:
        for (SubCommand subCommand : subCommands.values()) {
            for (String alias : subCommand.getAliases()) {
                if (alias.equals(subCommandName)) {
                    executeCommand(sender, subCommand, subCommandArgs);
                    break aliasesLoop;
                }
            }
        }

        if (!isFound) {
            sender.sendMessage(usage);
        }

        return true;
    }

    /**
     * Adds sub command to manager.
     * <p>
     * Returns sub command with the same name for given
     * sub command.
     *
     * @param   subCommand  Sub command instance;
     * @return Sub command with same name or <tt>null</tt>.
     */
    @Override public SubCommand addSubCommand(final SubCommand subCommand) {
        return subCommands.put(subCommand.getName(), subCommand);
    }

    @Override public Collection<SubCommand> getSubCommands() {
        return subCommands.values();
    }

    @Override public String getName() {
        return name;
    }

    @Override public void setUsage(final String usage) {
        this.usage = usage;
    }

    private void executeCommand(final CommandSender sender, final SubCommand cmd, final String[] args) {
        if (!sender.hasPermission(cmd.getPermission())) {
            sender.sendMessage(getMessagesManager().getMessage(
                "error.no-permission", cmd.getName()
            ));
        } else if (!cmd.execute(sender, args)) {
            sender.sendMessage(cmd.getUsage());
        }
    }
}
