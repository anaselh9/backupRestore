package android.esisa.mybackup2.models;

public class Sms {
    private String numero;
    private String contenu;

    public Sms() {
    }

    public Sms(String numero, String contenu) {
        this.numero = numero;
        this.contenu = contenu;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getContenu() {
        return contenu;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    @Override
    public String toString() {
        return "Sms{" +
                "numero='" + numero + '\'' +
                ", contenu='" + contenu + '\'' +
                '}';
    }
}
