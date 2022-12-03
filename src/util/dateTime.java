package util;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class dateTime {
    /**
     * This method checks for the date
     * @return date
     */
    public static java.sql.Date getDate(){
        java.sql.Date date = java.sql.Date.valueOf(LocalDate.now());
        return date;
    }
    public static java.sql.Timestamp getTime() {
        ZoneId zoneId = ZoneId.of("UTC");
        LocalDateTime localDateTime = LocalDateTime.now(zoneId);
        java.sql.Timestamp timestamp = Timestamp.valueOf(localDateTime);
        return timestamp;
    }

}
