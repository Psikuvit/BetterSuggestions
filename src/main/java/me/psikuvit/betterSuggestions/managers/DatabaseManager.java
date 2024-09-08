package me.psikuvit.betterSuggestions.managers;

import me.psikuvit.betterSuggestions.BSPlugin;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseManager {

    private Connection connection;

    public void connect(BSPlugin plugin) throws SQLException {
        File dataFolder = plugin.getDataFolder();
        if (!dataFolder.exists()) {
            dataFolder.mkdirs();
        }

        String url = "jdbc:sqlite:" + dataFolder + "/suggestions.db";
        connection = DriverManager.getConnection(url);

        createTable();
    }

    private void createTable() throws SQLException {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS suggestions ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "suggestion TEXT NOT NULL, "
                + "suggester VARCHAR(36) NOT NULL, "
                + "date TIMESTAMP NOT NULL"
                + ");";

        try (Statement stmt = connection.createStatement()) {
            stmt.execute(createTableSQL);
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public void disconnect() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }
}
