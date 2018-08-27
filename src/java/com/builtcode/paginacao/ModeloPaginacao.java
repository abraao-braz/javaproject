/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.builtcode.paginacao;

import com.builtcode.beans.Modelo;
import com.builtcode.classes.Mensagem;
import com.builtcode.classes.Paginacao;
import java.util.List;

/**
 *
 * @author Fabio
 */
public class ModeloPaginacao{
    private List<Modelo> dados;
    private Paginacao paginacao;
    private Mensagem erro;
    
    public ModeloPaginacao(){
        
    }
    
    public ModeloPaginacao(List<Modelo> dados, Paginacao paginacao, Mensagem erro){
        this.dados = dados;
        this.paginacao = paginacao;
        this.erro = erro;
    }



    /**
     * @param dados the dados to set
     */
    public void setDados(List<Modelo> dados) {
        this.dados = dados;
    }
    
    public List<Modelo> getDados() {
        return this.dados;
    }    

    /**
     * @return the paginacao
     */
    public Paginacao getPaginacao() {
        return paginacao;
    }

    /**
     * @param paginacao the paginacao to set
     */
    public void setPaginacao(Paginacao paginacao) {
        this.paginacao = paginacao;
    }

    /**
     * @return the erro
     */
    public Mensagem getErro() {
        return erro;
    }

    /**
     * @param erro the erro to set
     */
    public void setErro(Mensagem erro) {
        this.erro = erro;
    }

}
