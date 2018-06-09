package com.baidu.zhaocc;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class HQLtest {
	private static SessionFactory sessionFactory = null;

	public static void main(String[] args) {
		//creating configuration object  
        Configuration cfg=new Configuration();  
        cfg.configure("Employee.cfg.xml");//populates the data of the configuration file  

        //creating seession factory object  
        sessionFactory = cfg.buildSessionFactory();
//        insert();
        list();
//        count();
//        update(new Employee("zhao", "xinxin"), 1);
        list();
//        count();
//        delete(5);
//        list();
//        count();
	}

	// HQL insert 只能把已有的数据重新拷贝一份
	public static void insert() {
		Session session = sessionFactory.openSession();
		Transaction t = session.beginTransaction();
		String hql = "INSERT INTO Employee (firstName, lastName)" + " SELECT e.firstName, e.lastName FROM Employee e WHERE e.id = 1";
		Query query = session.createQuery(hql);
		int result = query.executeUpdate();
		System.out.println("insert result:" + result);
		t.commit();
		session.close();
	}

	public static void list() {
		Session session = sessionFactory.openSession();
		String hql = "from Employee";
		Query query = session.createQuery(hql);
		query.setCacheable(true);
		List results = query.list();
		System.out.println("list result:" + results.toString());
		session.close();
	}
	
	public static void count() {
		Session session = sessionFactory.openSession();
		String hql = "select count(E.lastName) from Employee as E";
		Query query = session.createQuery(hql);
		List result = query.list();
		System.out.println("count result:" + result);
		session.close();
	}

	public static void update(Employee e, int id) {
		Session session = sessionFactory.openSession();
		Transaction t = session.beginTransaction();
		String hql = "update Employee set firstName = :firstName, lastName = :lastName where id = :id";
		Query query = session.createQuery(hql);
		query.setParameter("firstName", e.getFirstName());
		query.setParameter("lastName", e.getLastName());
		query.setParameter("id", id);
		int result = query.executeUpdate();
		System.out.print("update result:" + result);
		t.commit();
		session.close();
	}

	public static void delete(int id) {
		Session session = sessionFactory.openSession();
		Transaction t = session.beginTransaction();
		String hql = "delete from Employee where id = :id";
		Query query = session.createQuery(hql);
		query.setParameter("id", id);
		int result = query.executeUpdate();
		System.out.println("delete result:" + result);
		t.commit();
		session.close();
	}
}
