package gob.regionancash.minsa.jpa;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

import javax.json.bind.annotation.JsonbDateFormat;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

@Entity
@Table(name = "BDHIS_MINSA.dbo.T_CONSULTA_COVID19")
public class VaccineCOVID  extends PanacheEntityBase {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    //@Basic(optional = false)
    
    @Column(name = "abrev_tipo_doc")
    private String abrevTipoDoc;
    
    @Column(name = "Num_Doc")
    private String numDoc;
    private String paciente;
    @Column(name = "fecha_vacunacion")
    @JsonbDateFormat(value = "uuuu-MM-dd")
    private LocalDate  fechaVacunacion; 
    private String  vacuna;  
    @Column(name = "dosis_aplicada")
    private String  dosisAplicada;  
    @Column(name = "Fabricante_Abrev")
    private String fabricanteAbrev;     

    private String establecimiento;
    
    @Column(name = "gruporiesgo")
    private String grupo;
    @Column(name = "fecha_nacimiento")
    private String birthdate;
    @Column(name = "provincia_residencia")
    private String  provinciaResidencia;    
    @Column(name = "distrito_residencia")
    private String  distritoResidencia;    
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }



	public String getPaciente() {
		return paciente;
	}

	public void setPaciente(String paciente) {
		this.paciente = paciente;
	}

	public String getFabricanteAbrev() {
		return fabricanteAbrev;
	}

	public void setFabricanteAbrev(String fabricanteAbrev) {
		this.fabricanteAbrev = fabricanteAbrev;
	}

	public String getNumDoc() {
		return numDoc;
	}

	public void setNumDoc(String numDoc) {
		this.numDoc = numDoc;
	}

	public String getAbrevTipoDoc() {
		return abrevTipoDoc;
	}

	public void setAbrevTipoDoc(String abrevTipoDoc) {
		this.abrevTipoDoc = abrevTipoDoc;
	}

	public String getVacuna() {
		return vacuna;
	}

	public void setVacuna(String vacuna) {
		this.vacuna = vacuna;
	}

	public String getEstablecimiento() {
		return establecimiento;
	}

	public void setEstablecimiento(String establecimiento) {
		this.establecimiento = establecimiento;
	}

	public String getGrupo() {
		return grupo;
	}

	public void setGrupo(String grupo) {
		this.grupo = grupo;
	}

	public String getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(String birthdate) {
		this.birthdate = birthdate;
	}

	public String getProvinciaResidencia() {
		return provinciaResidencia;
	}

	public void setProvinciaResidencia(String provinciaResidencia) {
		this.provinciaResidencia = provinciaResidencia;
	}

	public String getDistritoResidencia() {
		return distritoResidencia;
	}

	public void setDistritoResidencia(String distritoResidencia) {
		this.distritoResidencia = distritoResidencia;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getDosisAplicada() {
		return dosisAplicada;
	}

	public void setDosisAplicada(String dosisAplicada) {
		this.dosisAplicada = dosisAplicada;
	}

	public LocalDate getFechaVacunacion() {
		return fechaVacunacion;
	}

	public void setFechaVacunacion(LocalDate fechaVacunacion) {
		this.fechaVacunacion = fechaVacunacion;
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
        if (!(object instanceof VaccineCOVID)) {
            return false;
        }
        VaccineCOVID other = (VaccineCOVID) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "javaapplication1.Disabled[ id=" + id + " ]";
    }

}
