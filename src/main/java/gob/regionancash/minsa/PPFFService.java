package gob.regionancash.minsa;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;

import io.agroal.api.AgroalDataSource;
import io.quarkus.agroal.DataSource;

import org.isobit.util.XUtil;

import gob.regionancash.minsa.jpa.PPFF;
import gob.regionancash.minsa.jpa.Vaccine;

@Transactional
@ApplicationScoped
public class PPFFService {

	@Inject
	@DataSource("minsa")
	AgroalDataSource defaultDataSource;

	public Object edit(PPFF complaint) {
		try {
			EntityManager em = complaint.getEntityManager();
			if (complaint.getId() == null) {
				complaint.setNumDocumento(complaint.getNumDocumento().trim());
				HashMap m = new HashMap();
				m.put("numDoc", complaint.getNumDocumento());
				List l = (List) load(0, 1, null, m);
				System.out.println("size=" + l.size());
				if (!l.isEmpty())
					throw new RuntimeException(
							"El num documento " + complaint.getNumDocumento() + " ya esta registrado!");
				em.persist(complaint);
			} else {
				// complaint.setUpdatedDate(new Date());
				em.merge(complaint);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		return complaint;
	}

	public Object get(Object id) {
		EntityManager em = new PPFF().getEntityManager();
		PPFF vaccine = em.find(PPFF.class, id);
		return vaccine;
	}

	public Object load(int first, int pageSize, Object object, Map filters) {
		List<Query> ql = new ArrayList();
		String sql;
		EntityManager em = new Vaccine().getEntityManager();
		Object numDoc = XUtil.isEmpty(filters.get("numDoc"), null);
		Object datos = XUtil.isEmpty(filters.get("datos"), null);
		Object segundaDosis = XUtil.isEmpty(filters.get("segundaDosis"), null);
		Object numCelular = XUtil.isEmpty(filters.get("numCelular"), null);
		Object lugarVacunacion = XUtil.isEmpty(filters.get("lugarVacunacion"), null);
		Object red = XUtil.isEmpty(filters.get("red"), null);

		ql.add(em.createQuery("SELECT o " + (sql = " FROM PPFF o WHERE 1=1 "
				+ (numDoc != null ? " AND TRIM(UPPER(o.numDocumento)) like :numDoc" : "")
				+ (segundaDosis != null ? " AND UPPER(o.segundaDosis) like :segundaDosis" : "")
				+ (datos != null
						? " AND UPPER(CONCAT(o.paterno,' ',o.materno,' ',o.primerNombre,' ',o.segundoNombre)) like :datos"
						: "")
				+ (numCelular != null ? " AND UPPER(o.numCelular) like :numCelular" : "")
				+ (red != null ? " AND UPPER(o.red) like :red" : "")
				+ (lugarVacunacion != null ? " AND UPPER(o.lugarVacunacion) like :lugarVacunacion" : "")) +

				" ORDER BY o.id DESC"));
		if (pageSize <1) {
			pageSize=20;
		}
		ql.get(0).setFirstResult(first).setMaxResults(pageSize);
		ql.add(em.createQuery("SELECT COUNT(o) " + sql));
		
		for (Query q : ql) {
			if (numDoc != null) {
				q.setParameter("numDoc", "%" + numDoc.toString().trim().toUpperCase().replace("\\s+", "%") + "%");
			}
			if (segundaDosis != null) {
				q.setParameter("segundaDosis", "%" + segundaDosis.toString().toUpperCase().replace("\\s+", "%") + "%");
			}
			if (red != null) {
				q.setParameter("red", "%" + red.toString().toUpperCase().replace("\\s+", "%") + "%");
			}
			if (numCelular != null) {
				q.setParameter("numCelular", "%" + numCelular.toString().toUpperCase().replace("\\s+", "%") + "%");
			}
			if (lugarVacunacion != null) {
				q.setParameter("lugarVacunacion",
						"%" + lugarVacunacion.toString().toUpperCase().replace("\\s+", "%") + "%");
			}
			if (datos != null) {
				q.setParameter("datos", "%" + datos.toString().toUpperCase().replace("\\s+", "%") + "%");
			}
		}
		if (pageSize > 0) {
			filters.put("size", ql.get(1).getSingleResult());
		}
		List<PPFF> l = ql.get(0).getResultList();
		return l;
	}

	public Object download(Map m) {
		Connection con = null;
		try {
			con = defaultDataSource.getConnection();
			String net=(String) m.get("net");
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT TOP 10000 * FROM BDHIS_MINSA.dbo.REPORT_PPFF_EXCELS WHERE 1=1 "+
					(net!=null?(" AND (descripcion_red='"+net+"'"+(net.startsWith("NO ")?" OR descripcion_red IS NULL":"")+")"):"")
					);
			ResultSetMetaData rsmd = rs.getMetaData();
			int i = rsmd.getColumnCount();
			List lc = new ArrayList();
			Object row[] = new Object[i];
			for (int j = 0; j < i; j++) {
				row[j] = new Object[] { rsmd.getColumnName(j + 1), 100 };
			}
			lc.add(row);
			while (rs.next()) {
				row = new Object[i];
				for (int j = 0; j < i; j++) {
					row[j] = rs.getObject(j + 1);
				}
				lc.add(row);
			}
			return lc;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (con != null)
				try {
					con.close();
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			System.out.println("close");
		}
		return null;
	}

	public void destroy(Integer id) {
		EntityManager em = new PPFF().getEntityManager();
		PPFF v = em.find(PPFF.class, id);
		em.remove(v);
	}

}
