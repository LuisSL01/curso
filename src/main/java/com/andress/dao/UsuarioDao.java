package com.andress.dao;

import com.andress.models.Usuario;

import java.util.List;

public interface UsuarioDao {

    public List<Usuario> getUsuarios();

    void eliminar(Long id);

    void registrar(Usuario usuario);

    public Usuario obtenerUsuarioPorCredenciales(Usuario usuario);
}
