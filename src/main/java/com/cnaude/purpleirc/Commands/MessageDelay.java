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
public class MessageDelay implements IRCCommandInterface {

    private final PurpleIRC plugin;
    private final String usage = "[bot] [milliseconds]";
    private final String desc = "Change IRC message delay.";
    private final String name = "messagedelay";
    private final String fullUsage = EnumChatFormatting.WHITE + "Usage: " + EnumChatFormatting.GOLD + "/irc " + name + " " + usage;

    /**
     *
     * @param plugin
     */
    public MessageDelay(PurpleIRC plugin) {
        this.plugin = plugin;
    }

    /**
     *
     * @param sender
     * @param args
     */
    @Override
    public void dispatch(CommandSender sender, String[] args) {
        if (args.length == 3) {
            if (args[2].matches("\\d+")) {
                String bot = plugin.botify(args[1]);
                if (plugin.ircBots.containsKey(bot)) {
                    long delay = Long.parseLong(args[2]);
                    plugin.ircBots.get(bot).setIRCDelay(sender, delay);
                } else {
                    sender.sendMessage(plugin.invalidBotName.replace("%BOT%", bot));
                }
            } else {
                sender.sendMessage(fullUsage);
            }
        } else if (args.length == 2) {
            String bot = plugin.botify(args[1]);
            if (plugin.ircBots.containsKey(bot)) {
                sender.sendMessage(EnumChatFormatting.WHITE + "IRC message delay is currently "
                        + plugin.ircBots.get(bot).getMessageDelay() + " ms.");
            } else {
                sender.sendMessage(plugin.invalidBotName.replace("%BOT%", bot));
            }
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
