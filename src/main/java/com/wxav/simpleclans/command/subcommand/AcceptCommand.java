package com.wxav.simpleclans.command.subcommand;

import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;
import cn.nukkit.utils.TextFormat;
import com.wxav.simpleclans.clan.Clan;
import com.wxav.simpleclans.clan.ClanFactory;
import com.wxav.simpleclans.command.SubCommand;
import com.wxav.simpleclans.session.Session;
import com.wxav.simpleclans.session.SessionFactory;

public class AcceptCommand extends SubCommand {

    public AcceptCommand(String name, String description, String usage) {
        super(name, description, usage);
    }

    @Override
    public void execute(CommandSender sender, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(TextFormat.RED + "Run this command in-game");

            return;
        }

        if (args.length == 0) {
            sender.sendMessage(TextFormat.RED + "Use /" + label + " accept <player>");

            return;
        }

        Session session = SessionFactory.getInstance().getPlayerSession((Player) sender);

        if (session.getClan() != null) {
            session.sendTranslatedMessage("YOU_ALREADY_HAVE_CLAN");

            return;
        }

        Session target = SessionFactory.getInstance().getSession(args[0]);

        if (target == null) {
            sender.sendMessage(TextFormat.RED + "Player not found");

            return;
        }

        Clan clan = target.getClan();

        if (clan == null) {
            session.sendTranslatedMessage("PLAYER_DOES_NOT_HAVE_CLAN", target.getName());

            return;
        }

        if (!session.hasInvite(clan.getUniqueId())) {
            session.sendTranslatedMessage("PLAYER_HAS_NOT_INVITED_YOU", target.getName());

            return;
        }

        session.removeInvite(null);

        clan.member(session.getName());
        session.setClanName(clan.getName());

        ClanFactory.getInstance().saveClan(clan);
        SessionFactory.getInstance().saveSession(session);

        session.sendTranslatedMessage("CLAN_INVITATION_ACCEPTED", target.getName(), clan.getName());
        target.sendTranslatedMessage("PLAYER_INVITATION_ACCEPTED", session.getName());

        for (Session member : clan.getMembersOnline()) {
            member.sendTranslatedMessage("PLAYER_JOINED_CLAN", session.getName());
        }
    }
}
