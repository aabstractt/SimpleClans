package com.wxav.simpleclans.clan;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ClanConfiguration {

    private String name;
    private String leader;
    private List<String> members = new ArrayList<>();

    public ClanConfiguration name(String name) {
        this.name = name;

        return this;
    }

    public ClanConfiguration leader(String leader) {
        this.leader = leader;

        return this;
    }

    public ClanConfiguration member(String member) {
        this.members.add(member);

        return this;
    }
}