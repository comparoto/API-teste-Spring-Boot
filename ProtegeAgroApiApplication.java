package com.protegeagro.protege_agro_api;

import com.protegeagro.protege_agro_api.model.Usuario;
import com.protegeagro.protege_agro_api.repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class ProtegeAgroApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProtegeAgroApiApplication.class, args);
    }

    @Bean
    CommandLineRunner run(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        return args -> {

            if (usuarioRepository.findByEmail("admin@admin.com").isEmpty()) {

                Usuario admin = new Usuario();
                admin.setEmail("admin@admin.com");
                admin.setSenha(passwordEncoder.encode("123456"));

                admin.setTelefone("000000000");
                admin.setEstado("Pernambuco");
                admin.setRegiao("Sertão");
                admin.setCultivo("Milho");
                usuarioRepository.save(admin);
                System.out.println("Usuário de teste 'admin@admin.com' criado com sucesso!");
            } else {
                System.out.println("Usuário de teste 'admin@admin.com' já existe.");
            }
        };
    }
}
