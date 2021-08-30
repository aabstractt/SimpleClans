package com.wxav.simpleclans.clan;

import com.wxav.simpleclans.session.Session;
import com.wxav.simpleclans.session.SessionFactory;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class Clan {

    private String uniqueId;
    private String name;
    private String leader;
    private List<String> members = new ArrayList<>();

    public Clan uniqueId() {
        this.uniqueId = UUID.randomUUID().toString();

        return this;
    }

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

    public List<Session> getMembersOnline() {
        List<Session> sessions = new ArrayList<>();

        for (String name : this.members) {
            Session session = SessionFactory.getInstance().getSessionExact(name);

            if (session == null) {
                continue;
            }

            sessions.add(session);
        }

        return sessions;
    }

    @Override
    public String toString() {
        return "Clan{" + "name='" + name + '\'' + ", leader='" + leader + '\'' + ", members=" + members + '}';
    }
}