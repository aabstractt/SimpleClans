package com.wxav.simpleclans.clan;

import com.wxav.simpleclans.SimpleClans;
import lombok.Getter;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
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

    public void createClan(ClanConfiguration clanConfiguration) {
        DumperOptions options = new DumperOptions();

        options.setIndent(2);
        options.setPrettyFlow(true);
        options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);

        try {
            File file = new File(SimpleClans.getInstance().getDataFolder(), "clans/" + clanConfiguration.getName() + ".yml");

            if (!file.exists()) {
                file.createNewFile();
            }

            Yaml yaml = new Yaml(options);

            yaml.dump(clanConfiguration, new PrintWriter(file));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}