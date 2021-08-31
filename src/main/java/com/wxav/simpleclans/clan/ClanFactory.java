package com.wxav.simpleclans.clan;

import cn.nukkit.utils.MainLogger;
import com.wxav.simpleclans.SimpleClans;
import lombok.Getter;
import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClanFactory {

    @Getter
    private final static ClanFactory instance = new ClanFactory();

    private final Map<String, Clan> clanMap = new HashMap<>();

    @SuppressWarnings("unchecked")
    public void loadClan(String name) {
        if (name == null || name.equals("null") || this.clanMap.containsKey(name.toLowerCase())) {
            return;
        }

        File file = new File(SimpleClans.getInstance().getDataFolder(), "clans/" + name + ".yml");

        if (!file.exists() || file.getParentFile() == null || !file.getParentFile().exists()) {
            return;
        }

        try {
            InputStream inputStream = new FileInputStream(file);

            Yaml yaml = new Yaml();

            Map<String, Object> data = yaml.load(inputStream);

            if (data == null) {
                return;
            }

            Clan clan = new Clan(
                    data.get("uniqueId").toString(),
                    data.get("name").toString(),
                    data.get("leader").toString(),
                    (List<String>) data.get("members"),
                    data.get("motd").toString(),
                    (Map<String, Object>) data.get("homePosition")
            );

            this.clanMap.put(clan.getName().toLowerCase(), clan);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public Clan getClanName(String name) {
        return name == null ? null : this.clanMap.get(name.toLowerCase());
    }

    public boolean clanExists(String name) {
        return (new File(SimpleClans.getInstance().getDataFolder(), "clans/" + name + ".yml")).exists();
    }

    public void saveClan(Clan clan) {
        saveClan(clan, false);
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public void saveClan(Clan clan, boolean add) {
        File file = new File(SimpleClans.getInstance().getDataFolder(), "clans/" + clan.getName() + ".yml");

        File parent = file.getParentFile();

        if (parent != null) {
            parent.mkdirs();
        }

        Yaml yaml = new Yaml();

        try (FileWriter fileWriter = new FileWriter(file)) {
            fileWriter.write(yaml.dumpAsMap(clan));

            if (add) {
                this.clanMap.put(clan.getName().toLowerCase(), clan);
            }
        } catch (IOException e) {
            MainLogger.getLogger().error("Unable to save Config " + file, e);
        }
    }

    public void removeClan(Clan clan) {
        File file = new File(SimpleClans.getInstance().getDataFolder(), "clans/" + clan.getName() + ".yml");

        if (!file.exists()) {
            return;
        }

        if (!file.delete()) {
            return;
        }

        this.clanMap.remove(clan.getName().toLowerCase());
    }
}