package com.elastech.helpdelas.dtos;

import com.elastech.helpdelas.model.RoleModel;
import com.elastech.helpdelas.model.SectorModel;
import com.elastech.helpdelas.model.UserModel;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public class UserDTO {
    private Long userId;
    private String name;
    private String email;
    private String password;
    private SectorModel sector;
    private String supervisor;
    private RoleModel role;

    private Instant creationTimestamp;
    private Instant updatedTimestamp;

    public UserDTO(){

    }

    public UserDTO(UserModel userModel){
        this.userId = userModel.getUserId();
        this.email = userModel.getEmail();
        this.name = userModel.getName();
        this.password = userModel.getPassword();
        this.sector = userModel.getSector();
        this.supervisor = userModel.getSupervisor();
        this.role = userModel.getRole();
        this.creationTimestamp = userModel.getCreationTimestamp();
        this.updatedTimestamp = userModel.getUpdatedTimestamp();
    }

    public static UserModel convert(UserDTO userDTO){
        UserModel user = new UserModel();
        user.setUserId(userDTO.getUserId());
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        user.setSector(userDTO.getSector());
        user.setSupervisor(userDTO.getSupervisor());
        user.setRole(userDTO.getRole());
        user.setCreationTimestamp(userDTO.getCreationTimestamp());
        user.setUpdatedTimestamp(userDTO.getUpdatedTimestamp());
        return user;
    }

}
