package com.wxav.simpleclans.clan;

import com.wxav.simpleclans.SimpleClans;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Role {

    MEMBER(1),
    OFFICER(2),
    COLEADER(3),
    LEADER(4);

    private int id;

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