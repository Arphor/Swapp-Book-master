package com.swapp.swapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.swapp.swapp.model.AutorModel;
import com.swapp.swapp.repository.AutorRepository;

@Service
public class AutorService {
    @Autowired
    private AutorRepository autorrepository;

    public AutorModel AutorFactory(String name){
        if(autorrepository.existsByNome(name)){
            return autorrepository.findTopByNome(name).get();
        }else{
            AutorModel l = new AutorModel();
            l.setNome(name);
            return l;
        }
    }

    public List<String> findAllNames(String term){
        return autorrepository.findAllNomesByTerm(term);
    }
}
