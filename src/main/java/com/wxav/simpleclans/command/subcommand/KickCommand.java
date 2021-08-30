package com.wxav.simpleclans.command.subcommand;

import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;
import cn.nukkit.utils.TextFormat;
import com.wxav.simpleclans.clan.Clan;
import com.wxav.simpleclans.clan.ClanFactory;
import com.wxav.simpleclans.command.SubCommand;
import com.wxav.simpleclans.session.Session;
import com.wxav.simpleclans.session.SessionFactory;

public class KickCommand extends SubCommand {

    public KickCommand(String name, String description, String usage) {
        super(name, description, usage);
    }

    @Override
    public void execute(CommandSender sender, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(TextFormat.RED + "Run this command in-game");

            return;
        }

        if (args.length == 0) {
            sender.sendMessage(TextFormat.RED + "Use /" + label + " kick <player>");

            return;
        }

        Session session = SessionFactory.getInstance().getPlayerSession((Player) sender);
        Clan clan = session.getClan();

        if (clan == null) {
            session.sendTranslatedMessage("YOU_NEED_CLAN");

            return;
        }

        if (!session.getRole().canKick()) {
            session.sendTranslatedMessage("YOU_CANT_USE_THIS");

            return;
        }

        Session target = SessionFactory.getInstance().getSession(args[0]);

        if (target == null) {
            sender.sendMessage(TextFormat.RED + "Player not found");

            return;
        }

        if (!clan.getMembers().contains(target.getName().toLowerCase())) {
            session.sendTranslatedMessage("PLAYER_NOT_IS_MEMBER", target.getName());

            return;
        }

        clan.removeMember(target.getName());

        target.setRole(null);
        target.setClanName(null);

        ClanFactory.getInstance().saveClan(clan);
        SessionFactory.getInstance().removeSession(target.getName());

        target.sendTranslatedMessage("CLAN_KICKED", session.getName());

        for (Session member : clan.getMembersOnline()) {
            member.sendTranslatedMessage("PLAYER_KICKED", target.getName(), session.getName());
        }
    }
}