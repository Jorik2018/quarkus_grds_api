package gob.regionancash.sgds.jpa;

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
@Table(name = "ds_red")
public class Red extends PanacheEntityBase {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name="ruc")
    private String id;
    private String code;
    private String name;   

}

