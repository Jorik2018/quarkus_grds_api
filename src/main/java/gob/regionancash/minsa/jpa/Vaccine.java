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
@Table(name = "BDHIS_MINSA.dbo.T_EXTERNO_COVID19")
public class Vaccine  extends PanacheEntityBase {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    //@Basic(optional = false)
    @Column(name = "Num_Doc")
    private String numDoc;
    private String red;
    private String datos;
    @Column(name = "fecha_1_dosis")
   // @Temporal(TemporalType.DATE)
    @JsonbDateFormat(value = "uuuu-MM-dd")
    private LocalDate  fecha1Dosis;      
    @Column(name = "segunda_dosis")
    private String segundaDosis;    
    @Column(name = "lugar_vacunacion")
    private String lugarVacunacion;
    @Column(name = "fecha_2_dosis")
    @JsonbDateFormat(value = "uuuu-MM-dd")
    //@Temporal(TemporalType.DATE)
    private LocalDate  fecha2Dosis;
    
     
    @Column(name = "tercera_dosis")
    private String terceraDosis;  
    @Column(name = "fecha_3_dosis")
    @JsonbDateFormat(value = "uuuu-MM-dd")
    //@Temporal(TemporalType.DATE)
    private LocalDate  fecha3Dosis;
    
    @Column(name = "updated_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedDate;
    
    @Column(name = "lugar_vacunacion_3")
    private String lugarVacunacion3;
    
    @Column(name = "num_celular")
    private String numCelular;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRed() {
		return red;
	}

	public void setRed(String red) {
		this.red = red;
	}

	public String getDatos() {
		return datos;
	}

	public void setDatos(String datos) {
		this.datos = datos;
	}

	public String getNumDoc() {
		return numDoc;
	}

	public void setNumDoc(String numDoc) {
		this.numDoc = numDoc;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	public String getSegundaDosis() {
		return segundaDosis;
	}

	public void setSegundaDosis(String segundaDosis) {
		this.segundaDosis = segundaDosis;
	}

	public String getLugarVacunacion() {
		return lugarVacunacion;
	}

	public void setLugarVacunacion(String lugarVacunacion) {
		this.lugarVacunacion = lugarVacunacion;
	}



	public LocalDate getFecha1Dosis() {
		return fecha1Dosis;
	}

	public void setFecha1Dosis(LocalDate fecha1Dosis) {
		this.fecha1Dosis = fecha1Dosis;
	}

	public LocalDate getFecha2Dosis() {
		return fecha2Dosis;
	}

	public void setFecha2Dosis(LocalDate fecha2Dosis) {
		this.fecha2Dosis = fecha2Dosis;
	}

	public String getNumCelular() {
		return numCelular;
	}

	public void setNumCelular(String numCelular) {
		this.numCelular = numCelular;
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
        if (!(object instanceof Vaccine)) {
            return false;
        }
        Vaccine other = (Vaccine) object;
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
