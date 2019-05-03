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

import org.bukkit.command.CommandSender;

/** @author NyanGuyMF - Vasiliy Bely */
public abstract class AbstractSubCommand implements SubCommand {
    private final String name;

    private final String[] aliases;

    private final String permission;

    private SubCommandExecutor executor;

    public AbstractSubCommand(final String name, final String[] aliases, final String permission) {
        this.name = name;
        this.aliases = aliases;
        this.permission = permission;
    }

    @Override public boolean execute(final CommandSender sender, final String[] args) {
        return executor.onCommand(sender, this, args);
    }

    @Override public String getName() {
        return name;
    }

    @Override public String[] getAliases() {
        return aliases;
    }

    @Override public String getPermission() {
        return permission;
    }

    @Override public SubCommandExecutor getExecutor() {
        return executor;
    }

    @Override public void setExecutor(final SubCommandExecutor executor) {
        this.executor = executor;
    }
}
