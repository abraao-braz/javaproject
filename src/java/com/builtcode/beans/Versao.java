/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.builtcode.beans;

/**
 *
 * @author Fabio
 */
public class Versao {
    private String codigo;
    private String descricao;
    private Modelo modelo;
    private String ano;
    private Medida medida;
    private String carga;

    public Versao(){
        
    }
    
    public Versao(String codigo){
        this.codigo = codigo;
    }
    
    
    /**
     * @return the codigo
     */
    public String getCodigo() {
        return codigo;
    }

    /**
     * @param codigo the codigo to set
     */
    public void setCodigo(String codigo) {
        this.codigo = codigo;
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



    /**
     * @return the modelo
     */
    public Modelo getModelo() {
        return modelo;
    }

    /**
     * @param modelo the modelo to set
     */
    public void setModelo(Modelo modelo) {
        this.modelo = modelo;
    }

    /**
     * @return the ano
     */
    public String getAno() {
        return ano;
    }

    /**
     * @param ano the ano to set
     */
    public void setAno(String ano) {
        this.ano = ano;
    }

    /**
     * @return the medida
     */
    public Medida getMedida() {
        return medida;
    }

    /**
     * @param medida the medida to set
     */
    public void setMedida(Medida medida) {
        this.medida = medida;
    }

    /**
     * @return the carga
     */
    public String getCarga() {
        return carga;
    }

    /**
     * @param carga the carga to set
     */
    public void setCarga(String carga) {
        this.carga = carga;
    }
}
