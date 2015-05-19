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
package com.cnaude.purpleirc.GameListeners;

import com.cnaude.purpleirc.PurpleBot;
import com.cnaude.purpleirc.PurpleIRC;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 *
 * @author cnaude
 */
public class GamePlayerChatListener {

    private final PurpleIRC plugin;

    /**
     *
     * @param plugin
     */
    public GamePlayerChatListener(PurpleIRC plugin) {
        this.plugin = plugin;
    }

    @SubscribeEvent(receiveCanceled=true)
    public void onServerChat(ServerChatEvent event) {
        plugin.logDebug("[onServerChat]: " + event.username + ": " + event.message);
        if (event.isCanceled() && !plugin.ignoreChatCancel) {
            plugin.logDebug("Chat canceled! Accepting cancellation due to ignore-chat-cancel = " + plugin.ignoreChatCancel);
            return;
        }
        EntityPlayerMP player = (EntityPlayerMP) event.player;
        for (PurpleBot ircBot : plugin.ircBots.values()) {
            plugin.logDebug("[onServerChat]: Calling gameChat()...");
            ircBot.gameChat(player, event.message);
        }
    }

}
