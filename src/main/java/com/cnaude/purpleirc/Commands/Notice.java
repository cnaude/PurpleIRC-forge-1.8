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

import com.cnaude.purpleirc.PurpleBot;
import com.cnaude.purpleirc.PurpleIRC;
import java.util.ArrayList;

import com.cnaude.purpleirc.CommandSender;
import net.minecraft.util.EnumChatFormatting;

/**
 *
 * @author cnaude
 */
public class Notice implements IRCCommandInterface {

    private final PurpleIRC plugin;
    private final String usage = "([bot]) [target] [message]";
    private final String desc = "Send notice message to the user or channel.";
    private final String name = "notice";
    private final String fullUsage = EnumChatFormatting.WHITE + "Usage: " + EnumChatFormatting.GOLD + "/irc " + name + " " + usage;

    /**
     *
     * @param plugin
     */
    public Notice(PurpleIRC plugin) {
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
            plugin.logDebug("Dispatching notice command...");
            int msgIdx = 2;
            String target;
            java.util.List<PurpleBot> myBots = new ArrayList<>();
            if (plugin.ircBots.containsKey(args[1])) {
                myBots.add(plugin.ircBots.get(args[1]));
                msgIdx = 3;
                target = args[2];
            } else {
                myBots.addAll(plugin.ircBots.values());
                target = args[1];
            }

            if (msgIdx == 3 && args.length <= 3) {
                sender.sendMessage(fullUsage);
                return;
            }

            for (PurpleBot ircBot : myBots) {
                String msg = "";
                for (int i = msgIdx; i < args.length; i++) {
                    msg = msg + " " + args[i];
                }
                ircBot.asyncNotice(target, msg.substring(1));
                sender.sendMessage("Sent notice message \"" + msg.substring(1) + "\" to \"" + target + "\"");
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
