package com.wxav.simpleclans.listener;

import cn.nukkit.Player;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.EventPriority;
import cn.nukkit.event.Listener;
import cn.nukkit.event.entity.EntityDamageByEntityEvent;
import com.wxav.simpleclans.session.Session;
import com.wxav.simpleclans.session.SessionFactory;

public class EntityDamageByEntityListener implements Listener {

    @EventHandler (priority = EventPriority.NORMAL)
    public void onEntityDamageByEntityEvent(EntityDamageByEntityEvent ev) {
        if (!(ev.getEntity() instanceof Player) && !(ev.getDamager() instanceof Player)) {
            return;
        }

        Session target = SessionFactory.getInstance().getPlayerSession((Player) ev.getEntity());

        if (target == null || target.getClan() == null) {
            return;
        }

        Session player = SessionFactory.getInstance().getPlayerSession((Player) ev.getDamager());

        if (player == null || player.getClan() == null) {
            return;
        }

        if (player.getClanName().equals(target.getClanName())) {
            ev.setCancelled();
        }
    }
}