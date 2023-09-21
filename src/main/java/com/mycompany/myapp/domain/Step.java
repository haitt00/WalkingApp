package com.mycompany.myapp.domain;


import java.time.LocalDate;

public class Step {
    private LocalDate date;
    private int count;

    // getters and setters

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int steps) {
        this.count = steps;
    }
}
