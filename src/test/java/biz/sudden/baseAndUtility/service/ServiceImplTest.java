package biz.sudden.baseAndUtility.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Repeat;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import biz.sudden.baseAndUtility.domain.Actor;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/biz/sudden/spring/testContext.xml" })
public class ServiceImplTest {

	private ActorService service;
	private Actor actor;

	/**
	 * @param repository
	 *            The repository to set.
	 */
	@Autowired
	public void setService(ActorService service) {
		this.service = service;
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		this.actor = new Actor();
		this.actor.setName("Thomas Feiner");
	}

	/**
	 * Test method for
	 * {@link at.jku.ce.dropbox.repository.hibernate.DropletRepositoryImpl#create(at.jku.ce.dropbox.domain.Droplet)}
	 * .
	 */
	@Test
	public void testCreate() {
		createAndRetrieveActors();
	}

	/**
	 * Test method for
	 * {@link at.jku.ce.dropbox.repository.hibernate.DropletRepositoryImpl#delete(at.jku.ce.dropbox.domain.Droplet)}
	 * .
	 */
	// @Test
	public void testDelete() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for
	 * {@link at.jku.ce.dropbox.repository.hibernate.DropletRepositoryImpl#retrieve(java.lang.Long)}
	 * .
	 */
	// @Test
	public void testRetrieve() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for
	 * {@link at.jku.ce.dropbox.repository.hibernate.DropletRepositoryImpl#retrieveAll()}
	 * .
	 */
	// @Test
	public void testRetrieveAll() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for
	 * {@link at.jku.ce.dropbox.repository.hibernate.DropletRepositoryImpl#update(at.jku.ce.dropbox.domain.Droplet)}
	 * .
	 */
	@Repeat(3)
	public void testUpdate() {
		fail("Not yet implemented");
	}

	/**
	 * creates and retrieves Actors
	 */
	public void createAndRetrieveActors() {

		try {
			Actor actor1 = service.createActor("Thomas Feiner");
			// Actor actor1 = service.createActor("Thomas Feiner2"); //retrieve
			// null
			Actor actor2 = service.retrieveActor("Thomas Feiner");
			System.out.println(actor1 + " sID " + actor2 + " sID");
			assertEquals("Actors not equal: created with id:" + actor1.getId()
					+ " & retrieved with id:" + actor2.getId(), actor1.getId(),
					actor2.getId());
			System.out.println(actor1.getName().equals(actor2.getName())
					+ " equal Names Actor 1 and 2, " + actor1.getName() + ", "
					+ actor2.getName());
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
	}

}
