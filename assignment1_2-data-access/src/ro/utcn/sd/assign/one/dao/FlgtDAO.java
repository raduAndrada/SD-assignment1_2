package ro.utcn.sd.assign.one.dao;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import ro.utcn.sd.assign.one.configurations.HibernateUtil;
import ro.utcn.sd.assign.one.entities.Flgt;

public class FlgtDAO {

	public Flgt addFlgt(Flgt newFlgt) {
		final Session session = HibernateUtil.getSessionFactory().openSession();
		Long flgtId = -1L;

		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			flgtId = (Long) session.save(newFlgt);
			newFlgt.setId(flgtId);
			tx.commit();
		} catch (final HibernateException e) {
			if (tx != null) {
				tx.rollback();
			}
		} finally {
			session.close();
		}
		return newFlgt;
	}

	@SuppressWarnings("unchecked")
	public List<Flgt> findFlgts() {
		final Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = null;
		List<Flgt> flgts = null;
		try {
			tx = session.beginTransaction();
			flgts = session.createQuery("from Flgt").list();
			tx.commit();
		} catch (final HibernateException e) {
			if (tx != null) {
				tx.rollback();
			}
		} finally {
			session.close();
		}
		return flgts;
	}

	@SuppressWarnings("unchecked")
	public Flgt findFlgt(Long id) {
		final Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = null;
		List<Flgt> flgts = null;
		try {
			tx = session.beginTransaction();
			final Query query = session.createQuery("from Flgt where id = :id");
			query.setParameter("id", id);
			flgts = query.list();
			tx.commit();
		} catch (final HibernateException e) {
			if (tx != null) {
				tx.rollback();
			}
		} finally {
			session.close();
		}
		return flgts != null && !flgts.isEmpty() ? flgts.get(0) : null;
	}

	public Flgt deleteFlgt(Long id) {
		final Session session = HibernateUtil.getSessionFactory().openSession();
		final Flgt flgt = findFlgt(id);
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			session.delete(flgt);
			tx.commit();

		} catch (final HibernateException e) {
			if (tx != null) {
				tx.rollback();
			}
			return flgt;
		} finally {
			session.close();
		}
		return flgt;
	}

	public Flgt updateFlgt(Flgt update) {
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
