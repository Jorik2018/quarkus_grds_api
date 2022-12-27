package gob.regionancash.minsa;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;

import io.agroal.api.AgroalDataSource;
import io.quarkus.agroal.DataSource;

import org.isobit.app.jpa.User;
import org.isobit.util.XUtil;

import gob.regionancash.grds.mssql.jpa.Disabled;
import gob.regionancash.minsa.jpa.TCertDisc;
import gob.regionancash.minsa.jpa.Vaccine;
import gob.regionancash.minsa.jpa.VaccineCOVID;

@Transactional
@ApplicationScoped
public class CovidService {
	
	@Inject
	@DataSource("minsa")
	AgroalDataSource defaultDataSource;
	
	public Object edit(Vaccine complaint){
		EntityManager em=complaint.getEntityManager();
		if(complaint.getId()==null) {
			complaint.setNumDoc(complaint.getNumDoc().trim());
			HashMap m=new HashMap();
			m.put("numDoc",complaint.getNumDoc());
			List l=(List)load(0,1,null,m);
			if(!l.isEmpty()) throw new RuntimeException("El numero documento "+complaint.getNumDoc()+" ya esta registrado!");
			em.persist(complaint);
		}else {
			complaint.setUpdatedDate(new Date());
			em.merge(complaint);
		}
		return complaint;
	}
	
