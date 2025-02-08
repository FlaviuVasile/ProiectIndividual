import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClientService {
    public void adaugaClient(Client client) throws SQLException {
        String query = "INSERT INTO Clienti (nume, numar_telefon, preferinte) VALUES (?, ?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, client.getNume());
            statement.setString(2, client.getNumarTelefon());
            statement.setString(3, client.getPreferinte());
            statement.executeUpdate();
        }
    }

    public List<Client> getClienti() throws SQLException {
        List<Client> clienti = new ArrayList<>();
        String query = "SELECT * FROM Clienti";

        try (Connection connection = DatabaseConnection.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                long id = 1;
                Client client = new Client(
                        id, resultSet.getString("nume"),
                        resultSet.getString("numar_telefon"),
                        resultSet.getString("preferinte")
                );
                clienti.add(client);
            }
        }
        return clienti;
    }

    public void editeazaClient(int clientId, String numeNou, String telefonNou, String preferinteNoi) throws SQLException {
        String query = "UPDATE Clienti SET nume = ?, numar_telefon = ?, preferinte = ? WHERE id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, numeNou);
            statement.setString(2, telefonNou);
            statement.setString(3, preferinteNoi);
            statement.setInt(4, clientId);
            statement.executeUpdate();
        }
    }

    public void stergeClient(int clientId) throws SQLException {
        String query = "DELETE FROM Clienti WHERE id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, clientId);
            statement.executeUpdate();
        }
    }
}
