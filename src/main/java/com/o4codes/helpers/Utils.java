package com.o4codes.helpers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.util.Duration;

public class Utils {
    //app Theme
    public static ObservableList<String> appTheme = FXCollections.observableArrayList( "Light", "Dark" );
    public static final ObservableList<String> PROJECT_STATUS = FXCollections.observableArrayList( "Ongoing", "Completed" );

    // for numeric up down nodes
    public static void spinnerCount(Spinner<Integer> NumberBox, int maxNumber) {
        NumberBox.setValueFactory(
                new SpinnerValueFactory.IntegerSpinnerValueFactory( 1, maxNumber )
        );
    }

    public static void setSpinnerValue(Spinner<Integer> NumberBox, Integer newValue) {
        NumberBox.getValueFactory().setValue( newValue );

    }

    public static String getTimeLeftInMinutes(int seconds) {
        String durationMinutes;
        //duration is in seconds
        int minutes = (int) javafx.util.Duration.seconds( seconds ).toMinutes();
        //check if there are remaining minutes
        if (javafx.util.Duration.minutes( minutes ).toSeconds() == seconds) {
            durationMinutes = minutes + "mins";
        } else {
            int remaining_seconds = (int) (seconds - Duration.minutes( minutes ).toSeconds());
            durationMinutes = minutes + " m " + remaining_seconds + " s";
        }
        return durationMinutes;
    }



}
