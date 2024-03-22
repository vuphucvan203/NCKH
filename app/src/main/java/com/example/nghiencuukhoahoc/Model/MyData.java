package com.example.nghiencuukhoahoc.Model;

public class MyData {
    private State state;

    public MyData( State state) {
        this.state = state;
    }

    public MyData() {
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }
}
