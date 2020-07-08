package com.example.robotica.Controler;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.example.robotica.DAO.OntologyManager;
import com.example.robotica.DAO.RecomendacoesDao;
import com.example.robotica.Model.Aula;
import com.example.robotica.Model.Recomendacao;

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



    @GetMapping("/recomendados")
    public List<Aula> recomendados(@RequestParam("categoria") String cat) throws JSONException {
        List<Aula> lista = new ArrayList<Aula>();
        List<Aula> list = new LinkedList<Aula>();
        List<Recomendacao> rec = new ArrayList<Recomendacao>();
        List<String> views = new ArrayList<String>();
        lista = ont.query();
        rec = recomen.recomendar(cat);
        views=recomen.visualizacoesCont();

        for (Recomendacao r : rec) {

            for (Aula a : lista) {

                for (String str: views){
                if (r.getAula().equals(a.getNome())||str.equals(a.getNome())) {
                  if(list.contains(a)==false){
                  
                    list.add(a);
                }
                }
            }
            }

        }
       
            

        

        return list;

    }

    @GetMapping("/recomendacoeslist")
    public List<Recomendacao> listarRec() {

        List<Recomendacao> list = new LinkedList<Recomendacao>();
        try {
            list = recomen.listarrecs();
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return list;
    }

    @GetMapping("/visualizacoeslist")
    public List<String> viewsRec() {

        List<String> list = new LinkedList<String>();
        try {
            list = recomen.visualizacoesCont();
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

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