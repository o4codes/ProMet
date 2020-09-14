package com.o4codes.models;

import java.time.LocalDate;
import java.time.LocalTime;

public class Task {
    private String taskId, projectId, title, description;
    private LocalDate beginDate, deadlineDate, completionDate;
    private LocalTime beginTime, deadlineTime, completionTime;
    private int duration;
    private boolean mileStone;

    public Task(String taskId, String projectId, String title, String description, int duration, LocalDate beginDate, LocalTime beginTime, LocalDate deadlineDate, LocalTime deadlineTime, LocalDate completionDate, LocalTime completionTime, boolean mileStone) {
        this.taskId = taskId;
        this.projectId = projectId;
        this.title = title;
        this.description = description;
        this.duration = duration;
        this.beginDate = beginDate;
        this.beginTime = beginTime;
        this.deadlineDate = deadlineDate;
        this.deadlineTime = deadlineTime;
        this.completionDate = completionDate;
        this.completionTime = completionTime;
        this.mileStone = mileStone;
    }

    public Task(String projectId, String title, String description, int duration, LocalDate beginDate, LocalTime beginTime, LocalDate deadlineDate, LocalTime deadlineTime, boolean milestone ) {
        this.projectId = projectId;
        this.title = title;
        this.description = description;
        this.duration = duration;
        this.beginDate = beginDate;
        this.beginTime = beginTime;
        this.deadlineDate = deadlineDate;
        this.deadlineTime = deadlineTime;
        this.mileStone = milestone;
    }

    public Task() {
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
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

    public LocalDate getDeadlineDate() {
        return deadlineDate;
    }

    public void setDeadlineDate(LocalDate deadlineDate) {
        this.deadlineDate = deadlineDate;
    }

    public LocalDate getCompletionDate() {
        return completionDate;
    }

    public void setCompletionDate(LocalDate completionDate) {
        this.completionDate = completionDate;
    }

    public LocalTime getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(LocalTime beginTime) {
        this.beginTime = beginTime;
    }

    public LocalTime getDeadlineTime() {
        return deadlineTime;
    }

    public void setDeadlineTime(LocalTime deadlineTime) {
        this.deadlineTime = deadlineTime;
    }

    public LocalTime getCompletionTime() {
        return completionTime;
    }

    public void setCompletionTime(LocalTime completionTime) {
        this.completionTime = completionTime;
    }

    public boolean isMileStone() {
        return mileStone;
    }

    public void setMileStone(boolean mileStone) {
        this.mileStone = mileStone;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }


}
