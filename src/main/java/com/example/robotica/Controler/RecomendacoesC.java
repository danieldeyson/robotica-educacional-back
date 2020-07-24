package com.example.robotica.Controler;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.example.robotica.DAO.OntologyManager;
import com.example.robotica.Model.Aula;
import com.example.robotica.Model.Visualizacao;
import com.example.robotica.repository.ComentarioRepository;
import com.example.robotica.repository.VizualizacoesRepository;

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
public class RecomendacoesC {

    @Autowired
    private VizualizacoesRepository vizualizacoesRepository;
    @Autowired
    private ComentarioRepository comentarioRepository;

    OntologyManager ont = new OntologyManager();
    


        @GetMapping("/visualizacoes")
        public List<Aula> recomendadosRanqueado(@RequestParam("categoria") String cat) throws JSONException {
            List<Aula> lista = new ArrayList<Aula>();
            List<Aula> list = new LinkedList<Aula>();
            List<String> rec = new ArrayList<String>();
            List<String> views = new ArrayList<String>();
            lista = ont.query();
            rec = comentarioRepository.findAllCont(cat);
            views=vizualizacoesRepository.findAllCont();
    
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

        return vizualizacoesRepository.findAllCont();
    }


    @PostMapping("/visualizacoes")
    public int quantVisual(@RequestBody Aula aula) throws JSONException {

       return vizualizacoesRepository.contByAula(aula.getNome());
        
    }

    @PostMapping("/visualizacao")
    public int visualizar(@RequestParam("iduser") int iduser,@RequestBody Aula aula ) throws JSONException {

        List<Visualizacao> list= vizualizacoesRepository.findAll();
    
    for(Visualizacao c:list){
        if(c.getAula().equals(aula.getNome())&& c.getIduser()==iduser){
            return 0;

        }
    }
          Visualizacao view= new Visualizacao();
          view.setAula(aula.getNome());
          view.setIduser(iduser);
         return vizualizacoesRepository.save(view).getIduser();      
              
    }

}