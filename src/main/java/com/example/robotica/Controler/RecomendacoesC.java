package com.example.robotica.Controler;

import java.util.List;

import com.example.robotica.DAO.OntologyManager;
import com.example.robotica.Model.Aula;
import com.example.robotica.Model.Visualizacao;
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
   
    
    OntologyManager ont = new OntologyManager();
    

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