package com.wxav.simpleclans.session;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.utils.Config;
import com.wxav.simpleclans.SimpleClans;
import com.wxav.simpleclans.clan.Role;
import lombok.Getter;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class SessionFactory {

    @Getter
    private final static SessionFactory instance = new SessionFactory();

    private final Map<String, Session> sessions = new HashMap<>();

    public Session createSession(Player player) {
        String name = player.getName().toLowerCase();

        Config config = new Config(new File(SimpleClans.getInstance().getDataFolder(), "players_clan.yml"));

        String roleName = config.getString(name + ".role", null);

        if (roleName.equals("null")) {
            roleName = null;
        }

        Session session = new Session(player.getName(), player.getUniqueId(), config.getString(name + ".clan", null), roleName != null ? Role.valueOf(roleName) : null);

        this.sessions.put(player.getName().toLowerCase(), session);

        return session;
    }

    public void closeSession(Player player) {
        this.sessions.remove(player.getName().toLowerCase());
    }

    public Session getSession(String name) {
        Player player = Server.getInstance().getPlayer(name);

        if (player == null) {
            return null;
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
        Config config = new Config(new File(SimpleClans.getInstance().getDataFolder(), "players_clan.yml"));

        config.set(session.getName().toLowerCase(), new HashMap<String, String>() {{
            put("clan", session.getClanName());
            put("role", session.getRole().name());
        }});

        config.save();
    }

    public void removeSession(String name) {
        Config config = new Config(new File(SimpleClans.getInstance().getDataFolder(), "players_clan.yml"));

        config.remove(name.toLowerCase());

        config.save();
    }
}