import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {


    public static void main(String[] args) throws ParseException {
        String dateStr = "2021-02-22T:12:01:02Z";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T':HH:mm:ss'Z'");
        Date date = simpleDateFormat.parse(dateStr);
        System.out.println();
    }

    /**
     *
     * @return
     */
    public static Date parse(String dateStr) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T':HH:mm:ss'Z'");
        Date date = simpleDateFormat.parse(dateStr);
        return date;
    }
}
