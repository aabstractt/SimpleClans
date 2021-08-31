package com.wxav.simpleclans.command.subcommand;

import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;
import cn.nukkit.utils.TextFormat;
import com.wxav.simpleclans.clan.Clan;
import com.wxav.simpleclans.clan.Role;
import com.wxav.simpleclans.command.SubCommand;
import com.wxav.simpleclans.session.Session;
import com.wxav.simpleclans.session.SessionFactory;

public class LeaveCommand extends SubCommand {

    public LeaveCommand(String name, String description) {
        super(name, description);
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

        if (session.getRole() == Role.LEADER) {
            clan.disband();
        } else {
            for (Session target : clan.getMembersOnline()) {
                target.sendTranslatedMessage("PLAYER_LEFT_CLAN", session.getName());
            }

            clan.getMembers().remove(session.getName().toLowerCase());

            session.setClanName(null);
            session.setRole(null);

            SessionFactory.getInstance().saveSession(session, true);
        }
    }
}