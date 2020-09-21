package com.o4codes.helpers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;

public class Utils {
    //app Theme
    public static ObservableList<String> appTheme = FXCollections.observableArrayList("Light","Dark");
    public static final ObservableList<String> PROJECT_STATUS = FXCollections.observableArrayList("Ongoing","Completed");

    // for numeric up down nodes
    public static void spinnerCount(Spinner<Integer> NumberBox, int maxNumber){
        NumberBox.setValueFactory(
                new SpinnerValueFactory.IntegerSpinnerValueFactory(1,maxNumber)
        );
    }

    public static void setSpinnerValue(Spinner<Integer> NumberBox, Integer newValue) {
        NumberBox.getValueFactory().setValue(newValue);

    }
}
