package com.protegeagro.protege_agro_api.controller;

import com.protegeagro.protege_agro_api.dto.CadastroRequestDTO;
import com.protegeagro.protege_agro_api.dto.LoginRequestDTO;
import com.protegeagro.protege_agro_api.model.Usuario;
import com.protegeagro.protege_agro_api.service.AutenticacaoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AutenticacaoController {

    private final AutenticacaoService autenticacaoService;

    public AutenticacaoController(AutenticacaoService autenticacaoService) {
        this.autenticacaoService = autenticacaoService;
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequestDTO loginRequest) {
        boolean autenticado = autenticacaoService.autenticar(loginRequest);
        if (autenticado) {
            return ResponseEntity.ok("Login bem-sucedido!");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Email ou senha inválidos.");
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> registrar(@RequestBody CadastroRequestDTO request) {
        try {
            Usuario novoUsuario = autenticacaoService.registrar(request);
            novoUsuario.setSenha(null);
            return ResponseEntity.status(HttpStatus.CREATED).body(novoUsuario);

        } catch (RuntimeException e) {

            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", e.getMessage()));
        }
    }
}
