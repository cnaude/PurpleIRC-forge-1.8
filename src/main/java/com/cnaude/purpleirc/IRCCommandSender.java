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

import net.minecraft.command.CommandResultStats;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.Entity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

/**
 *
 * @author Chris Naude We have to implement our own ICommandSender so that we can
 * receive output from the command dispatcher.
 */
public class IRCCommandSender implements ICommandSender {

    private final PurpleBot ircBot;
    private final String target;
    private final PurpleIRC plugin;
    private final boolean ctcpResponse;
    private final String name;

    private void addMessageToQueue(String message) {
        plugin.logDebug("addMessageToQueue: " + message);
        ircBot.messageQueue.add(new IRCMessage(target,
                plugin.colorConverter.gameColorsToIrc(message), ctcpResponse));
    }

    /**
     *
     * @param ircBot
     * @param target
     * @param plugin
     * @param ctcpResponse
     * @param name
     */
    public IRCCommandSender(PurpleBot ircBot, String target, PurpleIRC plugin, boolean ctcpResponse, String name) {
        super();
        this.target = target;
        this.ircBot = ircBot;
        this.plugin = plugin;
        this.ctcpResponse = ctcpResponse;
        this.name = name;
    }

    @Override
    public void addChatMessage(IChatComponent chatComponent) {
        addMessageToQueue(chatComponent.getUnformattedText());
    }

    @Override
    public boolean canCommandSenderUseCommand(int level, String var2) {
        return true;
    }

    @Override
    public World getEntityWorld() {
        return MinecraftServer.getServer().getEntityWorld();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public IChatComponent getDisplayName() {
        return new ChatComponentText(name);
    }

    @Override
    public BlockPos getPosition() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Vec3 getPositionVector() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Entity getCommandSenderEntity() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean sendCommandFeedback() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void func_174794_a(CommandResultStats.Type type, int i) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
