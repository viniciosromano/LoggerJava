package br.com.imagesoft.dateTimeNow;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.sql.Timestamp;

public class DateTimeGeneretor {
    private final LocalDateTime currentDateTime;
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");


    public DateTimeGeneretor() {
        this.currentDateTime = LocalDateTime.now(ZoneId.systemDefault());
    }

    public Timestamp getFormattedTimestamp() {
        return Timestamp.valueOf(FORMATTER.format(this.currentDateTime));
    }

    public Timestamp addFiveMinutes(Timestamp ts, int plusMinuts){
        LocalDateTime ldt = ts.toLocalDateTime();
        ldt = ldt.plusMinutes(plusMinuts);
        return Timestamp.valueOf(FORMATTER.format(ldt));
    }
    public String getDate() {
        return DATE_FORMATTER.format(this.currentDateTime);
    }
}