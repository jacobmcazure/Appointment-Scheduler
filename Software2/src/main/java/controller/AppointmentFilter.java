package controller;

import dao.AppointmentQuery;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Appointments;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;

/**
 * This class contains logic for filtering appointments in the view appointments list form
 */
public abstract class AppointmentFilter extends AppointmentQuery {
    /**
     * This is an ObservableList of Appointments that collects all appointment data from a query to the database
     */
    public static ObservableList<Appointments> aList = selectAllAppointments();

    /**
     * This method sorts through all appointments using a lambda expression to find appointments that match the
     * current month and returns them in an ObservableList
     * @return the filtered list of matching appointments by month
     */
    public ObservableList<Appointments> selectMonthly() {
        ObservableList<Appointments> filteredList = FXCollections.observableArrayList();
        //returns filtered list if not empty to save time and memory
        if(!filteredList.isEmpty()) return filteredList;
        aList.stream()
                .filter(appointments -> YearMonth.from(appointments.getStartTime()).equals(YearMonth.now()))
                .forEach(filteredList::add);
        return filteredList;
    }

    /**
     * This method finds the start of the current week by using the current date
     * @return monday
     */
    public static LocalDate getStartofWeek() {
        LocalDate today = LocalDate.now();
        LocalDate monday = today;
        while(monday.getDayOfWeek() != DayOfWeek.MONDAY) {
            monday = monday.minusDays(1);
        }
        return monday;
    }

    /**
     * This method finds the end of the current week by using the current date
     * @return sunday
     */
    public static LocalDate getEndofWeek() {
        LocalDate today = LocalDate.now();
        LocalDate sunday = today;
        while (sunday.getDayOfWeek() != DayOfWeek.SUNDAY) {
            sunday = sunday.plusDays(1);
        }
        return sunday;
    }

    /**
     * This method sorts through all appointments using a lambda expression to filter results and the start and
     * end of week methods to find appointments that match the current week and returns them in an ObservableList
     * @return the filtered list of matching appointments by week
     */
    public ObservableList<Appointments> selectWeekly() {
        ObservableList<Appointments> fl = FXCollections.observableArrayList();
        //returns filtered list if not empty to save time and memory
        if(!fl.isEmpty()) return fl;
        aList.stream()
                .filter(ap -> (ap.getStartTime().toLocalDate().isAfter(getStartofWeek()) ||
                        ap.getStartTime().toLocalDate().isEqual(getStartofWeek())) &&
                        (ap.getStartTime().toLocalDate().isBefore(getEndofWeek()) ||
                                ap.getStartTime().toLocalDate().isEqual(getEndofWeek())))
                .forEach(fl::add);
        return fl;
    }

}

