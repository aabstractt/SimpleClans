package com.wxav.simpleclans.command.subcommand;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.command.CommandSender;
import cn.nukkit.level.Level;
import cn.nukkit.level.Position;
import cn.nukkit.utils.TextFormat;
import com.wxav.simpleclans.clan.Clan;
import com.wxav.simpleclans.command.SubCommand;
import com.wxav.simpleclans.session.Session;
import com.wxav.simpleclans.session.SessionFactory;

import java.util.Map;

public class HomeCommand extends SubCommand {

    public HomeCommand(String name, String description, String usage) {
        super(name, description, usage);
    }

    @Override
    public void execute(CommandSender sender, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(TextFormat.RED + "Run this command in-game");

            return;
        }

        Session session = SessionFactory.getInstance().getPlayerSession((Player) sender);

        Clan clan = session.getClan();

        if (clan == null) {
            session.sendTranslatedMessage("YOU_NEED_CLAN");

            return;
        }

        Map<String, Object> data = clan.getHomePosition();

        if (data.isEmpty()) {
            session.sendTranslatedMessage("INVALID_HOME");

            return;
        }

        Server.getInstance().loadLevel(data.get("world").toString());
        Level level = Server.getInstance().getLevelByName(data.get("world").toString());

        if (level == null) {
            session.sendTranslatedMessage("INVALID_HOME");

            return;
        }

        Position pos = new Position(Integer.parseInt(data.get("x").toString()), Integer.parseInt(data.get("y").toString()), Integer.parseInt(data.get("z").toString()), level);

        ((Player) sender).teleport(pos);

        session.sendTranslatedMessage("SUCCESSFULLY_TELEPORTED");
    }
}