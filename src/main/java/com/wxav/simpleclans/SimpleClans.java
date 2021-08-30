package com.wxav.simpleclans;

import cn.nukkit.plugin.PluginBase;
import cn.nukkit.plugin.PluginLogger;
import cn.nukkit.utils.LogLevel;
import com.creeperface.nukkit.placeholderapi.api.PlaceholderAPI;
import com.wxav.simpleclans.command.ClanCommand;
import com.wxav.simpleclans.listener.PlayerJoinListener;
import com.wxav.simpleclans.listener.PlayerQuitListener;
import lombok.Getter;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public class SimpleClans extends PluginBase {

    @Getter
    private static SimpleClans instance;

    private static final VersionInfo versionInfo = loadVersion();

    public PlaceholderAPI api = null;

    private final Map<String, Object> messages = new HashMap<>();

    @SuppressWarnings("ResultOfMethodCallIgnored")
    @Override
    public void onEnable() {
        instance = this;

        File file = new File(getDataFolder(), "clans/");

        if (!file.exists()) {
            file.mkdir();
        }

        PluginLogger logger = getLogger();

        // TODO: Waterdog log
        logger.info("§bStarting SimpleClans plugin!");
        logger.info("§9Commit Id: " + versionInfo.commitId());
        logger.info("§9Branch: " + versionInfo.branchName());
        logger.info("§9Build Author: " + versionInfo.author());
        logger.info("§9Development Build: " + versionInfo.development());

        if (!versionInfo.development() || (versionInfo.buildVersion().equals("#build") || versionInfo.branchName().equals("unknown"))) {
            logger.error("Custom build? Unofficial builds should be not run in production!");
        } else {
            logger.info("§bDiscovered branch §9" + versionInfo.branchName() + "§b commitId §9" + versionInfo.commitId());
        }

        saveResource("messages.properties");
        loadMessages();

        getServer().getCommandMap().register("clan", new ClanCommand("clan", "Manage your clan"));

        getServer().getPluginManager().registerEvents(new PlayerJoinListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerQuitListener(), this);

        api = PlaceholderAPI.getInstance();
    }

    private void loadMessages() {
        File file = new File(getDataFolder(), "messages.properties");

        if (file.isFile()) {
            try (FileReader rd = new FileReader(file)) {
                ResourceBundle bundle = new PropertyResourceBundle(rd);

                for (String key : bundle.keySet()) {
                    messages.put(key, bundle.getString(key));
                }
            } catch (IOException ex) {
                getLogger().log(LogLevel.ERROR, "Could not load custom messages.properties", ex);
            }
        }
    }

    public static String translateMessage(String message, String... args) {
        if (instance.messages.containsKey(message)) {
            message = (String) instance.messages.get(message);
        }

        for (int i = 0; i < args.length; i++) {
            message = message.replaceAll("\\{%" + i + "}", args[i]);
        }

        return message;
    }

    public static VersionInfo version() {
        return versionInfo;
    }

    private static VersionInfo loadVersion() {
        InputStream inputStream = SimpleClans.class.getClassLoader().getResourceAsStream("github.properties");

        if (inputStream == null) {
            return VersionInfo.unknown();
        }

        Properties properties = new Properties();

        try {
            properties.load(inputStream);
        } catch (IOException e) {
            return VersionInfo.unknown();
        }

        String branchName = properties.getProperty("git.branch", "unknown");
        String commitId = properties.getProperty("git.commit.id.abbrev", "unknown");

        return new VersionInfo(branchName, commitId, branchName.equals("release"), properties.getProperty("git.commit.user.name"));
    }
}