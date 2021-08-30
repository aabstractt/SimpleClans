package com.wxav.simpleclans.clan;

import com.wxav.simpleclans.SimpleClans;

public enum ClanRole {

    MEMBER(),
    OFFICER(),
    COLEADER(),
    LEADER();

    public boolean canInvite() {
        return name().equals(LEADER.name()) || SimpleClans.getInstance().getConfig().getBoolean("role." + name().toLowerCase() + ".invite");
    }

    public boolean canKick() {
        return name().equals(LEADER.name()) || SimpleClans.getInstance().getConfig().getBoolean("role." + name().toLowerCase() + ".kick");
    }

    public boolean canModifyHome() {
        return name().equals(LEADER.name()) || SimpleClans.getInstance().getConfig().getBoolean("role." + name().toLowerCase() + ".modifyHome");
    }

    public boolean canModifyMotd() {
        return name().equals(LEADER.name()) || SimpleClans.getInstance().getConfig().getBoolean("role." + name().toLowerCase() + ".modifyMotd");
    }
}