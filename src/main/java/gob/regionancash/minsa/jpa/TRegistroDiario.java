package gob.regionancash.minsa.jpa;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

@Entity
@Table(name = "BDHIS_MINSA.dbo.t_registro_diario")
public class TRegistroDiario extends PanacheEntityBase {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Size(max = 255)
    @Column(name = "provincia")
    private String provincia;
    @Size(max = 255)
    @Column(name = "distrito")
    private String distrito;
    @Size(max = 255)
    @Column(name = "red_salud")
    private String redSalud;
    @Size(max = 255)
    private String microred;
    @Size(max = 255)
    private String eess;
    @Size(max = 255)
    @Column(name = "dni_notificante")
    private String dniNotificante;
    @Size(max = 255)
    @Column(name = "Fecha")
    private String fecha;
    @Column(name = "instituciones_educativas")
    private String institucionesEducativas;
    @Size(max = 255)
    @Column(name = "viviendas_visitadas")
    private String viviendasVisitadas;
    @Size(max = 255)
    @Column(name = "viviendas_efectivas")
    private String viviendasEfectivas;
    @Size(max = 255)
    @Column(name = "viviendas_cerradas")
    private String viviendasCerradas;
    @Size(max = 255)
    @Column(name = "viviendas_rechazadas")
    private String viviendasRechazadas;
    @Size(max = 255)
    @Column(name = "n_muestras_PCR")
    private String nMuestrasPCR;
    @Column(name = "grupo_etario")
    private String grupoEtario;
    @Column(name = "vacunas_regular")
    private String vacunasRegular;
    private String vph;
    private String dtpa;
    private String sr;
    @Column(name = "n_brigada")
    private String nBrigada;
    
    
    @Column(name = "astrazeneca_dosis_1")
    private Integer astrazenecaDosis1;
    @Column(name = "astrazeneca_dosis_2")
    private Integer astrazenecaDosis2;
    @Column(name = "astrazeneca_dosis_3")
    private Integer astrazenecaDosis3;
    @Column(name = "pFizer_dosis_1")
    private Integer pFizerDosis1;
    @Column(name = "pFizer_dosis_2")
    private Integer pFizerDosis2;
    @Column(name = "pFizer_dosis_3")
    private Integer pFizerDosis3;
    @Column(name = "sinopharm_dosis_1")
    private Integer sinopharmDosis1;
    @Column(name = "sinopharm_dosis_2")
    private Integer sinopharmDosis2;
    @Column(name = "sinopharm_dosis_3")
    private Integer sinopharmDosis3;
    
    
    
    public TRegistroDiario() {
    }

