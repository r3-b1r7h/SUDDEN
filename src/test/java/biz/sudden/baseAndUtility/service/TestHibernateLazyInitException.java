package biz.sudden.baseAndUtility.service;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import biz.sudden.designCoordination.collaborativePlanning.domain.Topic;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/biz/sudden/spring/rootContext.xml" })
public class TestHibernateLazyInitException {

	@Autowired
	private SessionFactory sessionFactory;

	@Test
	public void testFirst() {
		Session s = sessionFactory.openSession();
		Transaction t = s.beginTransaction();

		Topic topic = (Topic) s.createCriteria(Topic.class).list().get(1);

		Topic topic2 = (Topic) s.createCriteria(Topic.class).list().get(1);

		System.out.println(topic + " : " + topic2);

		Topic meinTopic = new Topic();
		meinTopic.setName("HALLO");

		s.saveOrUpdate(meinTopic);

		t.commit();

		s.close();

		Session s1 = sessionFactory.openSession();

		Transaction t1 = s1.beginTransaction();

		s1.update(topic2);

		s1.close();

		System.out.println("!!!!!!!! " + topic2.getCommunications());

		t1.commit();

		s1.close();

	}

}
