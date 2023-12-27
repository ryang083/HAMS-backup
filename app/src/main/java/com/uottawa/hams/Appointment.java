package com.uottawa.hams;

public class Appointment {

    // this class represents a single appointment class
    private int id;

    private String firstName;
    private String lastName;
    private String date;
    private String startTime;
    private String endTime;
    private String status;

    public Appointment(int id,String firstName, String lastName, String date, String startTime, String endTime, String status) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    // Getters
    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getDate() {
        return date;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

}
