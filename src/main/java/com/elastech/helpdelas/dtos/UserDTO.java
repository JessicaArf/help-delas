package com.elastech.helpdelas.dtos;

import com.elastech.helpdelas.model.RoleModel;
import com.elastech.helpdelas.model.UserModel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO {
    private Long userId;
    private String name;
    private String email;
    private String password;
    private String department;
    private String supervisor;
    private RoleModel role;

    public UserDTO(){

    }

    public UserDTO(UserModel userModel){
        this.userId = userModel.getUserId();
        this.email = userModel.getEmail();
        this.name = userModel.getName();
        this.password = userModel.getPassword();
        this.department = userModel.getDepartment();
        this.supervisor = userModel.getSupervisor();
        this.role = userModel.getRole();
    }

    public static UserModel convert(UserDTO userDTO){
        UserModel user = new UserModel();
        user.setUserId(userDTO.getUserId());
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        user.setDepartment(userDTO.getDepartment());
        user.setSupervisor(userDTO.getSupervisor());
        user.setRole(userDTO.getRole());
        return user;
    }

}
