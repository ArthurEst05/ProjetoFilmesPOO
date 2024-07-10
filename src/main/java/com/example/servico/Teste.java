package com.example.servico;

import entites.Usuario;
import modelDao.DaoFactory;
import modelDao.UsuarioDao;

public class Teste {
    public static void main(String[] args) {
        UsuarioDao usuarioDao = DaoFactory.createUsuarioDao();

        Usuario newUsuario = new Usuario(null, "Alex", "alex@gmail.com", "1234567");
        usuarioDao.insert(newUsuario);
        System.out.println("Inserted! New id = " + newUsuario.getId());
    }
}
