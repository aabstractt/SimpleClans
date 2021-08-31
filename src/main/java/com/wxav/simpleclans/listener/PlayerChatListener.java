package com.wxav.simpleclans.listener;

import cn.nukkit.event.EventHandler;
import cn.nukkit.event.EventPriority;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerChatEvent;
import com.wxav.simpleclans.SimpleClans;
import com.wxav.simpleclans.session.Session;
import com.wxav.simpleclans.session.SessionFactory;

public class PlayerChatListener implements Listener {

    @EventHandler (priority = EventPriority.NORMAL)
    public void onPlayerChatEvent(PlayerChatEvent ev) {
        Session session = SessionFactory.getInstance().getPlayerSession(ev.getPlayer());

        if (session == null) {
            return;
        }

        String prefix = SimpleClans.translateMessage(session.getClan() == null ? "NO_CLAN" : "WITH_CLAN", session.getClanName());
        String rankPrefix = session.getClan() != null ? SimpleClans.translateMessage("RANK_PREFIX", session.getRole().simpleName()) : "";

        ev.setFormat(ev.getFormat().replaceAll("%CLAN_PREFIX%", prefix).replaceAll("%CLAN_RANK%", rankPrefix));
    }
}