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
public class Mensagem {
    private String tipo;
    private String descricao; 

    public Mensagem(){
        
    }
    
    public Mensagem(String tipo, String descricao){
        this.tipo = tipo;
        this.descricao = descricao;
    }
    
    /**
     * @return the tipo
     */
    public String getTipo() {
        return tipo;
    }

    /**
     * @param tipo the tipo to set
     */
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    /**
     * @return the descricao
     */
    public String getDescricao() {
        return descricao;
    }

    /**
     * @param descricao the descricao to set
     */
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
    
    
}
