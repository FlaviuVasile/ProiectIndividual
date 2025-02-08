import java.util.Objects;

public class Frizer {
    private Long id;
    private String nume;
    private String numarTelefon;
    private double tarif;
    private String specializari;

    public Frizer(long id, String nume, String numarTelefon, double tarif, String specializari) {
        this.nume = nume;
        this.numarTelefon = numarTelefon;
        this.tarif = tarif;
        this.specializari = specializari;
        this.id=id;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Frizer frizer = (Frizer) obj;
        return nume.equals(frizer.nume) && numarTelefon.equals(frizer.numarTelefon);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nume, numarTelefon);
    }


    // Getters and Setters
    public String getNume() {
        return nume;
    }
    public long getId() {
        return id;
    }


    public void setNume(String nume) {
        this.nume = nume;
    }

    public String getNumarTelefon() {
        return numarTelefon;
    }

    public void setNumarTelefon(String numarTelefon) {
        if (numarTelefon.matches("\\d{10}")) {  // Verifică dacă numărul de telefon are exact 10 cifre
            this.numarTelefon = numarTelefon;
        } else {
            throw new IllegalArgumentException("Numărul de telefon nu este valid.");
        }
    }

    public double getTarif() {
        return tarif;
    }

    public void setTarif(double tarif) {
        if (tarif >= 0) {
            this.tarif = tarif;
        } else {
            throw new IllegalArgumentException("Tariful trebuie să fie un număr pozitiv.");
        }
    }


    public String getSpecializari() {
        return specializari;
    }

    public void setSpecializari(String specializari) {
        this.specializari = specializari;
    }
    @Override
    public String toString() {
        return "Frizer [nume=" + nume + ", numarTelefon=" + numarTelefon + ", tarif=" + tarif + ", specializari=" + specializari + "]";
    }

    public boolean containsKey(String numeFrizerStergere) {
        return false;
    }


    public void setId(long aLong) {
        this.id=id;
    }
}
