/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.clinigment.application.controller;

import com.calendarfx.model.Calendar;
import com.calendarfx.model.CalendarEvent;
import com.calendarfx.model.CalendarSource;
import com.calendarfx.model.Entry;
import com.calendarfx.view.CalendarView;
import com.calendarfx.view.page.DayPage;
import com.clinigment.application.abstracts.LayoutController;
import static java.lang.Thread.sleep;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.Pane;

/**
 *
 * @author C.I.T
 */
public class CalendarFXTest02Controller extends LayoutController implements Initializable {

    @Override
    public void setLayout(Node node) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setLayout(Node node, Pane container) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @FXML
    private CalendarView calendarView;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        /*
        CalendarView calendarView = new CalendarView();

        Calendar doctor00 = new Calendar("Doctor 00");
       
        doctor00.setShortName("K");
        
        doctor00.setStyle(Calendar.Style.STYLE1);
        
        CalendarSource familyCalendarSource = new CalendarSource("Doctors");
        
        familyCalendarSource.getCalendars().addAll(doctor00);

        calendarView.getCalendarSources().setAll(familyCalendarSource);

        calendarView.setRequestedTime(LocalTime.now());
         */

        //this.calendarView = new CalendarView();
        //calendarView.setShowLayoutButton(false);
        //calendarView.setShowAddCalendarButton(false);
        //calendarView.setShowPageToolBarControls(false);
        //calendarView.setShowPageSwitcher(false);
        //calendarView.setShowPageToolBarControls(true);

        //calendarView.getDayPage().setTooltip(calendarView.getDayPage().getTooltip());
        //calendarView.setShowLayoutButton(true);
        //calendarView.getDayPage().setDayPageLayout(DayPage.DayPageLayout.DAY_ONLY); //****** SETS DAY LAYOUT TO DAY ONLY *****
        //System.out.println(calendarView.getDayPage().getDayPageLayout());

        //System.out.println(dayPage);
        Calendar doctor1 = new Calendar("Doctor 1");
        Calendar doctor2 = new Calendar("Doctor 2");
        
        
        
        calendarView.showDayPage();
        calendarView.prefWidth(calendarView.computeAreaInScreen());
        calendarView.prefHeight(calendarView.computeAreaInScreen());

        
        
        //calendarView.setDefaultCalendarProvider(new DefaultCalendarProvider());

        EventHandler<CalendarEvent> handler = new EventHandler<CalendarEvent>() {
            @Override
            public void handle(CalendarEvent event) {
                System.out.println(event.toString());
            }
        };

        doctor1.addEventHandler(handler);

        doctor1.setStyle(Calendar.Style.STYLE1);
        doctor2.setStyle(Calendar.Style.STYLE2);
        
        System.out.println(doctor1.readOnlyProperty());
        doctor1.addEntry(new Entry<Object>());

        CalendarSource myCalendarSource = new CalendarSource("My Calendars");
        myCalendarSource.getCalendars().addAll(doctor1, doctor2);

        calendarView.getCalendarSources().addAll(myCalendarSource);

        calendarView.setRequestedTime(LocalTime.now());

        Thread updateTimeThread = new Thread("Calendar: Update Time Thread") {
            @Override
            public void run() {
                while (true) {
                    Platform.runLater(() -> {
                        calendarView.setToday(LocalDate.now());
                        calendarView.setTime(LocalTime.now());
                    });

                    try {
                        // update every 10 seconds
                        sleep(10000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            }
        ;
        };

        updateTimeThread.setPriority(Thread.MIN_PRIORITY);
        updateTimeThread.setDaemon(true);
        updateTimeThread.start();

    }

}
