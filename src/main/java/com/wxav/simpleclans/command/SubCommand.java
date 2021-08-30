package com.wxav.simpleclans.command;

import cn.nukkit.command.CommandSender;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public abstract class SubCommand {

    protected String name;
    protected String description;

    public abstract void execute(CommandSender sender, String label, String[] args);
}