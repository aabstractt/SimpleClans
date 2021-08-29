package com.wxav.simpleclans.clan;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Clan {

    private String name;
    private String leader;
    private List<String> members = new ArrayList<>();

    public Clan name(String name) {
        this.name = name;

        return this;
    }

    public Clan leader(String leader) {
        this.leader = leader;

        return this;
    }

    public Clan member(String member) {
        this.members.add(member);

        return this;
    }

    @Override
    public String toString() {
        return "Clan{" + "name='" + name + '\'' + ", leader='" + leader + '\'' + ", members=" + members + '}';
    }
}