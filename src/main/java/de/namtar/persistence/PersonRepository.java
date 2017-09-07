package de.namtar.persistence;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

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

    public PersonEntity findPersonByName(final String name) {

        final CriteriaBuilder cb = em.getCriteriaBuilder();
        final CriteriaQuery<PersonEntity> crit = cb.createQuery(PersonEntity.class);
        final Root<PersonEntity> from = crit.from(PersonEntity.class);
        crit.select(from);
        crit.where(cb.equal(from.get(PersonEntity_.name), name));

        final List<PersonEntity> result = em.createQuery(crit).getResultList();
        return result.isEmpty() ? null : result.get(0);
    }

    public EntityManager getEm() {
        return em;
    }
}
