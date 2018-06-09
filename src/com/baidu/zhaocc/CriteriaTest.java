package com.baidu.zhaocc;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

public class CriteriaTest {
	private static SessionFactory sessionFactory = null;

	public static void main(String[] args) {
		// creating configuration object
		Configuration cfg = new Configuration();
		cfg.configure("Employee.cfg.xml");// populates the data of the
											// configuration file

		// creating seession factory object
		sessionFactory = cfg.buildSessionFactory();
		insert(new Employee("zhao", "xinxin"));
		list();
		count();
		sum();
	}

	// HQL insert 只能把已有的数据重新拷贝一份
	public static void insert(Employee e) {
		Session session = sessionFactory.openSession();
		Transaction t = null;
		try {
			t = session.beginTransaction();
			Integer employeeId = (Integer) session.save(e);
			System.out.println("insert employeeId:" + employeeId);
			t.commit();
		} catch (HibernateException ex) {
			ex.printStackTrace();
			if (t != null) {
				t.rollback();
			}
		} finally {
			session.close();
		}
	}

	public static void list() {
		Session session = sessionFactory.openSession();
		Transaction t = null;
		try {
			t = session.beginTransaction();
			Criteria cr = session.createCriteria(Employee.class);
			cr.setFirstResult(1);
			cr.setMaxResults(3);
			cr.add(Restrictions.eq("firstName", "zhao"));
			List results = cr.list();
			System.out.println("list result:" + results.toString());
			t.commit();
		} catch (HibernateException ex) {
			ex.printStackTrace();
			if (t != null) {
				t.rollback();
			}
		} finally {
			session.close();
		}
	}

	public static void count() {
		Session session = sessionFactory.openSession();
		Transaction t = null;
		try {
			t = session.beginTransaction();
			Criteria cr = session.createCriteria(Employee.class);
			cr.setProjection(Projections.rowCount());
			List results = cr.list();
			System.out.println("count result:" + results.toString());
			t.commit();
		} catch (HibernateException ex) {
			ex.printStackTrace();
			if (t != null) {
				t.rollback();
			}
		} finally {
			session.close();
		}
	}

	public static void sum() {
		Session session = sessionFactory.openSession();
		Transaction t = null;
		try {
			t = session.beginTransaction();
			Criteria cr = session.createCriteria(Employee.class);
			cr.setProjection(Projections.sum("id"));
			List results = cr.list();
			System.out.println("sum	 result:" + results.toString());
			t.commit();
		} catch (HibernateException ex) {
			ex.printStackTrace();
			if (t != null) {
				t.rollback();
			}
		} finally {
			session.close();
		}
	}
}
