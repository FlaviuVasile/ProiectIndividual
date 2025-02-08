import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FrizerService {
    public void adaugaFrizer(Frizer frizer) throws SQLException {
        String query = "INSERT INTO Frizer (nume, numar_telefon, tarif, specializari) VALUES (?, ?, ?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, frizer.getNume());
            statement.setString(2, frizer.getNumarTelefon());
            statement.setDouble(3, frizer.getTarif());
            statement.setString(4, frizer.getSpecializari());
            statement.executeUpdate();
        }
    }

    public List<Frizer> getFrizeri() throws SQLException {
        List<Frizer> frizeri = new ArrayList<>();
        String query = "SELECT * FROM Frizeri";

        try (Connection connection = DatabaseConnection.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                long id=1;
                Frizer frizer = new Frizer(
                        id, resultSet.getString("nume"),
                        resultSet.getString("numar_telefon"),
                        resultSet.getDouble("tarif"),
                        resultSet.getString("specializari")
                );
                frizeri.add(frizer);
            }
        }
        return frizeri;
    }

    public void editeazaFrizer(int frizerId, String numeNou, String telefonNou, double tarifNou, String specializariNoi) throws SQLException {
        String query = "UPDATE Frizeri SET nume = ?, numar_telefon = ?, tarif = ?, specializari = ? WHERE id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, numeNou);
            statement.setString(2, telefonNou);
            statement.setDouble(3, tarifNou);
            statement.setString(4, specializariNoi);
            statement.setInt(5, frizerId);
            statement.executeUpdate();
        }
    }

    public void stergeFrizer(int frizerId) throws SQLException {
        String query = "DELETE FROM Frizeri WHERE id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, frizerId);
            statement.executeUpdate();
        }
    }
}
