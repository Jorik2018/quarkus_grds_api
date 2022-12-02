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
import lombok.*;

/*
 * CREATE TABLE T_CERT_DISC (
 * "Número de certificado discapacidad" nvarchar(255), 
 * "Tipo documento" nvarchar(255),
 *  "Número documento" nvarchar(255), 
 * 
 *  nvarchar(255),
 *  Sexo nvarchar(255), 
 * Edad nvarchar(255),
 * 
 *  "País nacimiento" nvarchar(255), 
 * 
 * 
 * Etnia nvarchar(255), 
 * "H.C." nvarchar(255), 
 * "Fecha de emisión" date, 
 * 
 * 
 * "Reevaluación en meses" nvarchar(255), 
 * Vigencia nvarchar(255), 
 * "Cod. RENAES" nvarchar(255), 
 * 
 * 
 * "¿Es anulado?" nvarchar(255), 
 * Sector nvarchar(255), 
 * Establecimiento nvarchar(255), 
 * 
 
 * Departamento nvarchar(255), 
 * Provincia nvarchar(255), 
 * Distrito nvarchar(255), 
 * 
 * "Médico certificador" nvarchar(255), 
 * "Nro documento (médico certificador)" nvarchar(255),
 *  "Diagnóstico de daño 1" nvarchar(500),
 *  "Diagnóstico de daño 2" nvarchar(500), 
 * "Diagnóstico etiológico 1" nvarchar(500), 
 * "Diagnóstico etiológico 2" nvarchar(500), 
 * 
 * 
 * "De la conducta" nvarchar(500), 
 * "De la comunicación" nvarchar(500), 
 * "Del cuidado personal" nvarchar(500), 
 * "De la locomoción" nvarchar(500), 
 * 
 * "De la disposición corporal" nvarchar(500), 
 * "De la destreza" nvarchar(500), 
 * "De la situación" nvarchar(500), 
 * 
 * Gravedad nvarchar(500), 
 * "Restricción de la participación" nvarchar(500), 
 * "Requerimiento de productos de apoyo" nvarchar(500));

 */



@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper=false,onlyExplicitlyIncluded = true)
@Entity
@Table(name = "BDHIS_MINSA.dbo.T_CERT_DISC")
public class TCertDisc extends PanacheEntityBase {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name="Número de certificado discapacidad")
    private String id;
    @Column(name = "Tipo documento")
    private String tipoDocumento;
    @Column(name = "Número documento")
    private String numeroDocumento;
    @Column(name = "Nombres y apellidos")
    private String nombresApellidos;   
    private String sexo;    
    private String edad; 
    @Column(name = "País nacimiento")
    private String paisNacimiento;  
    private String etnia; 
    @Column(name = "H.C.")
    private String HC; 
    @Column(name = "Fecha de emisión")
    private String fechaEmision;  
    @Column(name = "Reevaluación en meses")
    private String reevaluacionMeses; 
    private String vigencia; 
    @Column(name = "Cod. RENAES")
    private String codRENAES; 
    @Column(name = "¿Es anulado?")
    private String esAnulado;  
    private String sector;  
    private String establecimiento;      
    private String departamento;  
    private String provincia;  
    private String distrito; 
    @Column(name = "Médico certificador")
    private String medicoCertificador;  
    @Column(name = "Nro documento (médico certificador)")
    private String nroDocumentoMedicoCertificador;  
    @Column(name = "Diagnóstico de daño 1")
    private String diagnosticoDanio1;  
    @Column(name = "Diagnóstico de daño 2")
    private String diagnosticoDanio2;   
    @Column(name = "Diagnóstico etiológico 1")
    private String diagnosticoEtiologico1;  
    @Column(name = "Diagnóstico etiológico 2")
    private String diagnosticoEtiologico2;      

    @Column(name = "De la conducta")
    private String conducta;  
    @Column(name = "De la comunicación")
    private String comunicacion;   
    @Column(name = "Del cuidado personal")
    private String cuidadoPersonal;  
    @Column(name = "De la locomoción")
    private String locomocion;   
    @Column(name = "De la disposición corporal")
    private String disposicionCorporal; 
    @Column(name = "De la destreza")
    private String destreza; 
    @Column(name = "De la situación")
    private String situacion; 

    private String gravedad; 
    @Column(name = "Restricción de la participación")
    private String restriccionParticipacion; 
    @Column(name = "Requerimiento de productos de apoyo")
    private String requerimientoProductosApoyo; 



}

