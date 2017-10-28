package ro.utcn.sd.assign.one.start;

import java.util.ArrayList;
import java.util.HashSet;

import com.google.common.collect.Lists;

import ro.utcn.sd.assign.one.business.AdmBusiness;
import ro.utcn.sd.assign.one.business.impl.AdmBusinessImpl;
import ro.utcn.sd.assign.one.entities.Ct;
import ro.utcn.sd.assign.one.entities.Flgt;

public class TestMain {
	public static void main(String[] args) {
		final Ct newCt = new Ct("Viena");

		final AdmBusiness admBusines = new AdmBusinessImpl();
		final Flgt flgt = new Flgt();
		flgt.setAplnTp("x");
		flgt.setFlgtNb("z");
		final ArrayList<Ct> l = Lists.newArrayList();
		l.add(newCt);
		flgt.setCts(new HashSet<Ct>(l));
		admBusines.addNewFlgt(flgt);

		/*
		 * final Usr usr = new Usr(); usr.setId(1L); usr.setfNm("Concretepage"); final
		 * Session session = HibernateUtil.getSessionFactory().openSession();
		 * session.beginTransaction(); session.save(usr);
		 * session.getTransaction().commit(); session.close();
		 * System.out.println("Done");
		 */
	}
}