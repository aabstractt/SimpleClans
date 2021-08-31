package com.wxav.simpleclans.command;

import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.utils.TextFormat;
import com.wxav.simpleclans.SimpleClans;
import com.wxav.simpleclans.command.subcommand.*;

import java.util.*;

public class ClanCommand extends Command {

    protected Map<String, SubCommand> commandMap = new HashMap<>();

    public ClanCommand(String name, String description) {
        super(name, description, null, new String[]{"c"});

        addCommand(
                new AcceptCommand("accept", "Accept a clan invitation"),
                new CreateCommand("create", "Create a new clan"),
                new DemoteCommand("demote", "Demote a player role"),
                new DenyCommand("deny", "Deny a clan invitation"),
                new HomeCommand("home", "Teleport to the clan home"),
                new InfoCommand("info", "See the clan info"),
                new InviteCommand("invite", "Invite a player to join the clan"),
                new KickCommand("kick", "Kick a player from the clan"),
                new LeaveCommand("leave", "Leave from the clan"),
                new MotdCommand("motd", "Change the clan motd"),
                new PromoteCommand("promote", "Promote a player role"),
                new SetHomeCommand("sethome", "Set a home position")
        );
    }

    @Override
    public boolean execute(CommandSender commandSender, String label, String[] args) {
        if (args.length == 0) {
            commandSender.sendMessage(TextFormat.RED + "Use /" + label + " help");

            return false;
        }

        if (args[0].equals("help")) {
            commandSender.sendMessage(TextFormat.BLUE + "SimpleClans commands:");

            for (SubCommand command : this.commandMap.values()) {
                commandSender.sendMessage(SimpleClans.translateMessage("COMMAND_HELP", label, command.getName(), command.getDescription()));
            }

            return false;
        }

        SubCommand command = this.getCommand(args[0].toLowerCase());

        if (command == null) {
            commandSender.sendMessage(TextFormat.RED + "Use /" + label + " help");

            return false;
        }

        args = formatArgs(Arrays.copyOfRange(args, 1, args.length));

        command.execute(commandSender, label, args);

        return false;
    }

    protected void addCommand(SubCommand... commands) {
        for (SubCommand command : commands) {
            this.commandMap.put(command.getName().toLowerCase(), command);
        }
    }

    protected SubCommand getCommand(String name) {
        return this.commandMap.get(name);
    }

    private String[] formatArgs(String[] args) {
        List<String> quotes = new ArrayList<>();

        StringBuilder temp = new StringBuilder();

        boolean inside = false;

        for (String s : args) {
            if (s.startsWith("\"") && s.endsWith("\"")) {
                quotes.add(s.substring(1, s.length() - 1));
            } else if (s.substring(0, 1).contains("\"")) {
                inside = true;

                temp.append(s.substring(1)).append(" ");
            } else if (s.substring(s.length() - 1).contains("\"")) {
                inside = false;

                temp.append(s, 0, s.length() - 1);

                quotes.add(temp.toString());

                temp = new StringBuilder();
            } else if (inside) {
                temp.append(s).append(" ");
            } else {
                quotes.add(s);
            }
        }

        return quotes.toArray(new String[0]);
    }
}
