package com.wxav.simpleclans.command.subcommand;

import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;
import cn.nukkit.utils.TextFormat;
import com.wxav.simpleclans.clan.Clan;
import com.wxav.simpleclans.command.SubCommand;
import com.wxav.simpleclans.session.Session;
import com.wxav.simpleclans.session.SessionFactory;

public class InviteCommand extends SubCommand {

    public InviteCommand(String name, String description, String usage) {
        super(name, description, usage);
    }

    @Override
    public void execute(CommandSender sender, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(TextFormat.RED + "Run this command in-game");

            return;
        }

        if (args.length == 0) {
            sender.sendMessage(TextFormat.RED + "Use /" + label + " invite <player>");

            return;
        }

        Session session = SessionFactory.getInstance().getPlayerSession((Player) sender);
        Clan clan = session.getClan();

        if (clan == null) {
            session.sendTranslatedMessage("YOU_NEED_CLAN");

            return;
        }

        if (!session.getRole().canInvite()) {
            session.sendTranslatedMessage("YOU_CANT_USE_THIS");

            return;
        }

        Session target = SessionFactory.getInstance().getSession(args[0]);

        if (target == null) {
            sender.sendMessage(TextFormat.RED + "Player not found");

            return;
        }

        if (target.hasInvite(clan.getUniqueId())) {
            session.sendTranslatedMessage("ALREADY_INVITED");

            return;
        }

        if (target.getClan() != null) {
            session.sendTranslatedMessage("PLAYER_ALREADY_HAVE_CLAN", target.getName());

            return;
        }

        target.addInvite(clan.getUniqueId());
        target.sendTranslatedMessage("CLAN_INVITE_RECEIVED", session.getName(), clan.getName());

        session.sendTranslatedMessage("INVITATION_SUCCESSFULLY_SENT", target.getName());
    }
}