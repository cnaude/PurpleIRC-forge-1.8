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

import com.cnaude.purpleirc.CommandSender;
import com.cnaude.purpleirc.PurpleIRC;
import net.minecraft.util.EnumChatFormatting;

import org.pircbotx.Channel;
import org.pircbotx.User;

/**
 *
 * @author cnaude
 */
public class AddOp implements IRCCommandInterface {

    private final PurpleIRC plugin;
    private final String usage = "[bot] [channel] [user|mask]";
    private final String desc = "Add IRC users to IRC auto op list.";
    private final String name = "addop";
    private final String fullUsage = EnumChatFormatting.WHITE + "Usage: " + EnumChatFormatting.GOLD + "/irc " + name + " " + usage;

    /**
     *
     * @param plugin
     */
    public AddOp(PurpleIRC plugin) {
        this.plugin = plugin;
    }

    /**
     *
     * @param sender
     * @param args
     */
    @Override
    public void dispatch(CommandSender sender, String[] args) {
        if (args.length == 4) {
            String bot = plugin.botify(args[1]);
            String channelName = args[2];
            if (plugin.ircBots.containsKey(bot)) {
                // #channel, user
                String nick = args[3];
                String mask = nick;
                Channel channel = plugin.ircBots.get(bot).getChannel(channelName);
                if (channel != null) {
                    for (User user : channel.getUsers()) {
                        if (user.getNick().equalsIgnoreCase(nick)) {
                            mask = "*!*" + user.getLogin() + "@" + user.getHostmask();
                        }
                    }
                }
                if (mask.split("[\\!\\@]", 3).length == 3) {
                    plugin.ircBots.get(bot).addOp(channelName, mask, sender);
                    plugin.ircBots.get(bot).opIrcUsers(channelName);
                } else {
                    sender.sendMessage(EnumChatFormatting.RED + "Invalid user or mask: "
                            + EnumChatFormatting.WHITE + mask);
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
