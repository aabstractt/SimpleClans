package com.wxav.simpleclans.command.subcommand;

import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;
import cn.nukkit.utils.TextFormat;
import com.wxav.simpleclans.clan.Clan;
import com.wxav.simpleclans.clan.ClanFactory;
import com.wxav.simpleclans.command.SubCommand;
import com.wxav.simpleclans.session.Session;
import com.wxav.simpleclans.session.SessionFactory;

public class CreateCommand extends SubCommand {

    public CreateCommand(String name, String description, String usage) {
        super(name, description, usage);
    }

    @Override
    public void execute(CommandSender sender, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(TextFormat.RED + "Run this command in-game");

            return;
        }

        Session session = SessionFactory.getInstance().getPlayerSession((Player) sender);

        if (session.getClan() != null) {
            session.sendTranslatedMessage("ALREADY_IN_CLAN");

            return;
        }

        if (args.length < 1) {
            sender.sendMessage("Use /" + label + " help");

            return;
        }

        if (ClanFactory.getInstance().clanExists(args[0])) {
            session.sendTranslatedMessage("CLAN_ALREADY_EXISTS", args[0]);

            return;
        }

        session.setClanName(args[0]);
        SessionFactory.getInstance().saveSession(session);

        ClanFactory.getInstance().createClan(new Clan().uniqueId().name(args[0]).leader(session.getName()).member(session.getName()));
    }
}