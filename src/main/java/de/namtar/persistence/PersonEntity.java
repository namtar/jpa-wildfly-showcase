package de.namtar.persistence;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * A person entity.
 *
 * @author namtar on 06.09.2017.
 */
@Entity
@Table(name = "person")
@Access(AccessType.FIELD)
public class PersonEntity extends AbstractIdEntity {

    @Column(name = "creation_date")
    public LocalDateTime creationDate;

    @Column(name = "name", columnDefinition = "VARCHAR(255)", nullable = false)
    public String name;
    @Column(name = "first_name", columnDefinition = "VARCHAR(255)", nullable = false)
    public String firstName;

    @PrePersist
    private void prePersist() {
        creationDate = LocalDateTime.now();
    }

}
