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

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;

import io.agroal.api.AgroalDataSource;
import org.isobit.util.XUtil;

import gob.regionancash.minsa.jpa.TCertDisc;
import gob.regionancash.minsa.jpa.Vaccine;
import gob.regionancash.minsa.jpa.VaccineCOVID;

@Transactional
@ApplicationScoped
public class CovidService {
	
	@Inject
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

	public Object loadDisabled(Integer from, Integer to, Object object, Map filters) {
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
	
}
