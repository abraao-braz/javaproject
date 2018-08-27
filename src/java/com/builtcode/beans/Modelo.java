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
public class Modelo {
    private String id;
    private String descricao;
    private Segmento segmento;
    private Carroceria carroceria;
    private Montadora montadora;
    private String cat1;
    private String cat2;
    private String cat3;

    public Modelo(){
        
    }
    
    public Modelo(String id, String descricao){
        this.id = id;
        this.descricao = descricao;
    }
    
    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
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
     * @return the segmento
     */
    public Segmento getSegmento() {
        return segmento;
    }

    /**
     * @param segmento the segmento to set
     */
    public void setSegmento(Segmento segmento) {
        this.segmento = segmento;
    }

    /**
     * @return the carroceria
     */
    public Carroceria getCarroceria() {
        return carroceria;
    }

    /**
     * @param carroceria the carroceria to set
     */
    public void setCarroceria(Carroceria carroceria) {
        this.carroceria = carroceria;
    }

    /**
     * @return the montadora
     */
    public Montadora getMontadora() {
        return montadora;
    }

    /**
     * @param montadora the montadora to set
     */
    public void setMontadora(Montadora montadora) {
        this.montadora = montadora;
    }

    /**
     * @return the cat1
     */
    public String getCat1() {
        return cat1;
    }

    /**
     * @param cat1 the cat1 to set
     */
    public void setCat1(String cat1) {
        this.cat1 = cat1;
    }

    /**
     * @return the cat2
     */
    public String getCat2() {
        return cat2;
    }

    /**
     * @param cat2 the cat2 to set
     */
    public void setCat2(String cat2) {
        this.cat2 = cat2;
    }

    /**
     * @return the cat3
     */
    public String getCat3() {
        return cat3;
    }

    /**
     * @param cat3 the cat3 to set
     */
    public void setCat3(String cat3) {
        this.cat3 = cat3;
    }
}
