package com.wxav.simpleclans.clan;

import cn.nukkit.utils.MainLogger;
import com.wxav.simpleclans.SimpleClans;
import lombok.Getter;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ClanFactory {

    @Getter
    private final static ClanFactory instance = new ClanFactory();

    private final Map<String, Clan> clanMap = new HashMap<>();

    public Clan getClanName(String name) {
        return this.clanMap.get(name.toLowerCase());
    }

    public boolean clanExists(String name) {
        return (new File(SimpleClans.getInstance().getDataFolder(), "clans/" + name + ".yml")).exists();
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public void createClan(ClanConfiguration clanConfiguration) {
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