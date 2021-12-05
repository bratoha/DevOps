package ru.fivt.dostavimvse;

import org.hibernate.Session;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import ru.fivt.dostavimvse.models.Leg;
import ru.fivt.dostavimvse.models.Product;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class DostavimvseApplicationTests {

	@Test
	public void contextLoads() {
	}

	Session session;
	@Before
	public void createSession() {
		session = HibernateSessionFactory.getSessionFactory().openSession();
	}

	@Test
	public void checkProductNonNegativePriceCreated() {
//		Leg leg = new Leg();

        session.beginTransaction();
		Product product = new Product();
		product.setPrice(0.5);
		product.setWeight(0.8);


//		Integer id =
	}

}
