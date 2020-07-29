package com.example.robotica.Controler;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import java.util.stream.Stream;

import com.example.robotica.DAO.OntologyManager;

import com.example.robotica.Model.Aula;
import com.example.robotica.Model.Programacao;
import com.example.robotica.Model.Robo;
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
public class Ontologia {
	OntologyManager ont = new OntologyManager();

	@Autowired
    private VizualizacoesRepository vizualizacoesRepository;
    @Autowired
    private ComentarioRepository comentarioRepository;
	

	@GetMapping("/query")
	public List<Aula> query(@RequestParam("categoria") String categoria, @RequestParam("index") int index)
			throws JSONException {
    
				List<Aula> lista = ont.query();
				List<Aula> list = new LinkedList<Aula>();
				List<String> rec =comentarioRepository.findAllCont(categoria);
				List<String> views = vizualizacoesRepository.findAllCont();
				
				
			   
		
				for (String str : views) {
					System.out.println(str);
		
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
			
	
	
        return ont.paginar(list,index);
	}


	
	@GetMapping("/robos")
	public List<Robo> seleRobos(){
		
		List<Robo> list = ont.selectRobos();
		
		
        return list;

	}
	@GetMapping("/programacaolist")
	public List<Programacao> selectProgramacao(){

		List<Programacao> lista = ont.selectProgramacaoCompleta();
		
		

		return lista;
	}

	@GetMapping("/programacao")
	public List<Programacao> selectPrograma(@RequestParam("atividade")String atividade){

		List<Programacao> list = new LinkedList<Programacao>();
		List<Programacao> lista = ont.selectProgramacaoCompleta();
		
		for(Programacao a:lista){
			if(a.getAtividade().toLowerCase().equals(atividade.toLowerCase())){
				list.add(a);
			}

		}
		
		Stream.of(list).distinct();

        return list;

	}


	@GetMapping("/assuntos")
	public Set<String> seleAssuntos(@RequestParam("serie")String serie){

		Set<String> list= new HashSet<String>() ;
	
	
		ArrayList <Aula> conteudos= ont.selectConteudoAssunto();		
		
			for(Aula a:conteudos){
				
				char prim=a.getSerie().charAt(0);
				char as=serie.charAt(0);
				if(as==prim){
					
					list.add(a.getAssunto());
				}

			}

        return list;

	}

	@GetMapping("/serie")
	public List<String> seleSerie(){	
		ArrayList <String> series= ont.selectSeries();		
		
		Stream.of(series).distinct();

        return series;

	}
	


	@GetMapping("/conteudoss")
	public Set<Aula> selectConteudoss(){
		
		Set<Aula> list= new HashSet<Aula>() ;
		list.addAll(ont.selectConteudoAssunto());
		
			
        return list;

	}

@GetMapping("/conteudos")
	public Set<String> selectConteudos(@RequestParam("serie")String serie,@RequestParam("assunto")String assunto){
		
		Set<String> list= new HashSet<String>() ;

		ArrayList <Aula> conteudos= ont.selectConteudo();		
		
			for(Aula a:conteudos){
				char prim=a.getSerie().charAt(0);
				char as=serie.charAt(0);
				if(prim==as && a.getAssunto().equals(assunto)){
					list.add(a.getConteudo());
				}

			}
        return list;

	}

	@GetMapping("/buscaAvancada")
	public List<Aula> resultBuscaAvancada(@RequestParam("serie")String serie, @RequestParam("assunto")String assunto,@RequestParam("conteudo")String conteudo, @RequestParam("index")int index){
		
		List<Aula> list= new ArrayList<Aula>() ;
	
		ArrayList <Aula> aulas= ont.query();		
		
			for(Aula a:aulas){
				char prim=a.getSerie().charAt(0);
				char as=serie.charAt(0);
				if((as==prim &&(a.getAssunto().equals(assunto))&&(a.getConteudo().equals(conteudo)))){
					list.add(a);
					
				}

			}
			


        return ont.paginar(list, index);


	}

	@GetMapping("/busca")
	public List<Aula> resultBusca(@RequestParam("busca")String busca, @RequestParam("index")int index){
		
		List<Aula> list=  new LinkedList<Aula>();
	
		ArrayList <Aula> aulas= ont.query();
		busca=busca.toLowerCase();		
		
			for(Aula a:aulas){
				String prim=a.getSerie();
				

				if(
				
				(a.getAssunto().toLowerCase().contains(busca))||
				(a.getConteudo().toLowerCase().contains(busca))||
				(a.getAtividade().toLowerCase().contains(busca))||
				(busca.contains(a.getAssunto().toLowerCase()))||
				(busca.contains(a.getConteudo().toLowerCase()))||
				(busca.contains(a.getAtividade().toLowerCase()))||
				(prim.toLowerCase().contains(busca))||
				(busca.contains(prim.toLowerCase()))||
				(busca.contains(a.getNome().toLowerCase()))){
					list.add(a);
					
				}

			}
			if(list.size()==0){
				
				
				ArrayList <Aula> conteudos= ont.selectConteudoAssunto();
				list.add(null);		
		
			for(Aula a:conteudos){

		
				if(( busca.contains(a.getConteudo().toLowerCase())||
				a.getConteudo().toLowerCase().contains(busca))){
					Set<Aula> lista= new HashSet<Aula>();					
				
					lista.addAll(resultBusca(a.getAssunto(), index));
					for(Aula aula:lista){
						
						if(aula.getSerie().equals(a.getSerie())){
							System.out.println(aula.getNome());
							list.add(aula);
						}
					}
			
				}

			}
			}
			
			if(list.size()==0){
				
				
				ArrayList <Aula> conteudos= ont.selectConteudoAssunto();		
		
			for(Aula a:conteudos){

		
				if(( busca.contains(a.getConteudo().toLowerCase())||
				a.getConteudo().toLowerCase().contains(busca))){
									
				
					list.addAll(resultBusca(a.getAssunto(), index));
				
			
				}

			}
			}

			
	
        return ont.paginar(list, index);


	}

@PostMapping("/aula")
public Aula nome_aula(@RequestBody Aula aula){
	Aula retorno = new Aula();
	
	ArrayList <Aula> aulas= ont.query();		
	
		for(Aula a:aulas){
			if(a.getNome().equals(aula.getNome())){
				retorno= a;
				return retorno;
			}

		}
		return null;
		
	


}

}



	

