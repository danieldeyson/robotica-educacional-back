package com.example.robotica.Controler;

import java.sql.SQLException;
import java.util.List;

import com.example.robotica.Model.Professor;
import com.example.robotica.repository.ProfessorRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*")
@RestController
public class UsuariosC {

  int resultado = 0;
 

  @Autowired
  private ProfessorRepository repositoryProfessor;
  @PostMapping("/cadastro")
  public int cadastrar(@RequestBody Professor usuario) throws SQLException {
    List<Professor> list=repositoryProfessor.findAll();
    for(Professor a:list){
        if(a.getUsuario().equals(usuario.getUsuario())){
            return 0;
        }
    }

    return repositoryProfessor.save(usuario).getId();

  }

  @GetMapping("/loguin")
  public Professor loguin(@RequestParam("loguin") String loguin, @RequestParam("senha") String senha) {

    List<Professor> list=repositoryProfessor.findAll();
    for(Professor a:list){
        if(a.getUsuario().equals(loguin) && a.getSenha().equals(senha)){
            return a;
        }

    }


    return null;
}
   

  

  @GetMapping("/listar")
  public List<Professor> listarUsers() throws JSONException {

   
    return repositoryProfessor.findAll();

  }

  @PostMapping("/editar")
  public Professor alterarUsuario(@RequestBody Professor professor) {
    Professor atualizado= repositoryProfessor.findById(professor.getId()).get();

    if (professor.getIdade()!=0){atualizado.setIdade(professor.getIdade());}
    if (professor.getSenha()!=null){atualizado.setSenha(professor.getSenha());}
    if (professor.getSexo()!=null){atualizado.setSexo(professor.getSexo());}
    if (professor.getNome()!=null){atualizado.setNome(professor.getNome());}
    if (professor.getEscola()!=null){atualizado.setEscola(professor.getEscola());}
    if (professor.getCidade()!=null){atualizado.setCidade(professor.getCidade());}
    if (professor.getUsuario()!=null){atualizado.setUsuario(professor.getUsuario());}
        return repositoryProfessor.saveAndFlush(atualizado);


  }

  



@GetMapping("/nomeId")
public Professor nomeId(@RequestParam ("iduser") int id) throws SQLException {
 
    return repositoryProfessor.findById(id).get();


}

@GetMapping("/usuario/id")
public Professor usuarioId(@RequestParam ("iduser") int id) throws SQLException {

    return repositoryProfessor.findById(id).get();

}

}