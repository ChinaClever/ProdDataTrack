package cn.iocoder.yudao.module.system.util.dateUtil;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.Map;

public class DateUtils {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static LocalDate parseDate(String dateStr) {
        try {
            return LocalDate.parse(dateStr, DATE_FORMATTER);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Invalid date format: " + dateStr, e);
        }
    }

    /**
     * 获取本月的起始和结束日期，并返回字符串形式
     * @return Map 包含 "start" 和 "end" 两个键，值为字符串形式的日期
     */
    public static Map<String, String> getStartAndEndOfMonth() {
        YearMonth currentMonth = YearMonth.now();
        LocalDateTime startOfMonth = currentMonth.atDay(1).atStartOfDay();
        LocalDateTime endOfMonth = currentMonth.atEndOfMonth().atTime(23, 59, 59);

        Map<String, String> dates = new HashMap<>();
        dates.put("start", startOfMonth.format(DATE_FORMATTER));
        dates.put("end", endOfMonth.format(DATE_FORMATTER));

        return dates;
    }

    /**
     * 获取本年的起始和结束日期，并返回字符串形式
     * @return Map 包含 "start" 和 "end" 两个键，值为字符串形式的日期
     */
    public static Map<String, String> getStartAndEndOfYear() {
        Year currentYear = Year.now();
        LocalDateTime startOfYear = currentYear.atDay(1).atStartOfDay();
        LocalDateTime endOfYear = currentYear.atMonthDay(MonthDay.of(12, 31)).atTime(23, 59, 59);

        Map<String, String> dates = new HashMap<>();
        dates.put("start", startOfYear.format(DATE_FORMATTER));
        dates.put("end", endOfYear.format(DATE_FORMATTER));

        return dates;
    }

    /**
     * 获取上年的起始和结束日期，并返回字符串形式
     * @return Map 包含 "start" 和 "end" 两个键，值为字符串形式的日期
     */
    public static Map<String, String> getStartAndEndOfLastYear() {
        Year currentYear = Year.now();
        Year lastYear = currentYear.minusYears(1);
        LocalDateTime startOfLastYear = lastYear.atDay(1).atStartOfDay();
        LocalDateTime endOfLastYear = lastYear.atMonthDay(MonthDay.of(12, 31)).atTime(23, 59, 59);

        Map<String, String> dates = new HashMap<>();
        dates.put("start", startOfLastYear.format(DATE_FORMATTER));
        dates.put("end", endOfLastYear.format(DATE_FORMATTER));

        return dates;
    }

}
