package com.andress.controllers;

import com.andress.dao.UsuarioDao;
import com.andress.models.Usuario;
import com.andress.utils.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    @Autowired
    private UsuarioDao usuarioDao;

    @Autowired
    private JWTUtil jwtUtil;

    @RequestMapping(value = "api/login", method = RequestMethod.POST)
    public String login(@RequestBody Usuario usuario){
        Usuario user =usuarioDao.obtenerUsuarioPorCredenciales(usuario);
        if(user != null){
            return jwtUtil.create(String.valueOf(user.getId()), user.getEmail());
        }
        return "FAIL";
    }

}
