package com.wxav.simpleclans;

import cn.nukkit.plugin.PluginBase;
import cn.nukkit.utils.LogLevel;
import com.creeperface.nukkit.placeholderapi.api.PlaceholderAPI;
import com.wxav.simpleclans.command.ClanCommand;
import com.wxav.simpleclans.listener.PlayerJoinListener;
import com.wxav.simpleclans.listener.PlayerQuitListener;
import lombok.Getter;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

public class SimpleClans extends PluginBase {

    @Getter
    private static SimpleClans instance;

    public PlaceholderAPI api = null;

    private final Map<String, Object> messages = new HashMap<>();

    @Override
    public void onEnable() {
        instance = this;

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
}