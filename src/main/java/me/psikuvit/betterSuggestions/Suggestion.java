package me.psikuvit.betterSuggestions;

import java.util.Date;
import java.util.UUID;

public class Suggestion {

    private final String suggestion;
    private final UUID suggester;
    private final Date date;
    private final int id;

    public Suggestion(String suggestion, UUID suggester, Date date, int id) {
        this.suggestion = suggestion;
        this.suggester = suggester;
        this.date = date;
        this.id = id;
    }

    public String getSuggestion() {
        return suggestion;
    }

    public UUID getSuggester() {
        return suggester;
    }

    public Date getDate() {
        return date;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return  "suggestion='" + suggestion + '\'' +
                ", suggester=" + suggester +
                ", date=" + date +
                ", id=" + id +
                '}';
    }
}
