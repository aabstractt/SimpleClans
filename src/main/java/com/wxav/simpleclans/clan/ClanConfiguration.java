package com.wxav.simpleclans.clan;

import lombok.Getter;

import java.util.List;

public class ClanConfiguration {

    @Getter
    private String name;
    @Getter
    private String leader;
    @Getter
    private List<String> members;

    public ClanConfiguration setName(String name) {
        this.name = name;

        return this;
    }

    public ClanConfiguration setLeader(String leader) {
        this.leader = leader;

        return this;
    }

    public ClanConfiguration addMember(String member) {
        this.members.add(member);

        return this;
    }
}