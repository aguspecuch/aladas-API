package ar.com.ada.api.aladas.services;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import ar.com.ada.api.aladas.entities.*;
import ar.com.ada.api.aladas.entities.Pais.PaisEnum;
import ar.com.ada.api.aladas.entities.Pais.TipoDocuEnum;
import ar.com.ada.api.aladas.entities.Usuario.TipoUsuarioEnum;
import ar.com.ada.api.aladas.repos.UsuarioRepository;
import ar.com.ada.api.aladas.security.Crypto;

@Service
public class UsuarioService {

    @Autowired
    UsuarioRepository repo;

    @Autowired
    PasajeroService pasajeroService;

    @Autowired
    StaffService staffService;

    public Usuario crearUsuario(TipoUsuarioEnum tipoUsuario, String nombre, int pais, Date fechaNacimiento,
            TipoDocuEnum tipoDocumento, String documento, String email, String password) {

        Usuario usuario = new Usuario();
        usuario.setUsername(email);
        usuario.setPassword(Crypto.encrypt(password, email.toLowerCase()));
        usuario.setEmail(email);
        usuario.setFechaLogin(new Date());
        usuario.setTipoUsuario(tipoUsuario);

        if (tipoUsuario == TipoUsuarioEnum.PASAJERO) {

            Pasajero pasajero = new Pasajero();
            pasajero.setDocumento(documento);
            pasajero.setFechaNacimiento(fechaNacimiento);
            pasajero.setNombre(nombre);
            pasajero.setPaisId(PaisEnum.parse(pais));
            pasajero.setTipoDocumentoId(tipoDocumento);
            pasajero.setUsuario(usuario);

            pasajeroService.crear(pasajero);

        } else if (tipoUsuario == TipoUsuarioEnum.STAFF) {

            Staff staff = new Staff();
            staff.setDocumento(documento);
            staff.setFechaNacimiento(fechaNacimiento);
            staff.setNombre(nombre);
            staff.setPaisId(PaisEnum.parse(pais));
            staff.setTipoDocumentoId(tipoDocumento);
            staff.setUsuario(usuario);

            staffService.crear(staff);
        }

        return usuario;
    }

    public Usuario login(String username, String password) {

        Usuario usuario = buscarPorUsername(username);

        if (usuario == null || !usuario.getPassword().equals(Crypto.encrypt(password, usuario.getEmail().toLowerCase()))) {

            throw new BadCredentialsException("Usuario o contraseña invalida");
        }

        return usuario;

    }

    public Usuario buscarPorUsername(String username) {
        return repo.findByUsername(username);
    }

    public Usuario buscarPorEmail(String email) {
        return repo.findByEmail(email);
    }

    public Usuario buscarPorId(Integer id) {
        return repo.findByUsuarioId(id);
    }

    public Map<String, Object> getUserClaims(Usuario usuario) {

        Map<String, Object> claims = new HashMap<>();

        claims.put("userType", usuario.getTipoUsuario());

        if (usuario.obtenerEntityId() != null)
            claims.put("entityId", usuario.obtenerEntityId());

        return claims;
    }

    public UserDetails getUserAsUserDetail(Usuario usuario) {

        UserDetails uDetails;

        uDetails = new User(usuario.getUsername(), usuario.getPassword(), getAuthorities(usuario));

        return uDetails;
    }

    // Usamos el tipo de datos SET solo para usar otro diferente a List private
    Set<? extends GrantedAuthority> getAuthorities(Usuario usuario) {

        Set<SimpleGrantedAuthority> authorities = new HashSet<>();

        TipoUsuarioEnum userType = usuario.getTipoUsuario();

        authorities.add(new SimpleGrantedAuthority("CLAIM_userType_" + userType.toString()));

        if (usuario.obtenerEntityId() != null)
            authorities.add(new SimpleGrantedAuthority("CLAIM_entityId_" + usuario.obtenerEntityId()));
        return authorities;
    }
}
