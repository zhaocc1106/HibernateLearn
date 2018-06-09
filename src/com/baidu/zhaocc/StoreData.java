package com.baidu.zhaocc;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StoreData {
	static final Logger logger = LoggerFactory.getLogger(StoreData.class);  
	
	public static void main(String[] args) {
		//creating configuration object  
        Configuration cfg=new Configuration();  
        cfg.configure("Employee.cfg.xml");//populates the data of the configuration file  

        //creating seession factory object  
        SessionFactory factory=cfg.buildSessionFactory();

        //creating session object  
        Session session=factory.openSession();  

        //creating transaction object  
        Transaction t=session.beginTransaction();  

        Employee e1=new Employee();
        e1.setFirstName("Max");
        e1.setLastName("Su");

        session.persist(e1);//persisting the object  

        t.commit();//transaction is committed  
        session.close();  

        System.out.println("successfully saved");
        logger.debug("successfully saved");

	}

}
