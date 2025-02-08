import java.time.LocalDateTime;
import java.util.Objects;

public class Programare {
    private long id;
    private Client client;
    private Frizer frizer;
    private String serviciu;
    private LocalDateTime dataOra;

    public Programare( Client client, Frizer frizer, String serviciu, LocalDateTime dataOra) {
        this.client = client;
        this.frizer = frizer;
        this.serviciu = serviciu;
        this.dataOra = dataOra;
    }

    public Programare(String client, String frizer, String serviciu, LocalDateTime dataOra) {
    }


    // Getters and Setters
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Frizer getFrizer() {
        return frizer;
    }

    public void setFrizer(Frizer frizer) {
        this.frizer = frizer;
    }

    public String getServiciu() {
        return serviciu;
    }

    public void setServiciu(String serviciu) {
        this.serviciu = serviciu;
    }

    public LocalDateTime getDataOra() {
        return dataOra;
    }

    public void setDataOra(LocalDateTime dataOra) {
        if (dataOra.isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Data și ora programării nu pot fi în trecut!");
        }
        this.dataOra = dataOra;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Programare that = (Programare) obj;
        return this.id == that.id;  // Compari ID-ul
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);  // Folosești doar ID-ul
    }

    public long timpPanaLaProgramare() {
        return LocalDateTime.now().until(dataOra, java.time.temporal.ChronoUnit.MINUTES);
    }

    @Override
    public String toString() {
        return "Programare [client=" + client.getNume() + ", frizer=" + frizer.getNume() + ", serviciu=" + serviciu + ", dataOra=" + dataOra + "]";
    }
}
