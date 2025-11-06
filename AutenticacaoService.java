package com.protegeagro.protege_agro_api.service;

import com.protegeagro.protege_agro_api.dto.CadastroRequestDTO;
import com.protegeagro.protege_agro_api.dto.LoginRequestDTO;
import com.protegeagro.protege_agro_api.model.Usuario;
import com.protegeagro.protege_agro_api.repository.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AutenticacaoService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public AutenticacaoService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public boolean autenticar(LoginRequestDTO loginRequest) {
        Usuario usuario = usuarioRepository.findByEmail(loginRequest.getEmail())
                .orElse(null);

        if (usuario == null) {
            return false;
        }
        return passwordEncoder.matches(loginRequest.getSenha(), usuario.getSenha());
    }

    public Usuario registrar(CadastroRequestDTO request) {

        if (usuarioRepository.findByEmail(request.getEmail()).isPresent()) {

            throw new RuntimeException("Email já cadastrado.");
        }

        String senhaCriptografada = passwordEncoder.encode(request.getSenha());

        Usuario novoUsuario = new Usuario();
        novoUsuario.setEmail(request.getEmail());
        novoUsuario.setSenha(senhaCriptografada);
        novoUsuario.setTelefone(request.getTelefone());
        novoUsuario.setEstado(request.getEstado());
        novoUsuario.setRegiao(request.getRegiao());
        novoUsuario.setCultivo(request.getCultivo());

        return usuarioRepository.save(novoUsuario);
    }
}
