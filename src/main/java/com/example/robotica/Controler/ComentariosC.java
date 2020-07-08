package com.example.robotica.Controler;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import com.example.robotica.DAO.Comentarios;
import com.example.robotica.DAO.RecomendacoesDao;
import com.example.robotica.DAO.Usuarios;
import com.example.robotica.Model.Aula;
import com.example.robotica.Model.Comentario;
import com.example.robotica.Model.Recomendacao;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ComentariosC {

    Comentarios com = new Comentarios();
    Usuarios user = new Usuarios();

    @PostMapping("/comentario")
    public String comentar(@RequestBody Comentario comentario) throws SQLException {

        if(com.comentar(comentario)){
        RecomendacoesDao rec= new RecomendacoesDao();
        Recomendacao recomenda= new Recomendacao();
        recomenda.setIdUser(comentario.getIdUser());
        recomenda.setCategoria(user.categId(comentario.getIdUser()));
        recomenda.setAula(comentario.getPlanoAula());

        rec.cadastrar(recomenda);
    
                return comentario.getConteudo();
                    }else return null;
                }
    
    
        @PostMapping("/comentarios")
        public List<Comentario> getComentarios(@RequestBody  Aula aula) {
        
            List<Comentario> list= new LinkedList<Comentario>();
            list= com.getComentarios(aula.getNome());
           
            return list;

        }
        
        @GetMapping("/comentarios")
        public List<Comentario> getComentarios() {
           
            List<Comentario> list= new LinkedList<Comentario>();
            list= com.gettComentarios();
           
            return list;

        }

        


    @PostMapping("/comentarios/delete")
    public boolean delComentario(@RequestParam("ID") int id){


        return com.deleteComentario(id);
    }
    
    
    
    
}