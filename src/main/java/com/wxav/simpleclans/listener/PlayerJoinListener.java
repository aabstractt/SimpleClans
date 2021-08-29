package com.wxav.simpleclans.listener;

import cn.nukkit.event.EventHandler;
import cn.nukkit.event.EventPriority;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerJoinEvent;
import com.wxav.simpleclans.clan.ClanFactory;
import com.wxav.simpleclans.session.Session;
import com.wxav.simpleclans.session.SessionFactory;

public class PlayerJoinListener implements Listener {

    @EventHandler (priority = EventPriority.NORMAL)
    public void onPlayerJoinEvent(PlayerJoinEvent ev) {
        Session session = SessionFactory.getInstance().createSession(ev.getPlayer());

        if (session.getClanName() == null) {
            return;
        }

        ClanFactory.getInstance().loadClan(session.getClanName());
    }
}