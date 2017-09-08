/*
 * Copyright 2017 IQTIG – Institut für Qualitätssicherung und Transparenz im Gesundheitswesen.
 * Diese Code ist urheberrechtlich geschützt (Copyright). Das Urheberrecht liegt, soweit nicht ausdrücklich anders gekennzeichnet, beim IQTIG.
 * Wer gegen das Urheberrecht verstößt, macht sich gem. § 106 ff Urhebergesetz strafbar. Er wird zudem kostenpflichtig abgemahnt und muss
 * Schadensersatz leisten.
 */
package de.namtar.test;

import de.namtar.persistence.AbstractIdEntity;
import de.namtar.persistence.AbstractIdEntity_;
import de.namtar.persistence.CatEntity;
import de.namtar.persistence.CatEntity_;
import de.namtar.persistence.CatRepository;
import de.namtar.persistence.PersonEntity;
import de.namtar.persistence.PersonEntity_;
import de.namtar.persistence.PersonRepository;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;

/**
 * Tests for {@link de.namtar.persistence.CatEntity}.
 *
 * @author namtar
 */
@RunWith(Arquillian.class)
public class CatEntityTest {

	@Inject
	private CatRepository catRepository;

	@Inject
	private PersonRepository personRepository;

	@Deployment
	public static WebArchive createArchive() {

		final WebArchive archive = ShrinkWrap.create(WebArchive.class, "test.war");
		archive.addClasses(PersonEntity.class, PersonRepository.class, PersonEntity_.class, AbstractIdEntity.class, AbstractIdEntity_.class,
				CatEntity.class, CatEntity_.class, CatRepository.class);
		archive.addAsResource("META-INF/test-persistence.xml", "META-INF/persistence.xml");
		archive.addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
		// Deploy our test datasource
		archive.addAsWebInfResource("test-ds.xml");

		return archive;
	}

	@Test
	public void testCreateCat() {

		final PersonEntity owner = createPerson();

		final CatEntity catEntity = new CatEntity();
		catEntity.name = "Katze";
		catEntity.owner = owner;

		final CatEntity createdCat = catRepository.createCat(catEntity);

		Assert.assertNotNull(createdCat);

		catRepository.getEm().clear();

		final CatEntity loadedFromDbCat = catRepository.findByName("Katze");
		Assert.assertNotNull(loadedFromDbCat);
		Assert.assertNotNull(loadedFromDbCat.id);
		Assert.assertNotNull(loadedFromDbCat.version);
		Assert.assertEquals("Katze", loadedFromDbCat.name);
		Assert.assertEquals(owner.name, loadedFromDbCat.owner.name);
	}

	private PersonEntity createPerson() {
		final PersonEntity personEntity = new PersonEntity();
		personEntity.name = "TestPerson";
		personEntity.firstName = "FirstNameOfTestPerson";

		return personRepository.createPerson(personEntity);
	}

}
