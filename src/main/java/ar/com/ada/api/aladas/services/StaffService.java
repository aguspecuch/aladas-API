package ar.com.ada.api.aladas.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.com.ada.api.aladas.entities.Staff;
import ar.com.ada.api.aladas.repos.StaffRepository;

@Service
public class StaffService {
    
    @Autowired
    StaffRepository repo;

    public void crear(Staff staff) {
        repo.save(staff);
    }

    public Staff buscarPorId(Integer id) {
        return repo.findByStaffId(id);
    }
}
