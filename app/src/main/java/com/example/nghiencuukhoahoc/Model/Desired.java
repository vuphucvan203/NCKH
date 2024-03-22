package com.example.nghiencuukhoahoc.Model;

public class Desired {
    private Rooms livingRoom;
    private Rooms bedRoom;
    private Rooms kitchen;

    public Desired(Rooms livingRoom, Rooms bedRoom,Rooms kitchen) {
        this.livingRoom = livingRoom;
        this.bedRoom = bedRoom;
        this.kitchen=kitchen;
    }

    public Rooms getKitchen() {
        return kitchen;
    }

    public void setKitchen(Rooms kitchen) {
        this.kitchen = kitchen;
    }

    public Desired() {
    }

    public Rooms getLivingRoom() {
        return livingRoom;
    }

    public void setLivingRoom(Rooms livingRoom) {
        this.livingRoom = livingRoom;
    }

    public Rooms getBedRoom() {
        return bedRoom;
    }

    public void setBedRoom(Rooms bedRoom) {
        this.bedRoom = bedRoom;
    }
}
