package iview.rental.domain;

import iview.rental.IdGenerator;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class AbstractEntity implements Entity {
    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    private final Long id;

    public AbstractEntity() {
        this.id = IdGenerator.getNextId();
    }

    @Override
    public Long getId() {
        return id;
    }
}
