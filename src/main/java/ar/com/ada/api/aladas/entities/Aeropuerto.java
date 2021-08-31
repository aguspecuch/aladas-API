package ar.com.ada.api.aladas.entities;

import javax.persistence.*;


@Entity
@Table(name = "aeropuerto")
public class Aeropuerto {
    
    @Id
    @Column(name = "aeropuerto_id")
    private Integer id;

    @Column(name = "nombre_aeropuerto")
    private String nombre;

    @Column(name = "codigo_IATA")
    private String codigoIATA;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCodigoIATA() {
        return codigoIATA;
    }

    public void setCodigoIATA(String codigoIATA) {
        this.codigoIATA = codigoIATA;
    }

    
}
