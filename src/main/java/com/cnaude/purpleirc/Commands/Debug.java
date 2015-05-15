/*
 * Copyright (C) 2014 cnaude
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.cnaude.purpleirc.Commands;

import com.cnaude.purpleirc.PurpleIRC;

import com.cnaude.purpleirc.CommandSender;
import net.minecraft.util.EnumChatFormatting;

/**
 *
 * @author cnaude
 */
public class Debug implements IRCCommandInterface {

    private final PurpleIRC plugin;
    private final String usage = "([t|f])";
    private final String desc = "Enable or disable debug mode.";
    private final String name = "debug";
    private final String fullUsage = EnumChatFormatting.WHITE + "Usage: " + EnumChatFormatting.GOLD + "/irc " + name + " " + usage;

    /**
     *
     * @param plugin
     */
    public Debug(PurpleIRC plugin) {
        this.plugin = plugin;
    }

    /**
     *
     * @param sender
     * @param args
     */
    @Override
    public void dispatch(CommandSender sender, String[] args) {
        if (args.length == 1) {
            sender.sendMessage(EnumChatFormatting.DARK_PURPLE + "Debug mode is currently "
                    + EnumChatFormatting.WHITE + plugin.debugMode());
        } else if (args.length == 2) {
            if (args[1].startsWith("t")) {
                plugin.debugMode(true);
            } else if (args[1].startsWith("f")) {
                plugin.debugMode(false);
            } else {
                sender.sendMessage(usage);
            }
            sender.sendMessage(EnumChatFormatting.DARK_PURPLE + "Debug mode is now "
                    + EnumChatFormatting.WHITE + plugin.debugMode());
        } else {
            sender.sendMessage(fullUsage);
        }
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public String desc() {
        return desc;
    }

    @Override
    public String usage() {
        return usage;
    }
}
