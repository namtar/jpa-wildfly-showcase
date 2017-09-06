package de.namtar.persistence;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Repository for {@link PersonEntity} access.
 *
 * @author namtar on 06.09.2017.
 */
@Stateless
public class PersonRepository {

    @PersistenceContext
    private EntityManager em;

    public PersonEntity createPerson(final PersonEntity entity) {

        em.persist(entity);
        return entity;
    }
}
