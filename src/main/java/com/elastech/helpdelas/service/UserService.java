package com.elastech.helpdelas.service;

import com.elastech.helpdelas.dtos.SectorDTO;
import com.elastech.helpdelas.dtos.UserDTO;
import com.elastech.helpdelas.model.RoleModel;
import com.elastech.helpdelas.model.UserModel;
import com.elastech.helpdelas.repositories.RoleRepository;
import com.elastech.helpdelas.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private SectorService sectorService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public UserDTO save(UserDTO userDTO) throws Exception {
        Optional<UserModel> byEmail = userRepository.findByEmail(userDTO.getEmail());
        RoleModel role = roleRepository.findByName(RoleModel.Values.USER.name());
        if(!byEmail.isPresent()){
            userDTO.setRole(role);
            userDTO.setSector(userDTO.getSector());
            userDTO.setStatus("ATIVO");
            userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
            UserModel userModel = UserDTO.convert(userDTO);
            userRepository.save(userModel);
            return new UserDTO(userModel);
        } else {
            throw new Exception("Já existe um usuário cadastrado com esse e-mail.");
        }
    }

    public UserDTO saveTech(UserDTO userDTO) throws Exception {
        Optional<UserModel> byEmail = userRepository.findByEmail(userDTO.getEmail());
        RoleModel role = roleRepository.findByName(RoleModel.Values.TECH.name());
        if(!byEmail.isPresent()){
            userDTO.setRole(role);
            userDTO.setSector(userDTO.getSector());
            userDTO.setStatus("ATIVO");
            userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
            UserModel userModel = UserDTO.convert(userDTO);
            userRepository.save(userModel);
            return new UserDTO(userModel);
        } else {
            throw new Exception("Já existe um usuário cadastrado com esse e-mail.");
        }
    }

    public List<SectorDTO> findAllSector() {
        return sectorService.findAllSector();
    }

    public List<UserDTO> findAll() {
        List<UserModel> users = userRepository.findAll();
        return users.stream().map(UserDTO::new).collect(Collectors.toList());
    }

    public UserDTO updateUserById(Long id, UserDTO userDTO) throws Exception {
        Optional<UserModel> userExistente =  userRepository.findById(id);
        Optional<UserModel> emailExistente =  userRepository.findByEmail(userExistente.get().getEmail());
        Boolean userDTO1 = getUserByEmailIgnoringCaseAndUser(userDTO.getEmail(), id);

        if(userDTO1) {
            throw new Exception("Já existe um usuário cadastrado com esse e-mail.");
        } else {
            UserModel user = emailExistente.get();
            user.setEmail(userDTO.getEmail());
            user.setName(userDTO.getName());
            if(user.getRole().getName().equals("TECH")){
                user.setSector(userExistente.get().getSector());
            } else {
                user.setSector(userDTO.getSector());
            }
            user.setSupervisor(userDTO.getSupervisor());
            userRepository.save(user);
            System.out.println(user);
            return new UserDTO(user);
        }
    }

    public Boolean getUserByEmailIgnoringCaseAndUser(String email, Long idUser) {
        UserModel byEmailIgnoringCaseAndIdNot = userRepository.findByEmailIgnoringCaseAndUserIdNot(email, idUser);
        if (byEmailIgnoringCaseAndIdNot != null){
            return true;
        }
        return  false;
    }

    public UserDTO getUserByEmail(String email){
        Optional<UserModel> user = userRepository.findByEmail(email);
        if (user.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Usuário não encontrado");
        }
        return new UserDTO(user.get());
    }

    public UserDTO getUserById(Long id) {
        Optional<UserModel> user = userRepository.findById(id);
        if (user.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Usuário não encontrado");
        }
        return new UserDTO(user.get());
    }

    public UserDTO updateStatus(Long id, String status) {
        Optional<UserModel> userExistente = userRepository.findById(id);
        if (userExistente.isPresent()) {
            if (status.equals("desativar")) {
                UserModel user = userExistente.get();
                user.setStatus("INATIVO");
                userRepository.save(user);
                return new UserDTO(user);
            } else {
                UserModel user = userExistente.get();
                user.setStatus("ATIVO");
                userRepository.save(user);
                return new UserDTO(user);
            }
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Usuário não encontrado");
        }
    }


    public List<UserDTO> showAllUsersWithSector(Long sectorId) {
        List<UserModel> users = userRepository.findBySectorSectorId(sectorId);
        return users.stream()
                .map(UserDTO::new)
                .collect(Collectors.toList());
    }

    public List<UserDTO> getAllTech() {
        List<UserModel> list = userRepository.findByRoleName("TECH");
        return list.stream()
                .filter(user -> user.getStatus().equals("ATIVO")) // Verifica se o técnico está ativo
                .map(UserDTO::new)
                .collect(Collectors.toList());
    }

}


