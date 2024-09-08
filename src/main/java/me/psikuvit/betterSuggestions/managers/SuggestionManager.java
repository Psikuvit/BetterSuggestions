package me.psikuvit.betterSuggestions.managers;


import me.psikuvit.betterSuggestions.Suggestion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class SuggestionManager {

    private final Set<Suggestion> suggestions;
    private final Connection connection;

    public SuggestionManager(Connection connection) {
        this.suggestions = new HashSet<>();
        this.connection = connection;
    }

    public void addNewSuggestion(String suggestionText, UUID suggester) {
        int nextId = getNextId(); // Get the next incrementing ID
        Suggestion suggestion = new Suggestion(suggestionText, suggester, new Date(), nextId);
        suggestions.add(suggestion);
    }

    public Suggestion getSuggestionByID(int id) {
        for (Suggestion suggestion : suggestions) {
            if (suggestion.getId() == id) return suggestion;
        }
        return null;
    }

    public boolean isRightID(int id) {
        return id <= suggestions.size();
    }

    public void saveSuggestions() throws SQLException {
        String query = "INSERT INTO suggestions (suggestion, suggester, date) VALUES (?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            for (Suggestion suggestion : suggestions) {
                ps.setString(1, suggestion.getSuggestion());
                ps.setString(2, suggestion.getSuggester().toString());
                ps.setTimestamp(3, new Timestamp(suggestion.getDate().getTime()));
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }

    public void loadSuggestions() throws SQLException {
        suggestions.clear();
        String query = "SELECT id, suggestion, suggester, date FROM suggestions";
        try (PreparedStatement ps = connection.prepareStatement(query); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {

                int id = rs.getInt("id");
                String suggestionText = rs.getString("suggestion");
                UUID suggester = UUID.fromString(rs.getString("suggester"));
                Date date = new Date(rs.getTimestamp("date").getTime());

                suggestions.add(new Suggestion(suggestionText, suggester, date, id));
            }
        }
    }

    public int getNextId() {
        return suggestions.size() + 1;
    }
}