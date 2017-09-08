/*
 * Copyright 2017 IQTIG – Institut für Qualitätssicherung und Transparenz im Gesundheitswesen.
 * Diese Code ist urheberrechtlich geschützt (Copyright). Das Urheberrecht liegt, soweit nicht ausdrücklich anders gekennzeichnet, beim IQTIG.
 * Wer gegen das Urheberrecht verstößt, macht sich gem. § 106 ff Urhebergesetz strafbar. Er wird zudem kostenpflichtig abgemahnt und muss
 * Schadensersatz leisten.
 */
package de.namtar.persistence;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Cat Entity
 *
 * @author namtar
 */
@Entity
@Table(name = "cat")
@Access(AccessType.FIELD)
public class CatEntity extends AbstractIdEntity {

	@Column(name = "name", columnDefinition = "VARCHAR(255)", nullable = false)
	public String name;

	@ManyToOne
	@JoinColumn(name = "person_id", columnDefinition = "INT")
	public PersonEntity owner;
}
