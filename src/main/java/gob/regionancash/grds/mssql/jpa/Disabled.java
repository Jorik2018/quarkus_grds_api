package gob.regionancash.grds.mssql.jpa;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import lombok.*;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper=false,onlyExplicitlyIncluded = true)
@Entity
@Table(name = "ds_disabled")
public class Disabled extends PanacheEntityBase  {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    private Integer id;
    
    private BigInteger code;

    private String names;

    private String surnames;
    @Transient
    private Object ext;
    @Column(name = "birthdate")
    @Temporal(TemporalType.DATE)
    private Date birthdate;

    private Integer province;

    private Integer district;

    private Integer town;

    private String address;

    private String type;
    @Column(name = "other_type")
    private String otherType;
    @Column(name = "phone")
    private String phone;

    private boolean certified;

    private String severity;
    @Column(name = "devices")
    private String devices;

    private boolean assistance;

    private String assistant;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    
    private Double lat;

    private Double lon;
    @Column(name = "user_")
    private Integer user;
    @Column(name = "update_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateDate;

    private boolean canceled;

    @Column(name = "nro_resolution")
    private String nroResolution;
    @Column(name = "mail")
    private String mail;
    @Column(name = "nro_certificate")
    private String nroCertificate;
    @Column(name = "issuance_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date issuanceDate;
    @Column(name = "expiry_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date expiryDate;
    @Column(name = "basic_needs")
    private String basicNeeds;
    @Column(name = "description_needs")
    private String descriptionNeeds;
    @Column(name = "assistant_phone")
    private String assistantPhone;
    @Column(name = "death_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date deathDate;
    @Transient
    private String provinceName;
    @Transient
    private String districtName;
    @Transient
    private String townName;

}