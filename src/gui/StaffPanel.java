package gui;

import model.Calendar;
import model.User;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Panel class which manages the menus for a logged in Staff member.
 * @author Miguel Barreto
 * @version 2 Dec, 2016
 */
public class StaffPanel extends UserPanel  {

    /** Panel for the Auction Max */
    private JPanel maxPanel;

    /** Panel for the displayed calendars */
    private JPanel calendarPanel;

    /** Update-able label for the Auction Max within maxPanel */
    private JLabel maxLabel;

    /** Minimum number that the Auction Max can be set to. */
    private static final int AUCMAX_MIN = 1;

    /** Maximum number that the Auction Max can be set to. */
    private static final int AUCMAX_MAX = 999;

    /** The current Auction Max value */
    private int currentMax;

    /** Constructor for the panel */
    StaffPanel(User theUser, Calendar theCalendar) {
        myCalendar = theCalendar;
        myUser = theUser;
        this.setLayout(new BorderLayout());

        //Login Message
        JLabel loginLabel = new JLabel("Successfully logged in. Welcome " + myUser.getName()
                + ". Total number of upcoming auctions: " + myCalendar.getFutureAuctionTotal());
        add(loginLabel, BorderLayout.PAGE_START);

        //Calendar Table
        calendarPanel = new JPanel();
        calendarPanel.setLayout(new BoxLayout(calendarPanel, BoxLayout.Y_AXIS));
        setupCalendarPanel();
//        JPanel centerPanel = new JPanel(new BoxLayout());
//        centerPanel.add(calendarPanel);
//        add(centerPanel, BorderLayout.CENTER);
        add(calendarPanel, BorderLayout.CENTER);

        //Max Auctions Text and Spinner
        maxPanel = new JPanel();
        maxPanel.setLayout(new BoxLayout(maxPanel, BoxLayout.Y_AXIS));
        maxLabel = new JLabel("Set the maximum number of auctions allowed by the system. Currently: "
                + myCalendar.getAuctionMax());
        setupSpinnerPanel();
        add(maxPanel, BorderLayout.PAGE_END);
    }

    /** Sets up the content of the Calendar Panel */
    private void setupCalendarPanel() {
        String[] dayNames = { "Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
        String[][] dataThisMonth = new String[6][7];
        String[][] dataNextMonth = new String[6][7];
        LocalDateTime startTime = LocalDateTime.now().plusDays(1).withHour(0).withMinute(0);
        LocalDate startDate = startTime.toLocalDate();
        LocalDate endDate = startDate.plusMonths(1);
        JLabel thisMonth = new JLabel();
        JLabel nextMonth = new JLabel();
        thisMonth.setText(startDate.getMonth() + " - " + startDate.getYear());
        nextMonth.setText(endDate.getMonth() + " - " + endDate.getYear());
        int currMonth = startDate.getMonthValue();
        int offset, index;
        if(startDate.getDayOfMonth() > 1) {
            LocalDate startFirstMonth = startDate.minusDays(startDate.getDayOfMonth()-1);
            offset = alignCalendar(startFirstMonth);
            while(startFirstMonth.isBefore(startDate)) {
                index = startFirstMonth.getDayOfMonth()-1 + offset;
                dataThisMonth[index/7][index%7] = startFirstMonth.getDayOfMonth() + "";
                startFirstMonth = startFirstMonth.plusDays(1);
            }
        }
        else
            offset = alignCalendar(startDate);
        while(currMonth == startDate.getMonthValue()) {
            index = startDate.getDayOfMonth()-1 + offset;
            dataThisMonth[index/7][index%7] = startDate.getDayOfMonth() + " : "
                    + myCalendar.getAuctionTotalOnDay(startDate);
            startDate = startDate.plusDays(1);
        }
        offset = alignCalendar(startDate);
        currMonth = startDate.getMonthValue();
        while(startDate.isBefore(endDate)) {
            index = startDate.getDayOfMonth()-1 + offset;
            dataNextMonth[index/7][index%7] = startDate.getDayOfMonth() + " : "
                    + myCalendar.getAuctionTotalOnDay(startDate);
            startDate = startDate.plusDays(1);
        }
        while(currMonth == startDate.getMonthValue()) {
            index = startDate.getDayOfMonth()-1 + offset;
            dataNextMonth[index/7][index%7] = startDate.getDayOfMonth() + "";
            startDate = startDate.plusDays(1);
        }
        JTable calendarThisMonthTable = new JTable(dataThisMonth, dayNames);
        JTable calendarNextMonthTable = new JTable(dataNextMonth, dayNames);
        calendarPanel.add(thisMonth);
        calendarPanel.add(new JScrollPane(calendarThisMonthTable));
        calendarPanel.add(nextMonth);
        calendarPanel.add(new JScrollPane(calendarNextMonthTable));
//        calendarPanel.add(Box.createVerticalGlue());
        calendarPanel.setPreferredSize(new Dimension(100, 100));
    }

    /** Sets up the content of the Max Auction Panel */
    private void setupSpinnerPanel() {
        JPanel spinnerPanel = new JPanel();
        currentMax = myCalendar.getAuctionMax();
        SpinnerModel maxModel = new SpinnerNumberModel(currentMax, AUCMAX_MIN, AUCMAX_MAX, 1);
        JSpinner spinner = new JSpinner(maxModel);
        spinner.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                currentMax = (int)((JSpinner)e.getSource()).getValue();
                myCalendar.setAuctionMax(currentMax);
                maxLabel.setText("Set the maximum number of auctions allowed by the system. Currently: "
                        + myCalendar.getAuctionMax());
            }
        });
        spinnerPanel.add(spinner);
        maxPanel.add(maxLabel);
        maxPanel.add(spinnerPanel);
    }

    /**
     * Lets the calendar know how many days from
     *  the start of the week we are off by.
     * @return offset from Sunday
     */
    private int alignCalendar(LocalDate theDate) {
        if(theDate.getDayOfWeek() == DayOfWeek.MONDAY)
            return 1;
        else if(theDate.getDayOfWeek() == DayOfWeek.TUESDAY)
            return 2;
        else if(theDate.getDayOfWeek() == DayOfWeek.WEDNESDAY)
            return 3;
        else if(theDate.getDayOfWeek() == DayOfWeek.THURSDAY)
            return 4;
        else if(theDate.getDayOfWeek() == DayOfWeek.FRIDAY)
            return 5;
        else if(theDate.getDayOfWeek() == DayOfWeek.SATURDAY)
            return 6;
        else
            return 0;
    }

}
