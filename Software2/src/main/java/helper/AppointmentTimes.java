package helper;

import dao.AppointmentQuery;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Appointments;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

/**
 * This class provides logic for other appointment classes
 */
public class AppointmentTimes extends TimezoneConversions {
    /**
     * This is an ObservableList of all appointments collected in a query from the database
     */
    private static List<Appointments> allAppts = AppointmentQuery.selectAllAppointments();

    /**
     * This is an ObservableList of all appointments by userID collected in a query from the database
     */
    private static ObservableList<Appointments> allApptsByUserId = FXCollections.observableArrayList();

    /**
     * This method checks all appointments for any overlaps and returns a boolean value
     * @param start the start date/time
     * @param end the end date/time
     * @return boolean value
     */
    public static boolean isApptOverlapping(LocalDateTime start, LocalDateTime end, long custId, long apptId) {
        ZonedDateTime s = toZonedLocalTimeConversion(start);
        ZonedDateTime e = toZonedLocalTimeConversion(end);
        for(Appointments a : allAppts) {
            if(custId != a.getCustomerID() || apptId == a.getApptID()) {
                return false;
            }
            ZonedDateTime s1 = toZonedLocalTimeConversion(a.getStartTime());
            ZonedDateTime e1 = toZonedLocalTimeConversion(a.getEndTime());

            if((s1.isAfter(s) || s1.isEqual(s)) && s1.isBefore(e)) {
                return true;
            }
            if(e1.isAfter(s) && (e1.isBefore(e) || e1.isEqual(e))) {
                return true;
            }
            if((s1.isBefore(s) || s1.isEqual(s)) && (e1.isAfter(e) || e1.isEqual(e))) {
                return true;
            }
        }
        return false;
    }

    /**
     * This method checks all the appointment's time value and compares it to the current time for any appointments
     * within the next 15 minutes and alerts the user
     * @param text the username
     */
    public static void upcomingAppt(String text, long userId) {
        try {
            allApptsByUserId = AppointmentQuery.selectAllApptsByUserId(userId);
        } catch(SQLException e) {
            e.printStackTrace();
        }
        for(Appointments a : allApptsByUserId) {
            LocalDate date = a.getStartTime().toLocalDate();
            if(!date.isEqual(LocalDate.now())) {
                new InformationAlert("There are no upcoming appointments.", "Welcome, " + text);
                return;
            }
            LocalTime start = toLocalTimeConversion(a.getStartTime());
            LocalTime now = getCurrentTime();
            long timeDifference = ChronoUnit.MINUTES.between(start, now);
            if(Math.abs(timeDifference) <= 15) {
                new InformationAlert("Attention: you have an upcoming appointment in the next 15 minutes of " +
                        "ID: " + a.getApptID() +
                        " on " +
                        date +
                        " at " +
                        start,
                         "Upcoming Appointment" + "(Welcome, " + text + ")");
            }
            else {
                new InformationAlert("There are no upcoming appointments.", "Welcome, " + text);
            }
            return;
        }
    }

}
