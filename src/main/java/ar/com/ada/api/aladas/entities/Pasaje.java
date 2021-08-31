package ar.com.ada.api.aladas.entities;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "pasaje")
public class Pasaje {

    @Id
    @Column(name = "pasaje_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "info_pago")
    private String infoPago;

    @OneToOne
    @JoinColumn(name = "reserva_id", referencedColumnName = "reserva_id")
    private Reserva reserva;

    @Column(name = "fecha_emision")
    private Date fechaEmision;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getInfoPago() {
        return infoPago;
    }

    public void setInfoPago(String infoPago) {
        this.infoPago = infoPago;
    }

    public Reserva getReserva() {
        return reserva;
    }

    public void setReserva(Reserva reserva) {
        this.reserva = reserva;
    }

    public Date getFechaEmision() {
        return fechaEmision;
    }

    public void setFechaEmision(Date fechaEmision) {
        this.fechaEmision = fechaEmision;
    }

}
