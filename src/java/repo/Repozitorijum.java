package repo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.websocket.Session;

public class Repozitorijum {

    private List<String> listaKorisnika;
    private List<Session> listaOdjavljenih;
    private Map<String, Session> mapaKorisnici;
    private static Repozitorijum instance;

    private Repozitorijum() {
        this.listaKorisnika = new ArrayList<>();
        this.listaOdjavljenih = new ArrayList<>();
        this.mapaKorisnici = new HashMap<>();
        this.listaKorisnika.add("Vidan");
        this.listaKorisnika.add("Ivan");
        this.listaKorisnika.add("Marko");
    }

    public static Repozitorijum getInstance() {
        if (instance == null) {
            instance = new Repozitorijum();
        }
        return instance;
    }

    public List<String> vratiListuKorisnika() {
        return this.listaKorisnika;
    }
    
    public void dodajUMapu(String korisnickoIme, Session session) {
        this.mapaKorisnici.put(korisnickoIme, session);
    }
    
    public Session vratiSesiju(String korisnickoIme) {
        return this.mapaKorisnici.get(korisnickoIme);
    }
    
    public List<Session> vratiListuOdjavljenih() {
        return this.listaOdjavljenih;
    }
    
    public void dodajUListuOdjavljenih(Session session) {
        this.listaOdjavljenih.add(session);
        for (Session s : this.listaOdjavljenih) {
            System.out.println(s.getId());
        }
    }

}
