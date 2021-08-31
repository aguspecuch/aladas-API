package ar.com.ada.api.aladas.entities;

import java.util.List;

import javax.persistence.*;

@Entity
@Table(name = "pasajero")
public class Pasajero extends Persona {

    @Id
    @Column(name = "pasajero_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToMany(mappedBy = "pasajero", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Reserva> reservas;

    @OneToOne(mappedBy = "pasajero", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Usuario usuario;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<Reserva> getReservas() {
        return reservas;
    }

    public void setReservas(List<Reserva> reservas) {
        this.reservas = reservas;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
        usuario.setPasajero(this);
    }

    public void agregarReserva(Reserva reserva) {
        this.reservas.add(reserva);
        reserva.setPasajero(this);
    }

}
