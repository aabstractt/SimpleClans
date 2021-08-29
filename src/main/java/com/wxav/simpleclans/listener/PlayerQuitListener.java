package com.wxav.simpleclans.listener;

import cn.nukkit.event.EventHandler;
import cn.nukkit.event.EventPriority;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerQuitEvent;
import com.wxav.simpleclans.session.SessionFactory;

public class PlayerQuitListener implements Listener {

    @EventHandler (priority = EventPriority.NORMAL)
    public void onPlayerQuitListener(PlayerQuitEvent ev) {
        SessionFactory.getInstance().closeSession(ev.getPlayer());
    }
}