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
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 *
 * @author cnaude
 */
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.event.entity.player.AchievementEvent;

public class GamePlayerPlayerAchievementAwardedListener {

    private final PurpleIRC plugin;

    /**
     *
     * @param plugin
     */
    public GamePlayerPlayerAchievementAwardedListener(PurpleIRC plugin) {
        this.plugin = plugin;
    }

    @SubscribeEvent
    public void onAchievement(AchievementEvent event) {
        for (PurpleBot ircBot : plugin.ircBots.values()) {
            ircBot.gameAchievement((EntityPlayerMP)event.entityPlayer, event.achievement);
        }

    }

}
