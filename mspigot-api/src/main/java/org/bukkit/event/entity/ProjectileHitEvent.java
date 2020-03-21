package org.bukkit.event.entity;

import org.bukkit.entity.Projectile;
import org.bukkit.event.HandlerList;

/**
 * Called when a projectile hits an object
 */
public class ProjectileHitEvent extends EntityEvent {
    private static final HandlerList handlers = new HandlerList();

    public ProjectileHitEvent(final Projectile projectile) {
        super(projectile);
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    @Override
    public Projectile getEntity() {
        return (Projectile) entity;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

}
