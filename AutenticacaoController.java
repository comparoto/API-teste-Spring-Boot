package com.protegeagro.protege_agro_api.controller;

import com.protegeagro.protege_agro_api.dto.CadastroRequestDTO;
import com.protegeagro.protege_agro_api.dto.LoginRequestDTO;
import com.protegeagro.protege_agro_api.model.Usuario;
import com.protegeagro.protege_agro_api.repository.UsuarioRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth" )
public class AutenticacaoController {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public AutenticacaoController(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/login")
    public ResponseEntity<?> autenticar(@RequestBody LoginRequestDTO loginRequest) {

        return ResponseEntity.ok("Login endpoint hit");
    }

    @PostMapping("/register")
    public ResponseEntity<?> registrar(@RequestBody CadastroRequestDTO request) {
        try {
            if (usuarioRepository.findByEmail(request.getEmail()).isPresent()) {

                return ResponseEntity.badRequest().body("O e-mail " + request.getEmail() + " já está cadastrado.");
            }

            Usuario novoUsuario = new Usuario();
            novoUsuario.setEmail(request.getEmail());
            novoUsuario.setSenha(passwordEncoder.encode(request.getSenha()));
            novoUsuario.setTelefone(request.getTelefone());
            novoUsuario.setEstado(request.getEstado());
            novoUsuario.setRegiao(request.getRegiao());
            novoUsuario.setCultivo(request.getCultivo());

            Usuario usuarioSalvo = usuarioRepository.save(novoUsuario);

            return ResponseEntity.status(201).body(usuarioSalvo);

        } catch (Exception e) {

            return ResponseEntity.status(500).body("Erro ao registrar usuário: " + e.getMessage());
        }
    }
}
