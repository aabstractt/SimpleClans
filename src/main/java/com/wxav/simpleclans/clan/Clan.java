package com.wxav.simpleclans.clan;

import com.wxav.simpleclans.session.Session;
import com.wxav.simpleclans.session.SessionFactory;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

@AllArgsConstructor
@Getter
@Setter
public class Clan {

    private String uniqueId;
    private String name;
    private String leader;
    private List<String> members = new ArrayList<>();
    private String motd = "";
    private Map<String, Object> homePosition = new HashMap<>();

    public Clan() {
        // TODO: Empty constructor
    }

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
        this.members.add(member.toLowerCase());

        return this;
    }

    public void removeMember(String name) {
        this.members.remove(name.toLowerCase());
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

    public void disband() {
        for (Session session : getMembersOnline()) {
            session.sendTranslatedMessage("CLAN_DISBAND", this.leader);

            session.setClanName(null);
            session.setRole(null);
        }

        for (String name : this.members) {
            SessionFactory.getInstance().saveSession(new Session(name, UUID.randomUUID(), null, null), true);
        }

        ClanFactory.getInstance().removeClan(this);
    }

    @Override
    public String toString() {
        return "Clan{" + "name='" + name + '\'' + ", leader='" + leader + '\'' + ", members=" + members + '}';
    }
}