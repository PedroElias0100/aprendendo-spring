package com.pedro.aprendendospring.business;

import com.pedro.aprendendospring.infrastructure.entity.Usuario;
import com.pedro.aprendendospring.infrastructure.exceptions.ConflictExxception;
import com.pedro.aprendendospring.infrastructure.exceptions.ResourceNotFoundException;
import com.pedro.aprendendospring.infrastructure.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor

public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public Usuario salvaUsuario(Usuario usuario) {
        try {
            emailExiste(usuario.getEmail());
           usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
            return usuarioRepository.save(usuario);
        } catch (ConflictExxception e) {
            throw new ConflictExxception("Email já cadastrado", e.getCause());
        }
    }

    public void emailExiste(String email) {
        try {
            boolean existe = verificaEmailExistente(email);
            if (existe) {
                throw new ClassCastException("email já cadastrado" + email);
            }
        }catch (ClassCastException e) {
            throw new ClassCastException("email já cadastrado" + e.getCause());
        }
    }

    public boolean verificaEmailExistente(String email) {

        return usuarioRepository.existsByEmail(email);
    }

    public Usuario buscarUsuarioPorEmail(String email) {
        return usuarioRepository.findByEmail(email).orElseThrow(
                () -> new ResourceNotFoundException("Email não encontrado" + email));
    }

    public void deletaUsuarioPorEmail (String email) {
        usuarioRepository.deleteByEmail(email);
    }
}
