package server;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonWriter;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import repo.Repozitorijum;

@ServerEndpoint(value = "/server")
public class ChatServer {

    static List<Session> korisnici = Collections.synchronizedList(new ArrayList<Session>());
    private List<String> korisnickaImena = Repozitorijum.getInstance().vratiListuKorisnika();

    @OnOpen
    public void open(Session session) {
        korisnici.add(session);
        System.out.println("Connected");
    }

    @OnMessage
    public void message(String poruka, Session session) throws IOException {
        String korisnickoIme = (String) session.getUserProperties().get("korisnickoIme");
        if (korisnickoIme == null) {
            if (!Repozitorijum.getInstance().vratiListuKorisnika().contains(poruka)) {
                session.getBasicRemote().sendText(vratiJson("Sistem", "Pogresno korisnicko ime!"));
                return;
            }
            session.getUserProperties().put("korisnickoIme", poruka);
            korisnickoIme = poruka;

            session.getBasicRemote().sendText(vratiJson("Sistem", "Ulogovani ste u sistem - " + poruka));
        } else {
            for (Session s : korisnici) {
                s.getBasicRemote().sendText(vratiJson(korisnickoIme, poruka));
            }

            int trenutniKorisnik = this.korisnickaImena.indexOf(session.getUserProperties().get("korisnickoIme"));
            System.out.println(trenutniKorisnik);
            String sledeciKorisnik = "";
            if (this.korisnickaImena.size() > trenutniKorisnik + 1) {
                sledeciKorisnik = this.korisnickaImena.get(trenutniKorisnik + 1);
                System.out.println(sledeciKorisnik);
            } else {
                sledeciKorisnik = this.korisnickaImena.get(0);
                System.out.println(sledeciKorisnik);
            }

            for (Session s : korisnici) {
                if (!s.getUserProperties().get("korisnickoIme").equals(sledeciKorisnik)) {
                    s.getBasicRemote().sendText(vratiJson("poruka", "blokiraj"));
                } else {
                    s.getBasicRemote().sendText(vratiJson("poruka", "odblokiraj"));
                }
            }
        }
    }

    @OnClose
    public void close(Session session) {
        korisnici.remove(session);
        System.out.println("Disconnected");
    }

    private static String vratiJson(String korisnickoIme, String tekst) {
        JsonObject json = Json.createObjectBuilder().add("poruka", korisnickoIme + "> " + tekst).build();
        StringWriter sw = new StringWriter();
        try (JsonWriter jw = Json.createWriter(sw)) {
            jw.write(json);
        }
        return sw.toString();
    }

}
