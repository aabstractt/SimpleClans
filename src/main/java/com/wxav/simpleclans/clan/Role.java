package com.wxav.simpleclans.clan;

import com.wxav.simpleclans.SimpleClans;

public enum Role {

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

    public boolean canPromote() {
        return name().equals(LEADER.name());
    }

    public boolean canDemote() {
        return name().equals(LEADER.name());
    }

    public static Role valueOf(int ordinal) {
        for (Role value : values()) {
            if (value.ordinal() == ordinal) {
                return value;
            }
        }

        return null;
    }
}