    public TRegistroDiario(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getInstitucionesEducativas() {
		return institucionesEducativas;
	}

	public void setInstitucionesEducativas(String institucionesEducativas) {
		this.institucionesEducativas = institucionesEducativas;
	}

	public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public String getDistrito() {
        return distrito;
    }

    public void setDistrito(String distrito) {
        this.distrito = distrito;
    }

    public String getRedSalud() {
        return redSalud;
    }

    public void setRedSalud(String redSalud) {
        this.redSalud = redSalud;
    }

    public String getDniNotificante() {
        return dniNotificante;
    }

    public void setDniNotificante(String dniNotificante) {
        this.dniNotificante = dniNotificante;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getViviendasVisitadas() {
        return viviendasVisitadas;
    }

    public void setViviendasVisitadas(String viviendasVisitadas) {
        this.viviendasVisitadas = viviendasVisitadas;
    }

    public String getViviendasEfectivas() {
        return viviendasEfectivas;
    }

    public void setViviendasEfectivas(String viviendasEfectivas) {
        this.viviendasEfectivas = viviendasEfectivas;
    }

    public String getViviendasCerradas() {
        return viviendasCerradas;
    }

    public void setViviendasCerradas(String viviendasCerradas) {
        this.viviendasCerradas = viviendasCerradas;
    }

    public String getViviendasRechazadas() {
        return viviendasRechazadas;
    }

    public void setViviendasRechazadas(String viviendasRechazadas) {
        this.viviendasRechazadas = viviendasRechazadas;
    }

    public String getnMuestrasPCR() {
		return nMuestrasPCR;
	}

	public void setnMuestrasPCR(String nMuestrasPCR) {
		this.nMuestrasPCR = nMuestrasPCR;
	}

	public Integer getAstrazenecaDosis1() {
		return astrazenecaDosis1;
	}

	public void setAstrazenecaDosis1(Integer astrazenecaDosis1) {
		this.astrazenecaDosis1 = astrazenecaDosis1;
	}

	public Integer getAstrazenecaDosis2() {
		return astrazenecaDosis2;
	}

	public void setAstrazenecaDosis2(Integer astrazenecaDosis2) {
		this.astrazenecaDosis2 = astrazenecaDosis2;
	}

	public Integer getAstrazenecaDosis3() {
		return astrazenecaDosis3;
	}

	public void setAstrazenecaDosis3(Integer astrazenecaDosis3) {
		this.astrazenecaDosis3 = astrazenecaDosis3;
	}

	public Integer getpFizerDosis1() {
		return pFizerDosis1;
	}

	public void setpFizerDosis1(Integer pFizerDosis1) {
		this.pFizerDosis1 = pFizerDosis1;
	}

	public Integer getpFizerDosis2() {
		return pFizerDosis2;
	}

	public void setpFizerDosis2(Integer pFizerDosis2) {
		this.pFizerDosis2 = pFizerDosis2;
	}

	public Integer getpFizerDosis3() {
		return pFizerDosis3;
	}

	public void setpFizerDosis3(Integer pFizerDosis3) {
		this.pFizerDosis3 = pFizerDosis3;
	}

	public Integer getSinopharmDosis1() {
		return sinopharmDosis1;
	}

	public void setSinopharmDosis1(Integer sinopharmDosis1) {
		this.sinopharmDosis1 = sinopharmDosis1;
	}

	public Integer getSinopharmDosis2() {
		return sinopharmDosis2;
	}

	public void setSinopharmDosis2(Integer sinopharmDosis2) {
		this.sinopharmDosis2 = sinopharmDosis2;
	}

	public Integer getSinopharmDosis3() {
		return sinopharmDosis3;
	}

	public void setSinopharmDosis3(Integer sinopharmDosis3) {
		this.sinopharmDosis3 = sinopharmDosis3;
	}

	
	
	public String getMicrored() {
		return microred;
	}

	public void setMicrored(String microred) {
		this.microred = microred;
	}

	public String getEess() {
		return eess;
	}

	public void setEess(String eess) {
		this.eess = eess;
	}

	public String getGrupoEtario() {
		return grupoEtario;
	}

	public void setGrupoEtario(String grupoEtario) {
		this.grupoEtario = grupoEtario;
	}

	public String getVacunasRegular() {
		return vacunasRegular;
	}

	public void setVacunasRegular(String vacunasRegular) {
		this.vacunasRegular = vacunasRegular;
	}

	public String getVph() {
		return vph;
	}

	public void setVph(String vph) {
		this.vph = vph;
	}

	public String getDtpa() {
		return dtpa;
	}

	public void setDtpa(String dtpa) {
		this.dtpa = dtpa;
	}

	public String getSr() {
		return sr;
	}

	public void setSr(String sr) {
		this.sr = sr;
	}

	public String getnBrigada() {
		return nBrigada;
	}

	public void setnBrigada(String nBrigada) {
		this.nBrigada = nBrigada;
	}

	@Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TRegistroDiario)) {
            return false;
        }
        TRegistroDiario other = (TRegistroDiario) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.isobit.directory.jpa.TRegistroDiario[ id=" + id + " ]";
    }
    
}
