package com.andress.dao;

import com.andress.models.Usuario;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@Transactional
public class UsuarioDaoImpl implements UsuarioDao{


    @PersistenceContext
    EntityManager entityManager;



    @Override
    public List<Usuario> getUsuarios() {
        String query=" FROM Usuario";
        return entityManager.createQuery(query).getResultList();
    }

    @Override
    public void eliminar(Long id) {
        Usuario usuario = entityManager.find(Usuario.class, id);
        entityManager.remove(usuario);
    }

    @Override
    public void registrar(Usuario usuario) {
        entityManager.merge(usuario);
    }

    @Override
    public Usuario obtenerUsuarioPorCredenciales(Usuario usuario) {
        String query=" FROM Usuario where email = :email";
        List<Usuario> usuarios = entityManager.createQuery(query)
                .setParameter("email", usuario.getEmail())
                .getResultList();
        if(usuarios.isEmpty())
            return null;
        String pwd = usuarios.get(0).getPassword();
        Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
        if(argon2.verify(pwd, usuario.getPassword())){
            return usuarios.get(0);
        }else return null;
    }

}
