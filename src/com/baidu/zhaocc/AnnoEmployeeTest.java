package com.baidu.zhaocc;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class AnnoEmployeeTest {

	public static void main(String[] args) {
		// creating configuration object
		Configuration cfg = new Configuration();
		cfg.configure("AnnoEmployee.cfg.xml");// populates the data of the
												// configuration file

		// creating seession factory object
		SessionFactory sessionFactory = cfg.buildSessionFactory();

		Session session = sessionFactory.openSession();
		Transaction t = session.beginTransaction();
		AnnoEmployee annoEmployee = new AnnoEmployee();
		annoEmployee.setFirstName("chaochao");
		annoEmployee.setLastName("zhao");
		session.persist(annoEmployee);

		t.commit();
		session.close();
		System.out.println("Store successfully");
	}

}
