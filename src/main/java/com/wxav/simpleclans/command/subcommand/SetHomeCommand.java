package com.wxav.simpleclans.command.subcommand;

import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;
import cn.nukkit.level.Position;
import cn.nukkit.utils.TextFormat;
import com.wxav.simpleclans.clan.Clan;
import com.wxav.simpleclans.clan.ClanFactory;
import com.wxav.simpleclans.command.SubCommand;
import com.wxav.simpleclans.session.Session;
import com.wxav.simpleclans.session.SessionFactory;

import java.util.HashMap;

public class SetHomeCommand extends SubCommand {

    public SetHomeCommand(String name, String description, String usage) {
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

        if (!session.getRole().canModifyHome()) {
            session.sendTranslatedMessage("YOU_CANT_USE_THIS");

            return;
        }

        session.sendTranslatedMessage("HOME_SUCCESSFULLY_SET");

        Position pos = ((Player) sender).getPosition();

        clan.setHomePosition(new HashMap<String, Object>() {{
            put("x", pos.getFloorX());
            put("y", pos.getFloorY());
            put("z", pos.getFloorZ());
            put("world", pos.getLevel().getFolderName());
        }});

        ClanFactory.getInstance().saveClan(clan);
    }
}
