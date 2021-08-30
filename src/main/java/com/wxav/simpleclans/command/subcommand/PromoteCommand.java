package com.wxav.simpleclans.command.subcommand;

import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;
import cn.nukkit.utils.TextFormat;
import com.wxav.simpleclans.clan.Clan;
import com.wxav.simpleclans.clan.Role;
import com.wxav.simpleclans.command.SubCommand;
import com.wxav.simpleclans.session.Session;
import com.wxav.simpleclans.session.SessionFactory;

public class PromoteCommand extends SubCommand {

    public PromoteCommand(String name, String description, String usage) {
        super(name, description, usage);
    }

    @Override
    public void execute(CommandSender sender, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(TextFormat.RED + "Run this command in-game");

            return;
        }

        if (args.length == 0) {
            sender.sendMessage(TextFormat.RED + "Use /" + label + " promote <player>");

            return;
        }

        Session session = SessionFactory.getInstance().getPlayerSession((Player) sender);
        Clan clan = session.getClan();

        if (clan == null) {
            session.sendTranslatedMessage("YOU_NEED_CLAN");

            return;
        }

        if (!session.getRole().canPromote()) {
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

        if (target.getRole().ordinal() >= Role.COLEADER.ordinal()) {
            session.sendTranslatedMessage("YOU_CANNOT_PROMOTE_PLAYER", target.getName());

            return;
        }

        Role targetRole = Role.valueOf(target.getRole().ordinal() + 1);

        if (targetRole == null) {
            return;
        }

        session.setRole(targetRole);
        SessionFactory.getInstance().saveSession(session);

        // TODO: charAt(0) = uppercase and the rest lowercase, example: Member
        String roleName = targetRole.name().charAt(0) + targetRole.name().substring(1).toLowerCase();

        target.sendTranslatedMessage("YOU_HAVE_BEEN_PROMOTED", session.getName(), roleName);

        for (Session member : clan.getMembersOnline()) {
            member.sendTranslatedMessage("PLAYER_PROMOTED", target.getName(), roleName, session.getName());
        }
    }
}