package com.wxav.simpleclans.clan;

import cn.nukkit.utils.MainLogger;
import com.wxav.simpleclans.SimpleClans;
import lombok.Getter;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class ClanFactory {

    @Getter
    private final static ClanFactory instance = new ClanFactory();

    private final Map<String, Clan> clanMap = new HashMap<>();

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

            Yaml yaml = new Yaml(new Constructor(Clan.class));

            Clan clan = yaml.load(inputStream);

            System.out.println(clan);

            this.clanMap.put(clan.getName().toLowerCase(), clan);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public Clan getClanName(String name) {
        return this.clanMap.get(name.toLowerCase());
    }

    public boolean clanExists(String name) {
        return (new File(SimpleClans.getInstance().getDataFolder(), "clans/" + name + ".yml")).exists();
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public void createClan(Clan clanConfiguration) {
        File file = new File(SimpleClans.getInstance().getDataFolder(), "clans/" + clanConfiguration.getName() + ".yml");

        File parent = file.getParentFile();

        if (parent != null) {
            parent.mkdirs();
        }

        Yaml yaml = new Yaml();

        try (FileWriter fileWriter = new FileWriter(file)) {
            fileWriter.write(yaml.dumpAsMap(clanConfiguration));
        } catch (IOException e) {
            MainLogger.getLogger().error("Unable to save Config " + file, e);
        }
    }
}