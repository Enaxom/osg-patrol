package com.app.osgrim.data;

public class BilanCir implements Bilan {

    private String date, time;
    private String fname, lname, age;
    private int gender;
    private State state;
    private String address, circumstance;
    private Transport transport;
    private Destination destination;
    private int discharge;

    public BilanCir(String date, String time) {
        this.date = date;
        this.time = time;
        gender = 0;
        discharge = 0;
    }

    @Override
    public BilanCir getBilanCir() {
        return this;
    }

    @Override
    public BilanFonc getBilanFonc() {
        return null;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCircumstance() {
        return circumstance;
    }

    public void setCircumstance(String circumstance) {
        this.circumstance = circumstance;
    }

    public Transport getTransport() {
        return transport;
    }

    public void setTransport(Transport transport) {
        this.transport = transport;
    }

    public Destination getDestination() {
        return destination;
    }

    public void setDestination(Destination destination) {
        this.destination = destination;
    }

    public int getDischarge() {
        return discharge;
    }

    public void setDischarge(int discharge) {
        this.discharge = discharge;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDateTime() {
        return date + " à " + time;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
