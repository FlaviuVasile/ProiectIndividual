import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProgramareService {
    public void creeazaProgramare(Programare programare) throws SQLException {
        String query = "INSERT INTO Programare (client_id, frizer_id, serviciu, data_ora) VALUES (?, ?, ?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, programare.getClient().getId());
            statement.setLong(2, programare.getFrizer().getId());
            statement.setString(3, programare.getServiciu());
            statement.setTimestamp(4, Timestamp.valueOf(programare.getDataOra()));
            statement.executeUpdate();
        }
    }

    public List<Programare> getProgramari() throws SQLException {
        List<Programare> programari = new ArrayList<>();
        String query = "SELECT p.id, c.nume AS client, f.nume AS frizer, p.serviciu, p.data_ora " +
                "FROM Programare p " +
                "JOIN Client c ON p.client_id = c.id " +
                "JOIN Frizer f ON p.frizer_id = f.id";

        try (Connection connection = DatabaseConnection.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                Programare programare = new Programare(
                        resultSet.getString("client"),
                        resultSet.getString("frizer"),
                        resultSet.getString("serviciu"),
                        resultSet.getTimestamp("data_ora").toLocalDateTime()
                );
                programari.add(programare);
            }
        }
        return programari;
    }

    public void anuleazaProgramare(int programareId) throws SQLException {
        String query = "DELETE FROM Programare WHERE id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, programareId);
            statement.executeUpdate();
        }
    }
}
