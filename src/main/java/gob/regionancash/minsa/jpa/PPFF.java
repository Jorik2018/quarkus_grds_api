package gob.regionancash.minsa.jpa;

import java.io.Serializable;
import java.time.LocalDate;

import javax.json.bind.annotation.JsonbDateFormat;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;


@Entity
@Table(name = "BDHIS_MINSA.dbo.t_ppff")
public class PPFF extends PanacheEntityBase {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    private Integer id;
    @Column(name = "sector")
    private String sector;
    @Column(name = "nun_Documento")
    private String numDocumento;
    @Column(name = "paterno")
    private String paterno;
    @Column(name = "Materno")
    private String materno;
    @Column(name = "primer_Nombre")
    private String primerNombre;
    @Column(name = "Segundo_Nombre")
    private String segundoNombre;
    @Column(name = "sexo")
    private String sexo;
    @Column(name = "residencia_habitual_MEF")
    private String residenciaHabitualMEF;
    @Column(name = "Residencia_habitual_Transeunte")
    private String residenciaHabitualTranseunte;
    @Column(name = "num_telf_mef")
    private String numTelfMEF;
    @Column(name = "Contacto_Telefono")
    private String contactoTelefono;
    @Column(name = "contacto_Descripcion")
    private String contactoDescripcion;
    @Column(name = "grado_instruccion")
    private String gradoInstruccion;
    private String religion;
    @Column(name = "estado_civil")
    private String estadoCivil;
    @Column(name = "formula_Obstetrica_G")
    private String formulaObstetricaG;
    @Column(name = "formula_Obstetrica_P")
    private String formulaObstetricaP;
    @Column(name = "formula_Obstetrica_FUR")
    private String formulaObstetricaFUR;
    @Column(name = "ao_Numero_Cesareas")
    private String aoNumeroCesareas;
    @Column(name = "fecha_ultimo_Parto")
    @JsonbDateFormat(value = "uuuu-MM-dd")
    private LocalDate fechaUltimoParto;

    
    @Column(name = "observaciones")
    private String observaciones;
    @Column(name = "Estado_Alta_Baja")
    private String estadoAltaBaja;

    public PPFF() {
    }

    public PPFF(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }

    public String getNumDocumento() {
		return numDocumento;
	}

	public void setNumDocumento(String numDocumento) {
		this.numDocumento = numDocumento;
	}

	public String getPaterno() {
        return paterno;
    }

    public void setPaterno(String paterno) {
        this.paterno = paterno;
    }

    public LocalDate getFechaUltimoParto() {
		return fechaUltimoParto;
	}

	public void setFechaUltimoParto(LocalDate fechaUltimoParto) {
		this.fechaUltimoParto = fechaUltimoParto;
	}

	public String getMaterno() {
        return materno;
    }

    public void setMaterno(String materno) {
        this.materno = materno;
    }

    public String getPrimerNombre() {
        return primerNombre;
    }

    public void setPrimerNombre(String primerNombre) {
        this.primerNombre = primerNombre;
    }

    public String getSegundoNombre() {
        return segundoNombre;
    }

    public void setSegundoNombre(String segundoNombre) {
        this.segundoNombre = segundoNombre;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }



    public String getResidenciaHabitualMEF() {
		return residenciaHabitualMEF;
	}

	public void setResidenciaHabitualMEF(String residenciaHabitualMEF) {
		this.residenciaHabitualMEF = residenciaHabitualMEF;
	}

	public String getResidenciaHabitualTranseunte() {
		return residenciaHabitualTranseunte;
	}

	public void setResidenciaHabitualTranseunte(String residenciaHabitualTranseunte) {
		this.residenciaHabitualTranseunte = residenciaHabitualTranseunte;
	}

	public String getNumTelfMEF() {
		return numTelfMEF;
	}

	public void setNumTelfMEF(String numTelfMEF) {
		this.numTelfMEF = numTelfMEF;
	}

	public String getContactoTelefono() {
        return contactoTelefono;
    }

    public void setContactoTelefono(String contactoTelefono) {
        this.contactoTelefono = contactoTelefono;
    }

    public String getContactoDescripcion() {
        return contactoDescripcion;
    }

    public void setContactoDescripcion(String contactoDescripcion) {
        this.contactoDescripcion = contactoDescripcion;
    }

    public String getGradoInstruccion() {
        return gradoInstruccion;
    }

    public void setGradoInstruccion(String gradoInstruccion) {
        this.gradoInstruccion = gradoInstruccion;
    }

    public String getReligion() {
        return religion;
    }

    public void setReligion(String religion) {
        this.religion = religion;
    }

    public String getEstadoCivil() {
        return estadoCivil;
    }

    public void setEstadoCivil(String estadoCivil) {
        this.estadoCivil = estadoCivil;
    }

    public String getFormulaObstetricaG() {
        return formulaObstetricaG;
    }

    public void setFormulaObstetricaG(String formulaObstetricaG) {
        this.formulaObstetricaG = formulaObstetricaG;
    }

    public String getFormulaObstetricaP() {
        return formulaObstetricaP;
    }

    public void setFormulaObstetricaP(String formulaObstetricaP) {
        this.formulaObstetricaP = formulaObstetricaP;
    }

    public String getFormulaObstetricaFUR() {
        return formulaObstetricaFUR;
    }

    public void setFormulaObstetricaFUR(String formulaObstetricaFUR) {
        this.formulaObstetricaFUR = formulaObstetricaFUR;
    }

    public String getAoNumeroCesareas() {
        return aoNumeroCesareas;
    }

    public void setAoNumeroCesareas(String aoNumeroCesareas) {
        this.aoNumeroCesareas = aoNumeroCesareas;
    }



    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public String getEstadoAltaBaja() {
        return estadoAltaBaja;
    }

    public void setEstadoAltaBaja(String estadoAltaBaja) {
        this.estadoAltaBaja = estadoAltaBaja;
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
        if (!(object instanceof PPFF)) {
            return false;
        }
        PPFF other = (PPFF) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "geoserver.PPFF[ id=" + id + " ]";
    }
    
}
