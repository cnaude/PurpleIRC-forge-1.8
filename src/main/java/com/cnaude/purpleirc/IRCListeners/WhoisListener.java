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
package com.cnaude.purpleirc.IRCListeners;

import com.cnaude.purpleirc.PurpleBot;
import com.cnaude.purpleirc.PurpleIRC;
import java.text.SimpleDateFormat;
import java.util.List;

import com.cnaude.purpleirc.CommandSender;
import net.minecraft.util.EnumChatFormatting;
import org.pircbotx.Channel;
import org.pircbotx.User;
import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.WhoisEvent;

/**
 *
 * @author cnaude
 */
public class WhoisListener extends ListenerAdapter {

    PurpleIRC plugin;
    PurpleBot ircBot;

    /**
     *
     * @param plugin
     * @param ircBot
     */
    public WhoisListener(PurpleIRC plugin, PurpleBot ircBot) {
        this.plugin = plugin;
        this.ircBot = ircBot;
    }

    /**
     *
     * @param event
     */
    @Override
    public void onWhois(WhoisEvent event) {
        if (ircBot.whoisSenders.isEmpty()) {
            return;
        }
        CommandSender sender = ircBot.whoisSenders.remove(0);

        sender.sendMessage(EnumChatFormatting.DARK_PURPLE + "----[ " + EnumChatFormatting.WHITE + "Whois" + EnumChatFormatting.DARK_PURPLE + " ]----");
        sender.sendMessage(EnumChatFormatting.DARK_PURPLE + "Nick: " + EnumChatFormatting.WHITE + event.getNick());
        sender.sendMessage(EnumChatFormatting.DARK_PURPLE + "Username: " + EnumChatFormatting.WHITE + event.getLogin() + "@" + event.getHostname());
        sender.sendMessage(EnumChatFormatting.DARK_PURPLE + "Real name: " + EnumChatFormatting.WHITE + event.getRealname());
        sender.sendMessage(EnumChatFormatting.DARK_PURPLE + "Server: " + EnumChatFormatting.WHITE + event.getServer());
        User user = null;
        for (Channel channel : ircBot.getBot().getUserBot().getChannels()) {
            for (User u : channel.getUsers()) {
                if (u.getNick().equalsIgnoreCase(event.getNick())) {
                    user = u;
                    break;
                }
            }
        }
        if (user != null) {
            if (user.isAway()) {
                sender.sendMessage(EnumChatFormatting.DARK_PURPLE + "Away: " + EnumChatFormatting.WHITE + user.getAwayMessage());
            }
        }
        if (!event.getChannels().isEmpty()) {
            StringBuilder sb = new StringBuilder();
            for (Object channel : (List<String>) event.getChannels()) {
                sb.append(" ");
                sb.append(channel);
            }
            sender.sendMessage(EnumChatFormatting.DARK_PURPLE + "Currently on:" + EnumChatFormatting.WHITE + sb.toString());
        }
        sender.sendMessage(EnumChatFormatting.DARK_PURPLE + "Idle: " + EnumChatFormatting.WHITE + secondsToTime(event.getIdleSeconds()));
        sender.sendMessage(EnumChatFormatting.DARK_PURPLE + "Online since: " + EnumChatFormatting.WHITE + secondsToDate(event.getSignOnTime()));
        sender.sendMessage(EnumChatFormatting.DARK_PURPLE + "----[ " + EnumChatFormatting.WHITE + "End Whois" + EnumChatFormatting.DARK_PURPLE + " ]----");
    }

    private String secondsToDate(long sec) {
        return new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(new java.util.Date(sec * 1000));
    }

    private String secondsToTime(long sec) {
        int idleDays = (int) (sec / 86400L);
        int idleHours = (int) (sec / 3600L % 24L);
        int idleMinutes = (int) (sec / 60L % 60L);
        int idleSeconds = (int) (sec % 60L);
        String msg = "";
        if (idleDays > 0) {
            msg = idleDays + " day";
            if (idleDays > 1) {
                msg = msg + "s";
            }
            msg = msg + " ";
        }
        if (idleHours > 0) {
            msg = idleHours + " hour";
            if (idleHours > 1) {
                msg = msg + "s";
            }
            msg = msg + " ";
        }
        if (idleMinutes > 0) {
            msg = msg + idleMinutes + " minute";
            if (idleMinutes > 1) {
                msg = msg + "s";
            }
            msg = msg + " ";
        }
        if (idleSeconds > 0) {
            msg = msg + idleSeconds + " second";
            if (idleSeconds > 1) {
                msg = msg + "s";
            }
            msg = msg + " ";
        }
        return msg;
    }
}
