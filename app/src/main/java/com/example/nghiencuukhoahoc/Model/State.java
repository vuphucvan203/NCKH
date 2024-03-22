package com.example.nghiencuukhoahoc.Model;

public class State {
    Desired desired;
    Reported reported;
    public State(Desired desired) {
        this.desired = desired;
    }

    public State() {
    }

    public Reported getReported() {
        return reported;
    }

    public void setReported(Reported reported) {
        this.reported = reported;
    }

    public Desired getDesired() {
        return desired;
    }

    public void setDesired(Desired desired) {
        this.desired = desired;
    }
}
