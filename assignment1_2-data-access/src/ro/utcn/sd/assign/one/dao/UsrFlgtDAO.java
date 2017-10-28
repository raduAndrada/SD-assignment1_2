package ro.utcn.sd.assign.one.dao;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import ro.utcn.sd.assign.one.configurations.HibernateUtil;
import ro.utcn.sd.assign.one.entities.UsrFlgt;

/**
 * connection table dao --- table flgt and usr
 *
 * @author Andrada
 *
 */
public class UsrFlgtDAO {

	/**
	 * add a new flgt for a usr
	 *
	 * @param newUsrFlgt
	 *            the object to be added in the db
	 * @return the inserted object
	 */
	public UsrFlgt addUsrFlgt(UsrFlgt newUsrFlgt) {

		Long usrFlgtId = -1L;
		final Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			usrFlgtId = (Long) session.save(newUsrFlgt);
			newUsrFlgt.setId(usrFlgtId);
			tx.commit();
		} catch (final HibernateException e) {
			if (tx != null) {
				tx.rollback();
			}
		} finally {
			session.close();
		}
		return newUsrFlgt;
	}

	/**
	 * fing usr's flgts by his/her id
	 *
	 * @param usrId
	 *            uniqued identifier in the db
	 * @return a list with all the flgts for the requested usr
	 */
	@SuppressWarnings("unchecked")
	public List<UsrFlgt> findUsrFlgtsByUsrId(Long usrId) {
		final Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = null;
		List<UsrFlgt> usrFlgts = null;
		try {
			tx = session.beginTransaction();
			usrFlgts = session.createQuery("from UsrFlgt where usrId=: usrId").setParameter("usrId", usrId).list();
			tx.commit();
		} catch (final HibernateException e) {
			if (tx != null) {
				tx.rollback();
			}
		} finally {
			session.close();
		}
		return usrFlgts;
	}

	/**
	 * find usrs in a flgt by the flgt's id
	 *
	 * @param flgtId
	 *            the id of the flgt
	 * @return a list with usr's ids attending to that flgt
	 */
	@SuppressWarnings("unchecked")
	public List<UsrFlgt> findUsrFlgtsByFlgtId(Long flgtId) {
		final Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = null;
		List<UsrFlgt> usrFlgts = null;
		try {
			tx = session.beginTransaction();
			usrFlgts = session.createQuery("from UsrFlgt where flgtId=: flgtId").setParameter("flgtId", flgtId).list();
			tx.commit();
		} catch (final HibernateException e) {
			if (tx != null) {
				tx.rollback();
			}
		} finally {
			session.close();
		}
		return usrFlgts;
	}

	/**
	 * delete a flgt using usr's id and the flgt's id
	 *
	 * @param usrId
	 *            usr's identifier in the db
	 * @param flgtId
	 *            flgt's unique identifier
	 * @return the deleted object
	 */

	public UsrFlgt deleteUsrFlgtByUsrIdAndFlgtId(Long usrId, Long flgtId) {
		final Session session = HibernateUtil.getSessionFactory().openSession();
		final UsrFlgt usrFlgt = findUsrFlgtsByUsrId(usrId).stream().findFirst().get();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			session.delete(usrFlgt);
			tx.commit();

		} catch (final HibernateException e) {
			if (tx != null) {
				tx.rollback();
			}
			return usrFlgt;
		} finally {
			session.close();
		}
		return usrFlgt;
	}

	/**
	 * method to delete a flgt by its id
	 *
	 * @param flgtId
	 *            the flgt that will be deleted's id
	 * @return the deletion result
	 */

	public UsrFlgt deleteUsrFlgtByFlgtId(Long flgtId) {
		final Session session = HibernateUtil.getSessionFactory().openSession();
		final UsrFlgt usrFlgt = findUsrFlgtsByFlgtId(flgtId).stream().findFirst().get();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			session.delete(usrFlgt);
			tx.commit();

		} catch (final HibernateException e) {
			if (tx != null) {
				tx.rollback();
			}
			return usrFlgt;
		} finally {
			session.close();
		}
		return usrFlgt;
	}
}
