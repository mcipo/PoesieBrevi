package entity;

public class User {
    private int id;
    private final String password;
    private final String email;
    private final String nome;
    private final String cognome;
    private final boolean isAdmin;
    private Profilo profilo;

    public User(String password, String email, String nome, String cognome, boolean isAdmin, Profilo profilo) {
        this.password = password;
        this.email = email;
        this.nome = nome;
        this.cognome = cognome;
        this.isAdmin = isAdmin;
        this.profilo = profilo;
    }

    public Profilo getProfilo() {
        return profilo;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getNome() {
        return nome;
    }

    public String getCognome() {
        return cognome;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setProfilo(Profilo profilo) {
        this.profilo = profilo;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", nome='" + nome + '\'' +
                ", cognome='" + cognome + '\'' +
                ", isAdmin=" + isAdmin +
                '}';
    }
}