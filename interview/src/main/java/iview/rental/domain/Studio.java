package iview.rental.domain;

import javax.persistence.Entity;

@Entity
public class Studio extends Apartment {
    private String category;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
