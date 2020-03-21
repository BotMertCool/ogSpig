package org.bukkit.event;


public class TestEvent extends Event {
    private static final HandlerList handlers = new HandlerList();

    public TestEvent(boolean async) {
        super(async);
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }
}
