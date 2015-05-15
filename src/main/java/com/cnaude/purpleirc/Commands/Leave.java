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
import org.pircbotx.Channel;

/**
 *
 * @author cnaude
 */
public class Leave implements IRCCommandInterface {

    private final PurpleIRC plugin;
    private final String usage = "[bot] [channel]";
    private final String desc = "Leave IRC channel.";
    private final String name = "leave";
    private final String fullUsage = EnumChatFormatting.WHITE + "Usage: " + EnumChatFormatting.GOLD + "/irc " + name + " " + usage;

    /**
     *
     * @param plugin
     */
    public Leave(PurpleIRC plugin) {
        this.plugin = plugin;
    }

    /**
     *
     * @param sender
     * @param args
     */
    @Override
    public void dispatch(CommandSender sender, String[] args) {
        if (args.length >= 3) {
            String bot = plugin.botify(args[1]);
            String channelName = args[2];
            String reason = "";
            if (args.length >= 4) {
                for (int i = 3; i < args.length; i++) {
                    reason = reason + " " + args[i];
                }
            }
            if (plugin.ircBots.containsKey(bot)) {
                if (plugin.ircBots.get(bot).isConnected()) {
                    for (Channel channel : plugin.ircBots.get(bot).getChannels()) {
                        if (channel.getName().equalsIgnoreCase(channelName)) {
                            channel.send().part(reason);
                            sender.sendMessage(EnumChatFormatting.WHITE + "Leaving " + channelName + "...");
                            return;
                        }
                    }
                    sender.sendMessage(EnumChatFormatting.WHITE + "Channel " + channelName + " is not valid.");
                } else {
                    sender.sendMessage(EnumChatFormatting.RED + "Not connected.");
                }
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
