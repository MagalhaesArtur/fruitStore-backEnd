package com.fruitStoreSystem.api.controller;

import com.fruitStoreSystem.api.domain.user.*;
import com.fruitStoreSystem.api.infra.security.TokenService;
import com.fruitStoreSystem.api.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserRepository repository;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UserRepository userRepository;


    @PostMapping("/validateToken")
    public ResponseEntity authToken(@RequestBody TokenDTO token) {

        var sub = tokenService.validateToken(token.token());
        User user = userRepository.findUserByLogin(sub);
        UserDTO userDTO = new UserDTO(user.getUsername(), user.getId(),user.getRole());
        return new ResponseEntity<>(userDTO, HttpStatus.OK);


    }


    @PostMapping("/login")
    public ResponseEntity login(@RequestBody AuthenticationDTO data){
        try{
            var usernamePassword = new UsernamePasswordAuthenticationToken(data.login(), data.password());

            var auth = this.authenticationManager.authenticate(usernamePassword);

            var token = tokenService.generateToken((User) auth.getPrincipal());
            return ResponseEntity.ok(new LoginResponseDTO(token));
        }

         catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Erro na autenticação");
        }
    }

    @PostMapping("/registerADM")
    public ResponseEntity registerADM(@RequestBody RegisterDTO registerDTO){
        if(this.repository.findByLogin(registerDTO.login()) != null) {
            return
                    ResponseEntity.badRequest().build();
        }
        String encryptedPassword = new BCryptPasswordEncoder().encode(registerDTO.password());
        User newUser = new User(registerDTO.login(),encryptedPassword,registerDTO.role());

        this.repository.save(newUser);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody AuthenticationDTO registerDTO){
        if(this.repository.findByLogin(registerDTO.login()) != null) {
            return
                    ResponseEntity.badRequest().build();
        }
        String encryptedPassword = new BCryptPasswordEncoder().encode(registerDTO.password());
        User newUser = new User(registerDTO.login(),encryptedPassword, UserRoles.USER);

        this.repository.save(newUser);

        return ResponseEntity.ok().build();
    }
}
