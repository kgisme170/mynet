import java.text.Collator;
import java.text.Normalizer;
import java.text.NumberFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class useNewTime {
    public static void main(String[] args){
        Instant start = Instant.now();
        Instant end = Instant.now();

        Duration d = Duration.between(start, end);
        long mils = d.toMillis();


        d.multipliedBy(10).minus(Duration.between(Instant.now(), Instant.now())).isNegative();
        LocalDate today = LocalDate.now();
        LocalDate birth = LocalDate.of(1999, 3, 3).plusDays(3);
        //.with(TemporalAdjuster.nextOrSame(DayOfWeek.TUESDAY));
        //.until(2, ChronoUnit.DAYS);
        ZonedDateTime zdt = ZonedDateTime.of(1999,9,9,9,9,9,0,ZoneId.of("America/New_York"));
        ZonedDateTime zdt2 = ZonedDateTime.of(
                LocalDate.of(1999,9,9),
                LocalTime.of(9,9,9),
                ZoneId.of("America/New_York")).plus(Period.ofDays(7));//day-light saving works. Don't use Duration.ofDays

        DateTimeFormatter fmt = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.LONG);
        String sf = fmt.format(zdt);

        for(DayOfWeek w : DayOfWeek.values()){
            System.out.println(w.getDisplayName(TextStyle.SHORT, Locale.ENGLISH) + " ");
        }
        fmt = DateTimeFormatter.ofPattern("E yyyy-MM-dd HH:mm");

        LocalDate church = LocalDate.parse("1999-09-09");

        LocalDateTime zdt3 = LocalDateTime.parse("1999-09-09 09:09:09.999",
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS"));

        Locale[] supportedLocals = NumberFormat.getAvailableLocales();
        Locale loc = Locale.GERMAN;
        NumberFormat curFmt = NumberFormat.getCurrencyInstance(loc);
        System.out.println(curFmt.format(1234567.8));

        Collator coll = Collator.getInstance(loc);
        List<String> words = new ArrayList();
        words.add("abc");
        words.add("xyz");
        words.add("123");
        words.sort(coll);
        System.out.println(words);
        String normalized = Normalizer.normalize("abc", Normalizer.Form.NFD);
        System.out.println(normalized);

    }
}
