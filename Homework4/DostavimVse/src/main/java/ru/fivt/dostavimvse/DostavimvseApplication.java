package ru.fivt.dostavimvse;

import org.hibernate.SessionFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DostavimvseApplication {

	public static void main(String[] args) {
		final SessionFactory factory = HibernateSessionFactory.getSessionFactory(); // open sessions and create factory
		Operator operator = Operator.getInstance();
		SpringApplication.run(DostavimvseApplication.class, args);
	}
}
