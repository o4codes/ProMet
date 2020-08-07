package com.o4codes.models;

public class AppConfiguration {
    private int maxProjects, taskDuration, shortBreakDuration, longBreakDuration;
    private String appTheme;

    public AppConfiguration(int maxProjects, int taskDuration, int shortBreakDuration, int longBreakDuration, String appTheme) {
        this.maxProjects = maxProjects;
        this.taskDuration = taskDuration;
        this.shortBreakDuration = shortBreakDuration;
        this.longBreakDuration = longBreakDuration;
        this.appTheme = appTheme;
    }

    public AppConfiguration(){

    }

    public int getMaxProjects() {
        return maxProjects;
    }

    public void setMaxProjects(int maxProjects) {
        this.maxProjects = maxProjects;
    }

    public int getTaskDuration() {
        return taskDuration;
    }

    public void setTaskDuration(int taskDuration) {
        this.taskDuration = taskDuration;
    }

    public int getShortBreakDuration() {
        return shortBreakDuration;
    }

    public void setShortBreakDuration(int shortBreakDuration) {
        this.shortBreakDuration = shortBreakDuration;
    }

    public int getLongBreakDuration() {
        return longBreakDuration;
    }

    public void setLongBreakDuration(int longBreakDuration) {
        this.longBreakDuration = longBreakDuration;
    }

    public String getAppTheme() {
        return appTheme;
    }

    public void setAppTheme(String appTheme) {
        this.appTheme = appTheme;
    }
}
