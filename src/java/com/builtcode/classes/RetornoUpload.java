/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.builtcode.classes;

/**
 *
 * @author Fabio
 */
public class RetornoUpload {
    private String arquivosalvo;
    
    public RetornoUpload(){
        
    }
    
    public RetornoUpload(String nome){
        this.arquivosalvo = nome;
    }

    /**
     * @return the arquivosalvo
     */
    public String getArquivosalvo() {
        return arquivosalvo;
    }

    /**
     * @param arquivosalvo the arquivosalvo to set
     */
    public void setArquivosalvo(String arquivosalvo) {
        this.arquivosalvo = arquivosalvo;
    }
    
    
    
}
