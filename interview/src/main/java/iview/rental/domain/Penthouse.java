package iview.rental.domain;

import javax.persistence.Entity;

@Entity
public class Penthouse extends Apartment {
    private int floorNumber;

    public int getFloorNumber() {
        return floorNumber;
    }

    public void setFloorNumber(int floorNumber) {
        this.floorNumber = floorNumber;
    }
}
