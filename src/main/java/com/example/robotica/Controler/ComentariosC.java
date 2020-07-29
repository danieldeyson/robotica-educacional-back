package com.example.robotica.Controler;

import java.sql.SQLException;
import java.util.List;


import com.example.robotica.Model.Aula;
import com.example.robotica.Model.Comentario;
import com.example.robotica.repository.ComentarioRepository;
import com.example.robotica.repository.ProfessorRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
public class ComentariosC {

    @Autowired
    private ComentarioRepository comentarioRepository;
    @Autowired
  private ProfessorRepository repositoryProfessor;
    

    @PostMapping("/comentario")
    public String comentar(@RequestBody Comentario comentario) throws SQLException {
        comentario.setCategoria(repositoryProfessor.findById(comentario.getIdUser()).get().getEscola());
        return comentarioRepository.save(comentario).getConteudo();
                  
    }
    
        @PostMapping("/comentarios")
        public List<Comentario> getComentarios(@RequestBody  Aula aula) {
           
            return comentarioRepository.findAllByPlanoAula(aula.getNome());

        }
        
        


    @PostMapping("/comentarios/delete")
    public boolean delComentario(@RequestParam("ID") int id){
        comentarioRepository.deleteById(id);

        return true;
    }
    
    
    
    
}