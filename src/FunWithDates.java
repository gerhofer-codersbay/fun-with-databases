import java.text.SimpleDateFormat;
import java.time.*;
import java.util.Calendar;
import java.util.Date;

public class FunWithDates {


    public static void main(String[] args) {

        // in lokaler Zeitzone
        Date date = new Date();
        System.out.println(date);

        // einfach zu speichern
        // Datum ist als einzelne Zahl repräsentiert
        // in UTC
        Instant instant = Instant.now();
        System.out.println(instant);

        // nur Datum, ohne Zeit von aktueller Zeitzone
        // keine Zeitzone wird gespeichert
        LocalDate localDate = LocalDate.of(2017, 1, 1);
        System.out.println(localDate);

        // mit Zeit von aktueller Zeitzone
        // keine Zeitzone wird gespeichert
        LocalDateTime localDateTime = LocalDateTime.of(localDate, LocalTime.of(13, 54));
        System.out.println(localDateTime);

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.YYYY hh:mm");
        System.out.println(dateFormat.format(date));
        System.out.println(dateFormat.format(Date.from(localDate.atStartOfDay().toInstant(ZoneOffset.ofHours(2)))));
        System.out.println(dateFormat.format(Date.from(instant)));
        System.out.println(dateFormat.format(Date.from(localDateTime.toInstant(ZoneOffset.UTC))));

    }

}
