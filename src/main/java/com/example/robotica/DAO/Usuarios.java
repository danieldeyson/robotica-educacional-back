package com.example.robotica.DAO;

import java.util.List;

import com.example.robotica.Model.Professor;
import com.example.robotica.repository.ProfessorRepository;

public class Usuarios {

    ProfessorRepository pfr;


    public int cadastrar(Professor usuario) {

        List<Professor> list=pfr.findAll();
         for(Professor a:list){
             if(a.getUsuario().equals(usuario.getUsuario())){
                 return 0;
             }

         }

        
       return pfr.save(usuario).getId();
        
    }

    public Professor loguin(String loguin, String senha) {


        List<Professor> list=pfr.findAll();
        for(Professor a:list){
            if(a.getUsuario().equals(loguin) && a.getSenha().equals(senha)){
                return a;
            }

        }


        return null;
    }

    public List<Professor> listarUsers() {

       
        return pfr.findAll();

    }

    public Professor alterarUsuario(Professor professor) {
        Professor atualizado= pfr.findById(professor.getId()).get();

        if (professor.getIdade()!=0){atualizado.setIdade(professor.getIdade());}
        if (professor.getSenha()!=null){atualizado.setSenha(professor.getSenha());}
        if (professor.getSexo()!=null){atualizado.setSexo(professor.getSexo());}
        if (professor.getNome()!=null){atualizado.setNome(professor.getNome());}
        if (professor.getEscola()!=null){atualizado.setEscola(professor.getEscola());}
        if (professor.getCidade()!=null){atualizado.setCidade(professor.getCidade());}
        if (professor.getUsuario()!=null){atualizado.setUsuario(professor.getUsuario());}
            return pfr.saveAndFlush(atualizado);

      
    }

    
    



public String categId(int id) {
            
        return pfr.findById(id).get().getEscola();

    }

    public Professor nomeId(int id){
        return pfr.findById(id).get();
    
    
    
        }

}









    
