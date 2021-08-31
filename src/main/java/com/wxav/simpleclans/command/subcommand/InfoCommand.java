package com.wxav.simpleclans.command.subcommand;

import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;
import cn.nukkit.utils.TextFormat;
import com.wxav.simpleclans.clan.Clan;
import com.wxav.simpleclans.clan.ClanFactory;
import com.wxav.simpleclans.command.SubCommand;
import com.wxav.simpleclans.session.Session;
import com.wxav.simpleclans.session.SessionFactory;

public class InfoCommand extends SubCommand {

    public InfoCommand(String name, String description) {
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

        if (args.length > 0) {
            clan = ClanFactory.getInstance().getClanName(args[0]);
        }

        if (clan == null) {
            session.sendTranslatedMessage("CLAN_NOT_FOUND");

            return;
        }

        session.sendTranslatedMessage("CLAN_INFO", clan.getName(), clan.getLeader(), String.valueOf(clan.getMembers().size()), String.valueOf(clan.getMembersOnline().size()), String.valueOf(Math.abs(clan.getMembers().size() - clan.getMembersOnline().size())), clan.getMotd());
    }
}
