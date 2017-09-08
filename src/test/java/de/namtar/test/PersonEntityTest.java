package de.namtar.test;

import de.namtar.persistence.AbstractIdEntity;
import de.namtar.persistence.AbstractIdEntity_;
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

import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * Test for the {@link de.namtar.persistence.PersonEntity}.
 *
 * @author namtar on 06.09.2017.
 */
@RunWith(Arquillian.class)
@Stateless
public class PersonEntityTest {

    @Inject
    private PersonRepository personRepository;

    @Deployment
    public static WebArchive createArchive() {

        final WebArchive archive = ShrinkWrap.create(WebArchive.class, "test.war");
        archive.addClasses(PersonEntity.class, PersonRepository.class, PersonEntity_.class, AbstractIdEntity.class, AbstractIdEntity_.class);
        archive.addAsResource("META-INF/test-persistence.xml", "META-INF/persistence.xml");
        archive.addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
        // Deploy our test datasource
        archive.addAsWebInfResource("test-ds.xml");

        return archive;
    }

    @Test
    public void testCreatePerson() {

        // https://www.javacodegeeks.com/2014/06/java-ee7-and-maven-project-for-newbies-part-5-unit-testing-using-arquillian-wildfly-8.html
        PersonEntity entity = new PersonEntity();
        entity.name = "TestName";
        entity.firstName = "FirstName";

        PersonEntity createdEntity = personRepository.createPerson(entity);

        Assert.assertNotNull(createdEntity.id);
        Assert.assertNotNull(createdEntity.creationDate);

        // detach all entities
        personRepository.getEm().clear();

        // found entity is a new object. Debug to see the object id difference between foundEntity and createdEntity
        final PersonEntity foundEntity = personRepository.findPersonByName("TestName");
        Assert.assertNotNull(foundEntity);
        Assert.assertNotNull(createdEntity.id);
        Assert.assertNotNull(createdEntity.creationDate);
        Assert.assertEquals("TestName", createdEntity.name);
        Assert.assertEquals("FirstName", createdEntity.firstName);
    }
}
