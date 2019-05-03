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
public interface SubCommand {
    /** Runs default {@link SubCommandExecutor}. */
    boolean execute(CommandSender sender, String[] args);

    /** Gets sub command name. */
    String getName();

    /** Gets array of sub command aliases. */
    String[] getAliases();

    /** Gets permission for subCommand. May return <tt>null</tt>. */
    String getPermission();

    /** Gets usage of sub command. */
    String getUsage();

    /** Gets executor of sub command. */
    SubCommandExecutor getExecutor();

    /** Sets executor for sub command. */
    void setExecutor(final SubCommandExecutor executor);

    /** Registers sub command for manager. */
    SubCommand register(final SubCommandManager manager);
}
