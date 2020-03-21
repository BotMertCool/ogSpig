package org.bukkit.event.player;

import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;

public class PlayerPearlRefundEvent extends PlayerEvent {
    private static final HandlerList handlers = new HandlerList();

    public PlayerPearlRefundEvent(final Player player) {
        super(player);
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }
}