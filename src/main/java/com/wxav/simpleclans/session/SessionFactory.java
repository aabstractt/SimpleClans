package com.wxav.simpleclans.session;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.utils.Config;
import cn.nukkit.utils.PluginException;
import com.wxav.simpleclans.SimpleClans;
import lombok.Getter;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class SessionFactory {

    @Getter
    private final static SessionFactory instance = new SessionFactory();

    private final Map<String, Session> sessions = new HashMap<>();

    private final Config config = new Config(new File(SimpleClans.getInstance().getDataFolder(), "players_clan.yml"));

    public Session createSession(Player player) {
        Session session = new Session(player.getName(), player.getUniqueId(), this.config.getString(player.getName().toLowerCase(), null));

        this.sessions.put(player.getName().toLowerCase(), session);

        return session;
    }

    public void closeSession(Player player) {
        this.sessions.remove(player.getName().toLowerCase());
    }

    public Session getSession(String name) {
        Player player = Server.getInstance().getPlayer(name);

        if (player == null) {
            throw new PluginException("Player not found");
        }

        return this.getPlayerSession(player);
    }

    public Session getPlayerSession(Player player) {
        return getSessionExact(player.getName());
    }

    public Session getSessionExact(String name) {
        return this.sessions.get(name.toLowerCase());
    }

    public void saveSession(Session session) {
        if (session.getClanName() == null) {
            return;
        }

        this.config.set(session.getName(), session.getClanName());
        this.config.save();
    }
}