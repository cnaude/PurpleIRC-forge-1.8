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
package com.cnaude.purpleirc;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 *
 * @author Chris Naude Poll the command queue and dispatch to Bukkit
 */
public class IRCMessageQueueWatcher {

    private final PurpleIRC plugin;
    private final PurpleBot ircBot;
    private final Timer timer;
    private final BlockingQueue<IRCMessage> queue = new LinkedBlockingQueue<>();

    /**
     *
     * @param plugin
     * @param ircBot
     */
    public IRCMessageQueueWatcher(final PurpleBot ircBot, final PurpleIRC plugin) {
        this.plugin = plugin;
        this.ircBot = ircBot;
        this.timer = new Timer();
        startWatcher();
    }

    private void startWatcher() {
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                queueAndSend();
            }

        }, 0, 5);
    }

    private void queueAndSend() {
        IRCMessage ircMessage = queue.poll();
        if (ircMessage != null) {
            plugin.logDebug("[" + queue.size() + "]: queueAndSend message detected");
            if (ircMessage.ctcpResponse) {
                ircBot.blockingCTCPMessage(ircMessage.target, ircMessage.message);
            } else {
                ircBot.blockingIRCMessage(ircMessage.target, ircMessage.message);
            }
        }
    }

    public void cancel() {
        timer.cancel();
    }

    public String clearQueue() {
        int size = queue.size();
        if (!queue.isEmpty()) {
            queue.clear();
        }
        return "Elements removed from message queue: " + size;
    }

    /**
     *
     * @param ircMessage
     */
    public void add(IRCMessage ircMessage) {
        queue.offer(ircMessage);
    }
}
