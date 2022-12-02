package gob.regionancash.minsa;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.Column;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;

import io.agroal.api.AgroalDataSource;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.isobit.util.XUtil;

import gob.regionancash.minsa.jpa.Covid;
import gob.regionancash.minsa.jpa.TRegistroDiario;
import gob.regionancash.minsa.jpa.Vaccine;
import gob.regionancash.minsa.jpa.VaccineCOVID;


@Transactional
@ApplicationScoped
public class TRegistroDiarioService {
	
	@Inject
	AgroalDataSource defaultDataSource;
	
	public Object edit(TRegistroDiario complaint){
		try {
			EntityManager em=complaint.getEntityManager();
			if(complaint.getId()==null) {
				/*complaint.setNumDocumento(complaint.getNumDocumento().trim());
				HashMap m=new HashMap();
				m.put("numDoc",complaint.getNumDocumento());
				List l=(List)load(0,1,null,m);
				System.out.println("size="+l.size());
				if(!l.isEmpty()) throw new RuntimeException("El num documento "+complaint.getNumDocumento()+" ya esta registrado!");
				*/em.persist(complaint);
			}else {
				//complaint.setUpdatedDate(new Date());
				em.merge(complaint);
			}
		}catch(Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		return complaint;
	}
	
	public Object get(Object id){
		EntityManager em=new TRegistroDiario().getEntityManager();
		TRegistroDiario vaccine=em.find(TRegistroDiario.class,id);
		return vaccine;
	}
	
	public Object load(int first, int pageSize, Object object, Map filters) {
        List<Query> ql = new ArrayList();
        String sql;
        EntityManager em = new Vaccine().getEntityManager();
        Object dniNotificante = XUtil.isEmpty(filters.get("dniNotificante"), null);
        Object datos = XUtil.isEmpty(filters.get("datos"), null);
        Object segundaDosis = XUtil.isEmpty(filters.get("segundaDosis"), null);
        Object numCelular = XUtil.isEmpty(filters.get("numCelular"), null);
        Object lugarVacunacion = XUtil.isEmpty(filters.get("lugarVacunacion"), null);
        Object red = XUtil.isEmpty(filters.get("red"), null);

        ql.add(em.createQuery("SELECT o " + (sql = " FROM TRegistroDiario o WHERE 1=1 "
        		+ (dniNotificante != null ? " AND TRIM(UPPER(o.dniNotificante)) like :dniNotificante" : "")
        		+ (segundaDosis != null ? " AND UPPER(o.segundaDosis) like :segundaDosis" : "")
        		+ (datos != null ? " AND UPPER(CONCAT(o.paterno,' ',o.materno,' ',o.primerNombre,' ',o.segundoNombre)) like :datos" : "")
        		+ (numCelular != null ? " AND UPPER(o.numCelular) like :numCelular" : "")
        		+ (red != null ? " AND UPPER(o.red) like :red" : "")
        		+ (lugarVacunacion != null ? " AND UPPER(o.lugarVacunacion) like :lugarVacunacion" : "")
        		)+
        		
        		" ORDER BY o.id DESC"));
        if (pageSize > 0) {
            ql.get(0).setFirstResult(first).setMaxResults(pageSize);
            ql.add(em.createQuery("SELECT COUNT(o) " + sql));
        }
        for (Query q : ql) {
        	if (dniNotificante != null) {
                q.setParameter("dniNotificante", "%" + dniNotificante.toString().trim().toUpperCase().replace("\\s+", "%") + "%");
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
                q.setParameter("lugarVacunacion", "%" + lugarVacunacion.toString().toUpperCase().replace("\\s+", "%") + "%");
            }
        	if (datos != null) {
                q.setParameter("datos", "%" + datos.toString().toUpperCase().replace("\\s+", "%") + "%");
            }
        }
        if (pageSize > 0) {
            filters.put("size", ql.get(1).getSingleResult());
        }
        List<TRegistroDiario> l = ql.get(0).getResultList();
        return l;
	}

	public void destroy(Integer id) {
		// TODO Auto-generated method stub
		EntityManager em = new TRegistroDiario().getEntityManager();
		TRegistroDiario v=em.find(TRegistroDiario.class,id);
		em.remove(v);
	}

}
