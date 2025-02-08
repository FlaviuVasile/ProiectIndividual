import java.sql.*;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class Salon {
    private Map<String, Client> clienti;
    private Map<String, Frizer> frizeri;
    private List<Programare> programari;
    private long clientCounter = 1; // Contor pentru ID-uri de clienți
    private long frizerCounter = 1; // Contor pentru ID-uri de frizeri

    public Salon() {
        this.clienti = new HashMap<>();
        this.frizeri = new HashMap<>();
        this.programari = new ArrayList<>();
        incarcaClienti();
        incarcaFrizeri();
        incarcaProgramari(); // Adaugă acest apel

    }

    // Încarcă frizerii din baza de date
    private void incarcaFrizeri() {
        String query = "SELECT * FROM Frizeri";
        try (Connection connection = DatabaseConnection.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                long id = resultSet.getLong("id");
                String nume = resultSet.getString("nume");
                String numarTelefon = resultSet.getString("numar_telefon");
                double tarif = resultSet.getDouble("tarif");
                String specializari = resultSet.getString("specializari");

                Frizer frizer = new Frizer(id, nume, numarTelefon, tarif, specializari);
                frizeri.put(nume, frizer); // Adaugă în mapă folosind numele ca cheie
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Încarcă clienții din baza de date
    public void incarcaClienti() {
        String query = "SELECT * FROM Clienti";
        try (Connection connection = DatabaseConnection.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                long id = resultSet.getLong("id");
                String nume = resultSet.getString("nume");
                String numarTelefon = resultSet.getString("numar_telefon");
                String preferinte = resultSet.getString("preferinte");

                Client client = new Client(id, nume, numarTelefon, preferinte);
                clienti.put(nume, client); // Adaugă în mapă folosind numele ca cheie
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Adaugă un client în baza de date și în mapă
    // Anulează o programare din baza de date și din memorie
    // Anulează o programare din baza de date și din memorie
    public boolean anuleazaProgramare(long programareId) {
        // Găsim programarea în lista locală de programări
        Programare programareDeAnulat = programari.stream()
                .filter(p -> p.getId() == programareId)
                .findFirst()
                .orElse(null);

        // Dacă programarea nu este găsită în memorie
        if (programareDeAnulat == null) {
            System.out.println("Programarea cu ID-ul " + programareId + " nu a fost găsită în memorie.");
            return false;
        }

        // Debug: Verificăm dacă am găsit programarea corect în memorie
        System.out.println("Programare găsită în memorie: " + programareDeAnulat);

        // Ștergem programarea din baza de date
        String query = "DELETE FROM Programari WHERE id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            // Setăm ID-ul programării pentru a-l șterge
            statement.setLong(1, programareId);

            // Executăm instrucțiunea de ștergere
            int rowsAffected = statement.executeUpdate();

            // Dacă programarea a fost ștearsă din baza de date
            if (rowsAffected > 0) {
                // Debug: Verificăm câte rânduri au fost afectate
                System.out.println("Rânduri afectate: " + rowsAffected);

                // Ștergem programarea din lista locală
                programari.remove(programareDeAnulat);
                System.out.println("Programarea cu ID-ul " + programareId + " a fost anulată cu succes.");
                return true;
            } else {
                // Dacă nu au fost afectate rânduri, programarea nu există în baza de date
                System.out.println("Programarea cu ID-ul " + programareId + " nu a fost găsită în baza de date.");
                return false;
            }
        } catch (SQLException e) {
            // Afișăm eroarea detaliată de SQL
            System.err.println("Eroare la anularea programării în baza de date: " + e.getMessage());
            return false; // În caz de eroare SQL
        }
    }


    public void adaugaClient(Client client) {
        String query = "INSERT INTO Clienti (nume, numar_telefon, preferinte) VALUES (?, ?, ?)";


        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, client.getNume());
            statement.setString(2, client.getNumarTelefon());
            statement.setString(3, client.getPreferinte());
            statement.executeUpdate();

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    client.setId(generatedKeys.getLong(1));
                }
            }
            clienti.put(client.getNume(), client);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void incarcaProgramari() {
        String query = "SELECT * FROM Programari";
        try (Connection connection = DatabaseConnection.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                long id = resultSet.getLong("id");
                long clientId = resultSet.getLong("client_id");
                long frizerId = resultSet.getLong("frizer_id");
                String serviciu = resultSet.getString("serviciu");
                Timestamp dataOraTimestamp = resultSet.getTimestamp("data_ora");
                LocalDateTime dataOra = dataOraTimestamp.toLocalDateTime();

                // Găsește clientul și frizerul din mape utilizând ID-urile
                Client client = clienti.values().stream()
                        .filter(c -> c.getId() == clientId)
                        .findFirst()
                        .orElse(null);

                Frizer frizer = frizeri.values().stream()
                        .filter(f -> f.getId() == frizerId)
                        .findFirst()
                        .orElse(null);

                // Dacă clientul și frizerul există, creează programarea
                if (client != null && frizer != null) {
                    Programare programare = new Programare(client, frizer, serviciu, dataOra);
                    programari.add(programare); // Adaugă programarea la lista de programări
                    System.out.println("Încărcat: Programare ID " + id + ", Client: " + client.getNume() + ", Frizer: " + frizer.getNume());
                } else {
                    System.err.println("Programare ID " + id + " are client sau frizer inexistent.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Adaugă un frizer în baza de date și în mapă
    public void adaugaFrizer(Frizer frizer) {
        String query = "INSERT INTO Frizeri (nume, numar_telefon, tarif, specializari) VALUES (?, ?, ?, ?)";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, frizer.getNume());
            statement.setString(2, frizer.getNumarTelefon());
            statement.setDouble(3, frizer.getTarif());
            statement.setString(4, frizer.getSpecializari());
            statement.executeUpdate();

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    frizer.setId(generatedKeys.getLong(1));
                }
            }
            frizeri.put(frizer.getNume(), frizer);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Creează o programare în baza de date și o adaugă în memorie
    public long creeazaProgramare(Programare programare) {
        LocalDateTime oraProgramare = programare.getDataOra();

        // Verificăm suprapunerile în lista de programări
        for (Programare p : programari) {
            LocalDateTime oraExistenta = p.getDataOra();

            // Verificăm dacă frizerul este același
            if (p.getFrizer().getId() == programare.getFrizer().getId()) {
                // Calculăm diferența în minute
                long diferenta = Math.abs(ChronoUnit.MINUTES.between(oraExistenta, oraProgramare));

                // Verificăm dacă suprapune cu mai puțin de 30 de minute
                if (diferenta < 30) {
                    System.out.println("Frizerul este deja rezervat în acest interval. Alegeți altă oră.");
                    return -1; // Nu permitem crearea programării
                }
            }
        }

        // Dacă nu există suprapuneri, adăugăm programarea
        String query = "INSERT INTO Programari (client_id, frizer_id, serviciu, data_ora) VALUES (?, ?, ?, ?)";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            statement.setLong(1, programare.getClient().getId());
            statement.setLong(2, programare.getFrizer().getId());
            statement.setString(3, programare.getServiciu());
            statement.setTimestamp(4, Timestamp.valueOf(programare.getDataOra()));
            statement.executeUpdate();

            // Programarea este adăugată în colecția locală
            programari.add(programare);

            // Dacă programarea este adăugată cu succes, returnează ID-ul programării
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getLong(1); // Returnează ID-ul generat
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1; // Eșec
    }

    // Șterge un frizer din baza de date și din memorie
    public void stergeFrizer(String numeFrizerStergere) {
        String query = "DELETE FROM Frizeri WHERE nume = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, numeFrizerStergere);
            statement.executeUpdate();
            frizeri.remove(numeFrizerStergere);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Șterge un client din baza de date și din memorie
    public void stergeClient(String numeClientStergere) {
        String query = "DELETE FROM Clienti WHERE nume = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, numeClientStergere);
            statement.executeUpdate();
            clienti.remove(numeClientStergere);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Vizualizează clienții
    public void vizualizareClienti() {
        clienti.forEach((nume, client) ->
                System.out.println("ID: " + client.getId() + ", Nume: " + nume + ", Telefon: " + client.getNumarTelefon() + ", Preferințe: " + client.getPreferinte())
        );
    }

    // Vizualizează frizerii
    public void vizualizareFrizeri() {
        frizeri.forEach((nume, frizer) ->
                System.out.println("ID: " + frizer.getId() + ", Nume: " + nume + ", Telefon: " + frizer.getNumarTelefon() + ", Tarif: " + frizer.getTarif() + ", Specializări: " + frizer.getSpecializari())
        );
    }

    // Metode pentru generarea de ID-uri
    public long generateClientId() {
        return clientCounter++;
    }

    public long generateFrizerId() {
        return frizerCounter++;
    }

    // Getteri pentru colecții
    public Map<String, Client> getClienti() {
        return clienti;
    }

    public Map<String, Frizer> getFrizeri() {
        return frizeri;
    }

    public List<Programare> getProgramari() {
        return programari;
    }
}
