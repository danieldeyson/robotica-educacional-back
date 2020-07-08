package com.example.robotica.services;

import java.util.List;

import com.example.robotica.Model.Aula;

public class funcoes {

    public List<Aula> tirarDuplicatas(List<Aula>lista){
       int i=1;
        for(Aula a:lista){            

                    if(a.getNome().equals(lista.get(i).getNome())){
                        lista.remove(a);
                    } 
                    i++;
                     

              
                
                    }
                    tirarDuplicatas(lista);

        return lista;
    }
    
}