package com.o4codes.models;

import java.time.LocalDate;

public class Project {
    private String id, colorTheme, title, description;
    private LocalDate beginDate, dueDate, completionDate;

    // to get objects with id
    public Project(String id, String colorTheme, String title, String description, LocalDate beginDate, LocalDate dueDate, LocalDate completionDate) {
        this.id = id;
        this.colorTheme = colorTheme;
        this.title = title;
        this.description = description;
        this.beginDate = beginDate;
        this.dueDate = dueDate;
        this.completionDate = completionDate;
    }

    // to insert objects without id
    public Project(String colorTheme, String title, String description, LocalDate beginDate, LocalDate dueDate) {
        this.colorTheme = colorTheme;
        this.title = title;
        this.description = description;
        this.beginDate = beginDate;
        this.dueDate = dueDate;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getColorTheme() {
        return colorTheme;
    }

    public void setColorTheme(String colorTheme) {
        this.colorTheme = colorTheme;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(LocalDate beginDate) {
        this.beginDate = beginDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public LocalDate getCompletionDate() {
        return completionDate;
    }

    public void setCompletionDate(LocalDate completionDate) {
        this.completionDate = completionDate;
    }
}
