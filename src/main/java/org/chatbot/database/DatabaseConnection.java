package org.chatbot.database;

import java.io.Closeable;
import java.sql.*;

public class DatabaseConnection implements IDatabaseConnection, AutoCloseable, Closeable {
    private final Connection connection;
    private final PreparedStatement insertStatement;
    private final PreparedStatement deleteStatement;

    public DatabaseConnection(String url, String user, String password) throws SQLException {
        connection = DriverManager.getConnection(url, user, password);
        insertStatement = connection.prepareStatement("INSERT INTO reservations(customer_name, reservation_time, number_of_guests) VALUES (?, ?, ?);");
        deleteStatement = connection.prepareStatement("DELETE FROM reservations WHERE id = ?;");
    }

    @Override
    public void addReservation(String customerName, String reservationTime, int numberOfGuests) throws SQLException {
        insertStatement.setString(1, customerName);
        insertStatement.setTimestamp(2, Timestamp.valueOf(reservationTime));
        insertStatement.setInt(3, numberOfGuests);
        insertStatement.execute();
    }

    @Override
    public void deleteReservation(int reservationId) throws SQLException {
        deleteStatement.setInt(1, reservationId);
        deleteStatement.execute();
    }

    @Override
    public ResultSet listReservations() throws SQLException {
        String sql = "SELECT * FROM reservations;";
        Statement statement = connection.createStatement();
        return statement.executeQuery(sql);
    }

    public void clearReservations() throws SQLException {
        String sql = "DELETE FROM reservations;";
        Statement statement = connection.createStatement();
        statement.execute(sql);
    }

    public void closeConnection() {
        try {
            connection.close();
            deleteStatement.close();
            insertStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void close() {
        closeConnection();
    }
}
