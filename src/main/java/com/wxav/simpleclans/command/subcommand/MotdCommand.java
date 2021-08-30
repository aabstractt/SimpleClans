package com.wxav.simpleclans.command.subcommand;

import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;
import cn.nukkit.utils.TextFormat;
import com.wxav.simpleclans.clan.Clan;
import com.wxav.simpleclans.clan.ClanFactory;
import com.wxav.simpleclans.command.SubCommand;
import com.wxav.simpleclans.session.Session;
import com.wxav.simpleclans.session.SessionFactory;

public class MotdCommand extends SubCommand {

    public MotdCommand(String name, String description) {
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

        if (args.length == 0) {
            session.sendTranslatedMessage("&cUse /{%0} motd <motd>", label);

            return;
        }

        if (!session.getRole().canModifyMotd()) {
            session.sendTranslatedMessage("YOU_CANT_USE_THIS");

            return;
        }

        clan.setMotd(String.join(" ", args));
        ClanFactory.getInstance().saveClan(clan);

        session.sendTranslatedMessage("MOTD_CHANGED", clan.getMotd());
    }
}
