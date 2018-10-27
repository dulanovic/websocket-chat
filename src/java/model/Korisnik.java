package model;

public class Korisnik {

    private int korisnikId;
    private String ime;
    private String prezime;

    public Korisnik() {
    }

    public Korisnik(int korisnikId, String ime, String prezime) {
        this.korisnikId = korisnikId;
        this.ime = ime;
        this.prezime = prezime;
    }

    public int getKorisnikId() {
        return korisnikId;
    }

    public void setKorisnikId(int korisnikId) {
        this.korisnikId = korisnikId;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getPrezime() {
        return prezime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    @Override
    public String toString() {
        return this.ime + this.prezime;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Korisnik) {
            Korisnik k = (Korisnik) obj;
            if (k.getKorisnikId() == this.korisnikId) {
                return true;
            } else return false;
        } else return false;
    }

}
