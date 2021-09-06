package iview.rental.domain;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
public class Owner extends Person {

    @OneToMany(mappedBy = "owner")
    private List<Apartment> apartments;
}
