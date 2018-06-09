package com.baidu.zhaocc;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.stat.Statistics;

public class SecondLevelCacheTest {
	private static SessionFactory sessionFactory = null;

	public static void main(String[] args) {
		// creating configuration object
		Configuration cfg = new Configuration();
		cfg.configure("Employee.cfg.xml");// populates the data of the
											// configuration file

		// creating seession factory object
		sessionFactory = cfg.buildSessionFactory();
		list();
		final Statistics s = sessionFactory.getStatistics();  
        System.out.println(s); //打印所有信息 监测SessionFactory  
        System.out.println("---------------");  
        System.out.println("放入:" + s.getSecondLevelCachePutCount()); //打印缓存的信息  
        System.out.println("命中:" + s.getSecondLevelCacheHitCount());  
        System.out.println("丢失:" + s.getSecondLevelCacheMissCount());  
	}

	// HQL insert 只能把已有的数据重新拷贝一份
	public static void insert() {
		Session session = sessionFactory.openSession();
		Transaction t = session.beginTransaction();
		String hql = "INSERT INTO Employee (firstName, lastName)"
				+ " SELECT e.firstName, e.lastName FROM Employee e WHERE e.id = 1";
		Query query = session.createQuery(hql);
		int result = query.executeUpdate();
		System.out.println("insert result:" + result);
		t.commit();
		session.close();
	}

	public static void list() {
		for (int i = 0; i < 10; i++) {
			Session session = sessionFactory.openSession();
			String hql = "from Employee where id = 1";
			Query query = session.createQuery(hql);
			query.setCacheable(true);
			List results = query.list();
			System.out.println("list result:" + results.toString());
			session.close();
		}
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
