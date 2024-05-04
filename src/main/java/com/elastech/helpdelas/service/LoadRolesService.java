package com.elastech.helpdelas.service;

import com.elastech.helpdelas.model.RoleModel;
import com.elastech.helpdelas.repositories.RoleRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoadRolesService {

    @Autowired
    private RoleRepository roleRepository;

    @PostConstruct
    public void loadData(){

        if(roleRepository.findAll().isEmpty()){
            RoleModel roleModel1 = new RoleModel();
            roleModel1.setName("ADMIN");
            roleRepository.save(roleModel1);

            RoleModel roleModel2 = new RoleModel();
            roleModel2.setName("TECH");
            roleRepository.save(roleModel2);

            RoleModel roleModel3 = new RoleModel();
            roleModel3.setName("USER");
            roleRepository.save(roleModel3);
        }
    }
}
