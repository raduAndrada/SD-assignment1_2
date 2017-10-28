package ro.utcn.sd.assign.one.dao;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import ro.utcn.sd.assign.one.configurations.HibernateUtil;
import ro.utcn.sd.assign.one.entities.Ct;

public class CtDAO {

	public Ct addCt(Ct newCt) {
		final Session session = HibernateUtil.getSessionFactory().openSession();

		Long ctId = -1L;

		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			ctId = (Long) session.save(newCt);
			newCt.setId(ctId);
			tx.commit();
		} catch (final HibernateException e) {
			if (tx != null) {
				tx.rollback();
			}
		} finally {
			session.close();
		}
		return newCt;
	}

	@SuppressWarnings("unchecked")
	public List<Ct> findCts() {
		final Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = null;
		List<Ct> cts = null;
		try {
			tx = session.beginTransaction();
			cts = session.createQuery("from Ct").list();
			tx.commit();
		} catch (final HibernateException e) {
			if (tx != null) {
				tx.rollback();
			}
		} finally {
			session.close();
		}
		return cts;
	}

	@SuppressWarnings("unchecked")
	public List<Ct> findCtByFlgtId(Long flgtId) {
		final Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = null;
		List<Ct> cts = null;
		try {
			tx = session.beginTransaction();
			final Query query = session.createQuery("from Ct where FLGT_ID = :flgtId");
			query.setParameter("flgtId", flgtId);
			cts = query.list();
			tx.commit();
		} catch (final HibernateException e) {
			if (tx != null) {
				tx.rollback();
			}
		} finally {
			session.close();
		}
		return cts;
	}

	public Ct deleteCtByFlgtId(Long flgtId) {
		final Session session = HibernateUtil.getSessionFactory().openSession();
		final Ct ct = findCtByFlgtId(flgtId).stream().findFirst().orElse(new Ct());
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			session.delete(ct);
			tx.commit();

		} catch (final HibernateException e) {
			if (tx != null) {
				tx.rollback();
			}
			return ct;
		} finally {
			session.close();
		}
		return ct;
	}

}
