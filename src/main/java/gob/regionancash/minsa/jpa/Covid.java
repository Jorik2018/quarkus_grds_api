package gob.regionancash.minsa.jpa;

import java.io.Serializable;
import java.util.Date;
/*import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;*/

//@Entity
//@Table(name = "F100_POSITIVO_14")
public class Covid implements Serializable {

    private static final long serialVersionUID = 1L;
   // @Id
    //@Basic(optional = false)
   // @Column(name = "Num_Doc")
    private String id;
   // @Column(name = "Tipo_Doc")
    private String tipoDocumento;
   // @Column(name = "paciente")
    private String apellidoPaterno;
    //@Column(name = "Tipo_Prueba")
    private String tipoPrueba;
   // @Column(name = "Fecha_Prueba")
   // @Temporal(TemporalType.TIMESTAMP)
    private Date fechaEjecucionPrueba;
  //  @Column(name = "dias")
    private Integer dias;
  //  @Column(name = "resultado2")
    private String resultado;
   // @Column(name = "disa")
    private String geresaDiresaDiris;
  //  @Column(name = "nombre_del_establecimiento")
    private String hospitalInstitutoOtros;

    public String getGeresaDiresaDiris() {
        return geresaDiresaDiris;
    }

    public void setGeresaDiresaDiris(String geresaDiresaDiris) {
        this.geresaDiresaDiris = geresaDiresaDiris;
    }

    public String getHospitalInstitutoOtros() {
        return hospitalInstitutoOtros;
    }

    public void setHospitalInstitutoOtros(String hospitalInstitutoOtros) {
        this.hospitalInstitutoOtros = hospitalInstitutoOtros;
    }

    public String getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(String tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }

    public String getTipoPrueba() {
        return tipoPrueba;
    }

    public void setTipoPrueba(String tipoPrueba) {
        this.tipoPrueba = tipoPrueba;
    }



    public Date getFechaEjecucionPrueba() {
        return fechaEjecucionPrueba;
    }

    public void setFechaEjecucionPrueba(Date fechaEjecucionPrueba) {
        this.fechaEjecucionPrueba = fechaEjecucionPrueba;
    }

    public Integer getDias() {
        return dias;
    }

    public void setDias(Integer dias) {
        this.dias = dias;
    }

    public String getResultado() {
        return resultado;
    }

    public void setResultado(String resultado) {
        this.resultado = resultado;
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
        if (!(object instanceof Covid)) {
            return false;
        }
        Covid other = (Covid) object;
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
