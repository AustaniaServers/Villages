package com.domsplace.Villages.Bases;

import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class EventBase extends Event {
    private static final HandlerList handlers = new HandlerList();
    
    public EventBase() {
        
    }
    
    public void fireEvent() {Bukkit.getPluginManager().callEvent(this);}
    
    @Override
    public HandlerList getHandlers() {
        return handlers;
    }
 
    public static HandlerList getHandlerList() {
        return handlers;
    }
}
