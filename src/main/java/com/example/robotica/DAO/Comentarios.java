package com.example.robotica.DAO;


import java.util.LinkedList;
import java.util.List;

import com.example.robotica.Model.Comentario;
import com.example.robotica.repository.ComentarioRepository;




public class Comentarios {
Usuarios user= new Usuarios();
ComentarioRepository cRepository;

public boolean comentar(Comentario comentario){

   try {
    cRepository.save(comentario);
    return true;
   } catch (Exception e) {
       //TODO: handle exception
   }        
            return false;
    }





    
    public List<Comentario> getComentarios(String plano) {
       
       
      List<Comentario> list= cRepository.findAll();
      List<Comentario> lista= new LinkedList<Comentario>();
      for(Comentario c:list){
          if(c.getPlanoAula().equals(plano)){
              lista.add(c);

          }
      }
        return lista;

    }
    public List<Comentario> gettComentarios() {
       
       
        List<Comentario> lista= cRepository.findAll();

          return lista;

    }



    public boolean deleteComentario(int id){

       cRepository.deleteById(id);
                    
               return true;     
       
    }



        
    }








