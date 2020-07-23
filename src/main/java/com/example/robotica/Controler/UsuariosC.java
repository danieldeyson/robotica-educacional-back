package com.example.robotica.Controler;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import com.example.robotica.DAO.Usuarios;
import com.example.robotica.Model.Professor;


import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UsuariosC {

  int resultado = 0;
  Usuarios user = new Usuarios();

  @PostMapping("/cadastro")
  public int cadastrar(@RequestBody Professor usuario) throws SQLException {

    resultado = user.cadastrar(usuario);

    return resultado;

  }

  @GetMapping("/loguin")
  public Professor loguin(@RequestParam("loguin") String loguin, @RequestParam("senha") String senha) {

    return user.loguin(loguin, senha);

  }

  @GetMapping("/listar")
  public List<Professor> listarUsers() throws JSONException {

    List<Professor> lista = new LinkedList<>();
    lista = user.listarUsers();

    return lista;

  }

  @PostMapping("/editar")
  public Professor alterarUsuario(@RequestBody Professor professor) {

    return user.alterarUsuario(professor);

  }

  



@GetMapping("/nomeId")
public Professor nomeId(@RequestParam ("iduser") int id) throws SQLException {
  Professor usuario= new Professor();
 usuario.setNome(user.nomeId(id).getNome());
    return usuario;


}

@GetMapping("/usuario/id")
public Professor usuarioId(@RequestParam ("iduser") int id) throws SQLException {

    return user.nomeId(id);

}

}