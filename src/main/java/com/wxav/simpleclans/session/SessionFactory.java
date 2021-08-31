package com.wxav.simpleclans.session;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.utils.MainLogger;
import com.wxav.simpleclans.SimpleClans;
import com.wxav.simpleclans.clan.Role;
import lombok.Getter;
import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class SessionFactory {

    @Getter
    private final static SessionFactory instance = new SessionFactory();

    private final Map<String, Session> sessions = new HashMap<>();

    @SuppressWarnings("unchecked")
    public Session createSession(Player player) {
        Map<String, Object> data = getConfigData();

        data = (Map<String, Object>) data.getOrDefault(player.getName(), null);

        String roleName = null;
        String clanName = null;

        if (data != null) {
            roleName = data.get("role").toString();

            clanName = data.get("clan").toString();
        }

        Session session = new Session(player.getName(), player.getUniqueId(), clanName, roleName != null ? Role.valueOf(roleName) : null);

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
        saveSession(session, false);
    }

    public void saveSession(Session session, boolean remove) {
        File file = new File(SimpleClans.getInstance().getDataFolder(), "players_clan.yml");

        Yaml yaml = new Yaml();

        try (FileWriter fileWriter = new FileWriter(file)) {
            Map<String, Object> data = getConfigData();

            if (remove) {
                data.remove(session.getName());
            } else {
                data.put(session.getName(), new HashMap<String, String>() {{
                    put("clan", session.getClanName());
                    put("role", session.getRole().name());
                }});
            }

            fileWriter.write(yaml.dumpAsMap(data));
        } catch (IOException e) {
            MainLogger.getLogger().error("Unable to save Config " + file, e);
        }
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    private Map<String, Object> getConfigData() {
        Map<String, Object> data = new HashMap<>();

        try {
            File file = new File(SimpleClans.getInstance().getDataFolder(), "players_clan.yml");

            if (!file.exists()) {
                file.createNewFile();
            }

            File parent = file.getParentFile();

            if (parent != null) {
                parent.mkdirs();
            }

            InputStream inputStream = new FileInputStream(file);

            Yaml yaml = new Yaml();

            data = yaml.load(inputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return data != null ? data : new HashMap<>();
    }
}