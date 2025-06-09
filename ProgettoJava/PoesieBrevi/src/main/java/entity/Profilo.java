package entity;

import java.util.Date;

public class Profilo {
    private final String username;
    private final String bio;
    private String immagineProfilo;
    private final Date dataNascita;

    public Profilo(String username, String bio, String immagineProfilo, Date dataNascita) {
        this.username = username;
        this.bio = bio;
        this.immagineProfilo = immagineProfilo;
        this.dataNascita = dataNascita;
    }

    public String getUsername() {
        return username;
    }

    public String getBio() {
        return bio;
    }

    public String getImmagineProfilo() {
        return immagineProfilo;
    }

    public void setImmagineProfilo(String immagineProfilo) {
        this.immagineProfilo = immagineProfilo;
    }

    public Date getDataNascita() {
        return dataNascita;
    }

    @Override
    public String toString() {
        return "Profilo{" +
                "username='" + username + '\'' +
                ", bio='" + bio + '\'' +
                ", immagineProfilo='" + immagineProfilo + '\'' +
                ", dataNascita=" + dataNascita +
                '}';
    }
}