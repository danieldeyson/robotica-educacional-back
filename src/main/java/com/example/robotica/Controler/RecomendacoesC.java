package com.example.robotica.Controler;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.example.robotica.DAO.OntologyManager;
import com.example.robotica.DAO.RecomendacoesDao;
import com.example.robotica.Model.Aula;


import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RecomendacoesC {

    OntologyManager ont = new OntologyManager();
    RecomendacoesDao recomen = new RecomendacoesDao();


   
       
            
        @GetMapping("/visualizacoes")
        public List<Aula> recomendadosRanqueado(@RequestParam("categoria") String cat) throws JSONException {
            List<Aula> lista = new ArrayList<Aula>();
            List<Aula> list = new LinkedList<Aula>();
            List<String> rec = new ArrayList<String>();
            List<String> views = new ArrayList<String>();
            lista = ont.query();
            rec = recomen.comentariosCont(cat);
            views=recomen.visualizacoesCont();
    
            for (String str : views) {
    
                for (Aula a : lista) {
    
                    
                    if (str.equals(a.getNome())) {
                      
                        list.add(a);
                      
                    }

                    }
                
                }
                 for (Aula a : lista) {
                for (String recomendados : rec) {
                   
                    
                    
                        if (recomendados.equals(a.getNome())&&!list.contains(a)) {
                         
                            list.add(a);
                           
                        }
                        }
                    
                    }
                    for(Aula a: lista){
                        if(!list.contains(a)){
                            list.add(a);
                        }
                    }
            
        
        
                    list.stream().distinct();
        return list;

    }

    

    @GetMapping("/visualizacoeslist")
    public List<String> viewsRec() {

        List<String> list = new LinkedList<String>();
        
            list = recomen.visualizacoesCont();
        

        return list;
    }
    @PostMapping("/visualizacoes")
    public int quantVisual(@RequestBody Aula aula) throws JSONException {

        return recomen.visualizacoes(aula.getNome());
    }

    @PostMapping("/visualizacao")
    public int quantVisual(@RequestParam("iduser") int iduser,@RequestBody Aula aula ) throws JSONException {

        try {
            recomen.visualizar(aula.getNome(), iduser);
            return recomen.visualizacoes(aula.getNome());
        } catch (SQLException e) {
            
            e.printStackTrace();
        }
        return recomen.visualizacoes(aula.getNome());
    }

}