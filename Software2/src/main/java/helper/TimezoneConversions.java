package helper;

import java.time.*;

/**
 * This class provides logic for Timezone conversions used throughout the project
 */

public class TimezoneConversions {
    /**
     * Organization start time
     */
    public static final LocalTime companyStartTime = LocalTime.of(7, 59);
    /**
     * Organization end time
     */
    public static final LocalTime companyEndTime = LocalTime.of(22,1);
    /**
     * LocalTime value used for populating available times
     */
    public static final LocalTime beginningTime = LocalTime.of(0,0);
    /**
     * LocalTime value used for populating available times
     */
    public static final LocalTime endingTime = LocalTime.of(23,59);
    /**
     * variable that holds the system zone Id
     */
    public static ZoneId sysDefZoneId = ZoneId.systemDefault();
    /**
     * variable that holds the zone Id of the eastern time zone, which is what the organization is in
     */
    public static ZoneId estZoneId = ZoneId.of("America/New_York");

    /**
     * This method takes in a LocalDateTime object, converts it into est, and returns a boolean value if it lies
     * between the organization's start and end times
     * @param myLDT localdatetime entered
     * @return boolean value if time is within company hours
     */
    public static boolean estConversion(LocalDateTime myLDT) {
        ZonedDateTime zdt = myLDT.atZone(sysDefZoneId);
        ZonedDateTime myzdt = zdt.withZoneSameInstant(estZoneId);
        LocalDateTime ldt = myzdt.toLocalDateTime();
        LocalDateTime companyStartLDT = LocalDateTime.of(ldt.toLocalDate(), companyStartTime);
        LocalDateTime companyEndLDT = LocalDateTime.of(ldt.toLocalDate(), companyEndTime);
        return ldt.isAfter(companyStartLDT) || !ldt.isBefore(companyEndLDT);
    }

    /**
     * This method takes in a LocalDateTime object and converts it into a zoneddatetime object on the system's zone Id
     * for use in other helper methods
     * @param zdt the localdatetime
     * @return the zoned date time converted
     */
    public static ZonedDateTime toZonedLocalTimeConversion(LocalDateTime zdt) {
        ZonedDateTime myZDT = ZonedDateTime.of(zdt, sysDefZoneId);
        return myZDT;
    }

    /**
     * This method takes in a LocalDateTime object, converts it into a zoned date time object on the system's zone Id,
     * and returns a LocalTime object version of it
     * @param ldt the localdatetime
     * @return the converted localtime
     */
    public static LocalTime toLocalTimeConversion(LocalDateTime ldt) {
        ZonedDateTime myZDT = ZonedDateTime.of(ldt, sysDefZoneId);
        return myZDT.toLocalTime();
    }

    /**
     * This method returns the current LocalTime according to the system's zone Id
     * @return localtime in system's zone Id
     */
    public static LocalTime getCurrentTime() {
        return LocalTime.now(sysDefZoneId);
    }

}