	public Object getVaccine(Object id){
		EntityManager em=new Vaccine().getEntityManager();
		Vaccine vaccine=em.find(Vaccine.class,id);
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
        
        ql.add(em.createQuery("SELECT o " + (sql = " FROM Vaccine o WHERE 1=1 "
        		+ (numDoc != null ? " AND TRIM(UPPER(o.numDoc)) like :numDoc" : "")
        		+ (segundaDosis != null ? " AND UPPER(o.segundaDosis) like :segundaDosis" : "")
        		+ (datos != null ? " AND UPPER(o.datos) like :datos" : "")
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
                q.setParameter("lugarVacunacion", "%" + lugarVacunacion.toString().toUpperCase().replace("\\s+", "%") + "%");
            }
        	if (datos != null) {
                q.setParameter("datos", "%" + datos.toString().toUpperCase().replace("\\s+", "%") + "%");
            }
        }
        if (pageSize > 0) {
            filters.put("size", ql.get(1).getSingleResult());
        }
        List<Vaccine> l = ql.get(0).getResultList();
        return l;
	}

	public void destroy(Integer id) {
		// TODO Auto-generated method stub
		EntityManager em = new Vaccine().getEntityManager();
		Vaccine v=em.find(Vaccine.class,id);
		em.remove(v);
	}

	public Object loadVaccineCOVID(Integer first, int pageSize, Object object, Map filters) {
		List l = new ArrayList();
		Object numDoc = XUtil.isEmpty(filters.get("numDoc"), null);
		Object query = XUtil.isEmpty(filters.get("query"), null);
		if(query!=null)numDoc=query;
		if(numDoc==null||numDoc.toString().trim().length()<8) throw new RuntimeException("numDoc no valido");
		Connection con = null;
		try {
			con = defaultDataSource.getConnection();
			Statement stmt = con.createStatement();
			ResultSet rs = stmt
					.executeQuery("SELECT TOP 20 * FROM BDHIS_MINSA.dbo.T_CONSULTA_COVID19 WHERE num_doc LIKE '%"+numDoc+"%'");
			while(rs.next()) {
				VaccineCOVID covid=new VaccineCOVID();
				Date date=rs.getDate("fecha_vacunacion");
				if(date!=null)
					covid.setFechaVacunacion(new Date(date.getTime()).toInstant()
						      .atZone(ZoneId.systemDefault())
						      .toLocalDate());
				covid.setPaciente(rs.getString("paciente"));
	            covid.setDosisAplicada(rs.getString("dosis_aplicada"));
	            covid.setNumDoc(rs.getString("num_doc"));
	            covid.setFabricanteAbrev(rs.getString("Fabricante"));
	            covid.setBirthdate(rs.getString("fecha_nacimiento"));
	            covid.setGrupo(rs.getString("gruporiesgo"));
	            covid.setProvinciaResidencia(rs.getString("provincia_residencia"));
	            covid.setDistritoResidencia(rs.getString("distrito_residencia"));
	            covid.setVacuna(rs.getString("vacuna"));
	            covid.setAbrevTipoDoc(rs.getString("abrev_tipo_doc"));
	            covid.setEstablecimiento(rs.getString("establecimiento"));
				l.add(covid);
			}
		} catch(Exception e){
			e.printStackTrace();
		} finally {
			if (con != null)
				try {
					con.close();
				}catch(Exception e) {
					throw new RuntimeException(e);
				}
			System.out.println("close");
		}
		return l;
	}

	public Object loadDisabledCertificate(Integer from, Integer to, Object object, Map filters) {
		List l = new ArrayList();
		Object numDoc = XUtil.isEmpty(filters.get("numDoc"), null);
		Object query = XUtil.isEmpty(filters.get("query"), null);
		if(query!=null)numDoc=query;
		//if(numDoc==null||numDoc.toString().trim().length()<8) throw new RuntimeException("numDoc no valido");
		Connection con = null;
		try {
			con = defaultDataSource.getConnection();
			Statement stmt = con.createStatement();
			ResultSet rs = stmt
					.executeQuery("SELECT TOP 20 * FROM BDHIS_MINSA.dbo.T_CERT_DISC WHERE \"Número documento\" LIKE '%"+numDoc+"%' AND \"¿Es anulado?\"='NO' ORDER BY \"Fecha de emisión\" DESC");
			while(rs.next()) {
				TCertDisc tCertDisc=new TCertDisc();
				tCertDisc.setId(rs.getString("Número de certificado discapacidad"));
				tCertDisc.setTipoDocumento(rs.getString("Tipo documento"));
				tCertDisc.setNumeroDocumento(rs.getString("Número documento"));
				tCertDisc.setNombresApellidos(rs.getString("Nombres y apellidos"));
				tCertDisc.setSexo(rs.getString("sexo"));
				tCertDisc.setEdad(rs.getString("edad"));
				tCertDisc.setPaisNacimiento(rs.getString("País nacimiento"));
				tCertDisc.setEtnia(rs.getString("etnia"));
				tCertDisc.setHC(rs.getString("H.C."));
				tCertDisc.setFechaEmision(rs.getString("Fecha de emisión"));
				tCertDisc.setReevaluacionMeses(rs.getString("Reevaluación en meses"));
				tCertDisc.setVigencia(rs.getString("vigencia"));
				tCertDisc.setCodRENAES(rs.getString("Cod. RENAES"));
				tCertDisc.setEsAnulado(rs.getString("¿Es anulado?"));
				tCertDisc.setSector(rs.getString("sector"));
				tCertDisc.setEstablecimiento(rs.getString("establecimiento"));
				tCertDisc.setDepartamento(rs.getString("departamento"));
				tCertDisc.setProvincia(rs.getString("provincia"));
				tCertDisc.setDistrito(rs.getString("distrito"));
				tCertDisc.setMedicoCertificador(rs.getString("Médico certificador"));
				tCertDisc.setNroDocumentoMedicoCertificador(rs.getString("Nro documento (médico certificador)"));
				tCertDisc.setDiagnosticoDanio1(rs.getString("Diagnóstico de daño 1"));
				tCertDisc.setDiagnosticoDanio2(rs.getString("Diagnóstico de daño 2"));
				tCertDisc.setDiagnosticoEtiologico1(rs.getString("Diagnóstico etiológico 1"));
				tCertDisc.setDiagnosticoEtiologico2(rs.getString("Diagnóstico etiológico 2"));
				tCertDisc.setConducta(rs.getString("De la conducta"));
				tCertDisc.setComunicacion(rs.getString("De la comunicación"));
				tCertDisc.setCuidadoPersonal(rs.getString("Del cuidado personal"));
				tCertDisc.setLocomocion(rs.getString("De la locomoción"));
				tCertDisc.setDisposicionCorporal(rs.getString("De la disposición corporal"));
				tCertDisc.setDestreza(rs.getString("De la destreza"));
				tCertDisc.setSituacion(rs.getString("De la situación"));
				tCertDisc.setGravedad(rs.getString("gravedad"));
				tCertDisc.setRestriccionParticipacion(rs.getString("Restricción de la participación"));
				tCertDisc.setRequerimientoProductosApoyo(rs.getString("Requerimiento de productos de apoyo"));
				/*Date date=rs.getDate("fecha_vacunacion");
				if(date!=null)
					
					covid.setFechaVacunacion(new Date(date.getTime()).toInstant()
						      .atZone(ZoneId.systemDefault())
						      .toLocalDate());
				covid.setPaciente(rs.getString("paciente"));
	            covid.setDosisAplicada(rs.getString("dosis_aplicada"));
	            covid.setNumDoc(rs.getString("num_doc"));
	            covid.setFabricanteAbrev(rs.getString("Fabricante"));
	            covid.setBirthdate(rs.getString("fecha_nacimiento"));
	            covid.setGrupo(rs.getString("gruporiesgo"));
	            covid.setProvinciaResidencia(rs.getString("provincia_residencia"));
	            covid.setDistritoResidencia(rs.getString("distrito_residencia"));
	            covid.setVacuna(rs.getString("vacuna"));
	            covid.setAbrevTipoDoc(rs.getString("abrev_tipo_doc"));
	            covid.setEstablecimiento(rs.getString("establecimiento"));*/
				l.add(tCertDisc);
			}
		} catch(Exception e){
			e.printStackTrace();
		} finally {
			if (con != null)
				try {
					con.close();
				}catch(Exception e) {
					throw new RuntimeException(e);
				}
			System.out.println("close");
		}
		return l;
	}

    public List<Disabled> loadDisabled(Integer from, Integer to, Object object, Map filters) {
        EntityManager em=Disabled.getEntityManager();
		Object filter = XUtil.isEmpty(filters.get("filter"), null);
        Object columns = XUtil.isEmpty(filters.get("columns"), null);
        Object code = XUtil.isEmpty(filters.get("code"), null);
        Object names = XUtil.isEmpty(filters.get("names"), null);
        Object surnames = XUtil.isEmpty(filters.get("surnames"), null);
        List<Query> ql = new ArrayList();
        String sql;
        //User u = userFacade.getCurrentUser();
        boolean can_admin =true;// userFacade.access("DESARROLLO_SOCIAL_ADMIN_DISABLED", u, true);
        ql.add(em.createQuery("SELECT "+(columns==null?"o,t.name ":columns) + 
                
                (sql = " FROM Disabled o "
               // + (columns != null ? " LEFT JOIN District d ON d.code=o.districtId LEFT JOIN d.province p " : "")
                        + "LEFT JOIN Town2 t ON t.id=o.town WHERE o.canceled=0 " //+ (filter != null ? " AND UPPER(w.name) like :filter" : "")
                + (!can_admin ? " AND o.province IN :province" : "")
                + (code != null ? " AND concat(o.code,'') like :code" : "")
                + (names != null ? " AND UPPER(o.names) like :names" : "")
                + (surnames != null ? " AND upper(o.surnames) like :surnames" : "")) + "  ORDER BY 1 DESC"));
        if (to > 0) {
            ql.get(0).setFirstResult(from).setMaxResults(to);
            ql.add(em.createQuery("SELECT COUNT(o) " + sql));
        }
        for (Query q : ql) {
            /*if (!can_admin) {
                List l = netFacade.getUserScopeList(u);
                l.add(0);
                q.setParameter("province", l);
            }*/
            if (filter != null) {
                q.setParameter("filter", "%" + filter.toString().toUpperCase().replace(" ", "%") + "%");
            }
            if (code != null) {
                q.setParameter("code", "%" + code + "%");
            }
            if (names != null) {
                q.setParameter("names", "%" + names.toString().toUpperCase().replace(" ", "%") + "%");
            }
            if (surnames != null) {
                q.setParameter("surnames", "%" + surnames.toString().toUpperCase().replace(" ", "%") + "%");
            }
        }
        if (to > 0) {
            filters.put("size", ql.get(1).getSingleResult());
        }
        List l = columns!=null?ql.get(0).getResultList():
		((List<Object[]>)ql.get(0).getResultList()).stream().map((row)->{
			Disabled t = (Disabled) row[0];
			t.setTownName((String) row[1]);
			return t;
		}).collect(Collectors.toList());


        List ids = new ArrayList();
        if(columns==null){
            for (Disabled c : (List<Disabled>)l)
                if (c.getDistrict() != null) {
                    ids.add(XUtil.intValue(c.getDistrict()));
                }
        }else{
            for (Object[] c : (List<Object[]>)l)
                if (c[5]!= null) {
                    ids.add(XUtil.intValue(c[5]));
                }
        }
        /*if (!ids.isEmpty()) {
            Map m = new HashMap();
            for (Object[] r : (List<Object[]>) super.getEntityManager().createQuery("SELECT 0+d.code,d.name,p.code,p.name FROM District d JOIN d.province p WHERE (0+d.code) IN (:ids)")
                    .setParameter("ids", ids)
                    .getResultList()) {
                m.put(r[0], r);
            }
            if(columns==null){
                for (Disabled c : (List<Disabled>)l) {
                    HashMap hm = new HashMap();
                    Object row[] = (Object[]) m.get(XUtil.intValue(c.getDistrict()));
                    if (row != null) {
                        c.setDistrictName((String) row[1]);
                        c.setProvinceName((String) row[3]);
                        hm.put("ubigeo", row);
                    }
                    c.setExt(hm);
                }
            }else{
                for (Object[] c : (List<Object[]>)l) {
                    HashMap hm = new HashMap();
                    Object row[] = (Object[]) m.get(XUtil.intValue(c[5]));
                    if (row != null) {
                        c[6]=((String) row[1]);
                        c[7]=((String) row[3]);
                        hm.put("ubigeo", row);
                    }
                }
            }
        }*/
        return l;
    }

	public Disabled findDisabled(Integer id) {
		EntityManager em=Disabled.getEntityManager();
        Disabled o = em.find(Disabled.class, id);
        HashMap ext = new HashMap();
        int province = XUtil.intValue(o.getDistrict()) / 100;
        ext.put("province", province);
        o.setExt(ext);
        return o;
	}

	public void saveDisabled(Disabled entity) {
		EntityManager em=Disabled.getEntityManager();
        
        //if (entity.getUpdateDate()== null) {
        //entity.setUpdateDate(X.getServerDate());
        //}
        if (entity.getUser() == null) {
            /*User u = userFacade.getCurrentUser();
            People p = (People) sessionFacade.get("people");
            if (p != null) {
                entity.setUser(XUtil.intValue(p.getCode()));
            } else if (u != null) {
                entity.setUser(-XUtil.intValue(u.getIdDir()));
            }*/
        }
        if (entity.getId() == null) {
			em.persist(entity);
        } else {
            em.merge(entity);
        }
        Map ext = (Map) entity.getExt();
        if (ext != null && !XUtil.isEmpty(ext.get("tempFile"))) {
            //changeImage(entity.getId(), (String) ext.get("tempFile"));
        }
    }

}
