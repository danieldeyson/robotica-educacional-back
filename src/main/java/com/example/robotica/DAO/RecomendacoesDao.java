package com.example.robotica.DAO;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import com.example.robotica.Model.Comentario;
import com.example.robotica.Model.Visualizacao;
import com.example.robotica.repository.ComentarioRepository;
import com.example.robotica.repository.VizualizacoesRepository;

import org.springframework.boot.configurationprocessor.json.JSONException;

public class RecomendacoesDao {

    VizualizacoesRepository vRepository;
    ComentarioRepository cRepository;
    

    public int visualizacoes(String aula) throws JSONException {

        List<Visualizacao> list= vRepository.findAll();
      int resultado =0;
      for(Visualizacao c:list){
          if(c.getAula().equals(aula)){
              resultado ++;

          }
      }
        return resultado;

    }


 public int visualizar(String aula,int idUser) throws SQLException {
        
    List<Visualizacao> list= vRepository.findAll();
    
    for(Visualizacao c:list){
        if(c.getAula().equals(aula)&& c.getIduser()==idUser){
            return 0;

        }
    }
          Visualizacao view= new Visualizacao();
          view.setAula(aula);
          view.setIduser(idUser);
         return vRepository.save(view).getIduser();      
              
    }


    public List<String> visualizacoesCont(){
        return vRepository.findAllCont();

    }



    public List<String> comentariosCont(String cat) throws JSONException {
 
        
     List<String>list= cRepository.findAllCont(cat);
            
          
            return list;
        }
}