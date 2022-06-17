package com.andress.controllers;

import com.andress.dao.UsuarioDao;
import com.andress.models.Usuario;
import com.andress.utils.JWTUtil;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "api/")
public class UsuarioController {

    @Autowired
    private UsuarioDao usuarioDao;

    @Autowired
    private JWTUtil jwtUtil;

    @RequestMapping(value = "usuario/{id}", method = RequestMethod.GET)
    public Usuario getUsuario(@PathVariable Long id){
        Usuario usuario = new Usuario();
        usuario.setId(id);
        usuario.setNombre("luis");
        usuario.setApellido("andres");
        usuario.setEmail("email@example");
        usuario.setTelefono("5555555555");
        return usuario;
    }

    public boolean validarUsuario(String token){
        String idUser = jwtUtil.getKey(token);
        return idUser != null;
    }

    @RequestMapping(value = "usuarios", method = RequestMethod.GET)
    public List<Usuario> getUsuarios(@RequestHeader(value = "Authorization") String token){
        if(validarUsuario(token)){
            return  usuarioDao.getUsuarios();
        }else{
            return new ArrayList<>();
        }
    }


    @RequestMapping(value = "usuarios", method = RequestMethod.POST)
    public void registrarUsuario(@RequestBody Usuario usuario){
        Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
        usuario.setPassword(argon2.hash(1, 1024, 1, usuario.getPassword()));
        System.out.println("tamaño_: "+ usuario.getPassword().length());
        usuarioDao.registrar(usuario);
    }

    @RequestMapping(value = "editar")
    public Usuario editar(){
        Usuario usuario = new Usuario();
        usuario.setNombre("luis");
        usuario.setApellido("andres");
        usuario.setEmail("email@example");
        usuario.setTelefono("5555555555");
        return usuario;
    }

    @RequestMapping(value = "usuarios/{id}", method = RequestMethod.DELETE)
    public void eliminar(@RequestHeader(value = "Authorization") String token, @PathVariable Long id){
        if(validarUsuario(token)){
            this.usuarioDao.eliminar(id);
        }else{
            return;
        }
    }


}
