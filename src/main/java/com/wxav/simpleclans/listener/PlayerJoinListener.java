package com.wxav.simpleclans.listener;

import cn.nukkit.Player;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.EventPriority;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerJoinEvent;
import com.wxav.simpleclans.SimpleClans;
import com.wxav.simpleclans.clan.ClanFactory;
import com.wxav.simpleclans.session.Session;
import com.wxav.simpleclans.session.SessionFactory;
import ru.nukkit.multichat.MultiChat;

public class PlayerJoinListener implements Listener {

    @EventHandler (priority = EventPriority.MONITOR)
    public void onPlayerJoinEvent(PlayerJoinEvent ev) {
        Player player = ev.getPlayer();

        Session session = SessionFactory.getInstance().createSession(player);

        if (session.getClanName() != null) {
            ClanFactory.getInstance().loadClan(session.getClanName());
        }

        String prefix = SimpleClans.translateMessage(session.getClan() == null ? "NO_CLAN" : "WITH_CLAN", session.getClanName());
        String rankPrefix = session.getClan() != null ? SimpleClans.translateMessage("RANK_PREFIX", session.getRole().simpleName()) : "";

        if (MultiChat.getCfg().nametagEnabled) {
            player.setNameTag(player.getNameTag().replaceAll("%CLAN_PREFIX%", prefix).replaceAll("%CLAN_RANK%", rankPrefix));
        }

        player.setDisplayName(player.getDisplayName().replaceAll("%CLAN_PREFIX%", prefix).replaceAll("%CLAN_RANK%", rankPrefix));

        player.sendMessage("NameTag > " + player.getNameTag() + ", DisplayName > " + player.getDisplayName());
    }
}