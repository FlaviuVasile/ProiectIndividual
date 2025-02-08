public class Client {

    private Long id;
    private String nume;
    private String numarTelefon;
    private String preferinte;


    public Client(long id, String nume, String numarTelefon, String preferinte) {
        this.nume = nume;
        this.numarTelefon = numarTelefon;
        this.preferinte = preferinte;
        this.id=id;
    }

    // Getters and Setters
    public String getNume() {
        return nume;
    }
    public Long getId() {
        return id;
    }


    public void setNume(String nume) {
        this.nume = nume;
    }

    public String getNumarTelefon() {
        return numarTelefon;
    }


    public String getPreferinte() {
        return preferinte;
    }

    public void setPreferinte(String preferinte) {
        this.preferinte = preferinte;
    }
    @Override
    public String toString() {
        return "Client [nume=" + nume + ", numarTelefon=" + numarTelefon + ", preferinte=" + preferinte + "]";
    }

    public void setNumarTelefon(String numarTelefon) {
        if (numarTelefon.matches("\\d{10}")) {  // Verifică dacă numărul are exact 10 cifre
            this.numarTelefon = numarTelefon;
        } else {
            throw new IllegalArgumentException("Numărul de telefon nu este valid!");
        }
    }

    public void setId(long id) {
        this.id=id;
    }



}
