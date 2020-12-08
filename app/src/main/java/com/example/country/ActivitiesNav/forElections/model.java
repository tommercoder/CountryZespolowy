package com.example.country.ActivitiesNav.forElections;

public class model {

    String name,age,party,purl,info,votes;
    public int position;
    private boolean expanded;
    boolean vote;
    model()
    {

    }
    public model(String name, String age, String party, String purl,String info,String votes)
    {
        this.name = name;
        this.age = age;
        this.party = party;
        this.purl = purl;
        this.info = info;
        this.expanded = false;
        this.vote = false;
        this.votes = votes;
    }

    public String getVotes() {
        return votes;
    }

    public void setVotes(String votes) {
        this.votes = votes;
    }

    public boolean isVote() {
        return vote;
    }

    public void setVote(boolean vote) {
        this.vote = vote;
    }

    public boolean isExpanded() {
        return expanded;
    }

    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getParty() {
        return party;
    }

    public void setParty(String party) {
        this.party = party;
    }

    public String getPurl() {
        return purl;
    }

    public void setPurl(String purl) {
        this.purl = purl;
    }
}
