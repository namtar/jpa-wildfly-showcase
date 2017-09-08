/*
 * Copyright 2017 IQTIG – Institut für Qualitätssicherung und Transparenz im Gesundheitswesen.
 * Diese Code ist urheberrechtlich geschützt (Copyright). Das Urheberrecht liegt, soweit nicht ausdrücklich anders gekennzeichnet, beim IQTIG.
 * Wer gegen das Urheberrecht verstößt, macht sich gem. § 106 ff Urhebergesetz strafbar. Er wird zudem kostenpflichtig abgemahnt und muss
 * Schadensersatz leisten.
 */
package de.namtar.persistence;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * Repository to manage cats.
 *
 * @author namtar
 */
@Stateless
public class CatRepository {

	@PersistenceContext
	private EntityManager em;

	public CatEntity findByName(final String name) {
		final CriteriaBuilder cb = em.getCriteriaBuilder();
		final CriteriaQuery<CatEntity> crit = cb.createQuery(CatEntity.class);
		final Root<CatEntity> from = crit.from(CatEntity.class);
		crit.select(from);
		crit.where(cb.equal(from.get(CatEntity_.name), name));
		final List<CatEntity> result = em.createQuery(crit).getResultList();

		return result.isEmpty() ? null : result.get(0);
	}

	public CatEntity createCat(final CatEntity catEntity) {
		em.persist(catEntity);
		return catEntity;
	}

	public EntityManager getEm() {
		return em;
	}
}
