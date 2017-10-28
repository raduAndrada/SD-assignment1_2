package ro.utcn.sd.assign.one.dao;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import ro.utcn.sd.assign.one.configurations.HibernateUtil;
import ro.utcn.sd.assign.one.entities.Usr;

/**
 * DAO for the usr table --- methods: get, add, update, delete
 *
 * @author Andrada
 *
 */
public class UsrDAO {

	/**
	 * add a new usr
	 *
	 * @param newUsr
	 *            the usr to be added in the db
	 * @return the inserted usr if, operation was successful
	 */
	public Usr addUsr(Usr newUsr) {

		Long usrId = -1L;
		final Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			usrId = (Long) session.save(newUsr);
			newUsr.setId(usrId);
			tx.commit();
		} catch (final HibernateException e) {
			if (tx != null) {
				tx.rollback();
			}
		} finally {
			session.close();
		}
		return newUsr;
	}

	/**
	 * get the list of usrs
	 *
	 * @return a list with all the usrs in the table
	 */
	@SuppressWarnings("unchecked")
	public List<Usr> findUsrs() {
		final Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = null;
		List<Usr> usrs = null;
		try {
			tx = session.beginTransaction();
			usrs = session.createQuery("from Usr").list();
			tx.commit();
		} catch (final HibernateException e) {
			if (tx != null) {
				tx.rollback();
			}
		} finally {
			session.close();
		}
		return usrs;
	}

	/**
	 * find usr by email --- email is unique in the db
	 *
	 * @param usrEmail
	 *            the usr's email
	 * @return the usr with the corresponding email
	 */
	@SuppressWarnings("unchecked")
	public Usr findUsr(String usrEmail) {
		final Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = null;
		List<Usr> usrs = null;
		try {
			tx = session.beginTransaction();
			final Query query = session.createQuery("from Usr where email = :usrEmail");
			query.setParameter("usrEmail", usrEmail);
			usrs = query.list();
			tx.commit();
		} catch (final HibernateException e) {
			if (tx != null) {
				tx.rollback();

			}
			e.printStackTrace();
		} finally {
			session.close();
		}
		return usrs != null && !usrs.isEmpty() ? usrs.get(0) : null;
	}

	/**
	 * delete usr by email
	 *
	 * @param usrEmail
	 *            usr's email
	 * @return the deleted usr
	 */
	public Usr deleteUsr(String usrEmail) {
		final Session session = HibernateUtil.getSessionFactory().openSession();
		final Usr usr = findUsr(usrEmail);
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			session.delete(usr);
			tx.commit();

		} catch (final HibernateException e) {
			if (tx != null) {
				tx.rollback();
			}
			return usr;
		} finally {
			session.close();
		}
		return usr;
	}

	/**
	 * update usr by email
	 *
	 * @param update
	 *            usr's email
	 * @return the deleted usr
	 */
	public Usr updateUsr(Usr update) {
		final Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			session.update(update);
			tx.commit();

		} catch (final HibernateException e) {
			if (tx != null) {
				tx.rollback();
			}
			return update;
		} finally {
			session.close();
		}
		return update;
	}
}
