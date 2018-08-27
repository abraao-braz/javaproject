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
public class Produto {
    private String id;
    private String descricao;
    private String caracteristica1;
    private String caracteristica2;
    private String caracteristica3;
    private String caracteristica4;
    private String imagem;
    private String cat1;
    private String homologado;

    public Produto(){
        
    }
    
    public Produto(String id){
        this.id = id;
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
     * @return the caracteristica1
     */
    public String getCaracteristica1() {
        return caracteristica1;
    }

    /**
     * @param caracteristica1 the caracteristica1 to set
     */
    public void setCaracteristica1(String caracteristica1) {
        this.caracteristica1 = caracteristica1;
    }

    /**
     * @return the caracteristica2
     */
    public String getCaracteristica2() {
        return caracteristica2;
    }

    /**
     * @param caracteristica2 the caracteristica2 to set
     */
    public void setCaracteristica2(String caracteristica2) {
        this.caracteristica2 = caracteristica2;
    }

    /**
     * @return the caracteristica3
     */
    public String getCaracteristica3() {
        return caracteristica3;
    }

    /**
     * @param caracteristica3 the caracteristica3 to set
     */
    public void setCaracteristica3(String caracteristica3) {
        this.caracteristica3 = caracteristica3;
    }

    /**
     * @return the caracteristica4
     */
    public String getCaracteristica4() {
        return caracteristica4;
    }

    /**
     * @param caracteristica4 the caracteristica4 to set
     */
    public void setCaracteristica4(String caracteristica4) {
        this.caracteristica4 = caracteristica4;
    }

    /**
     * @return the imagem
     */
    public String getImagem() {
        return imagem;
    }

    /**
     * @param imagem the imagem to set
     */
    public void setImagem(String imagem) {
        this.imagem = imagem;
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
     * @return the homologado
     */
    public String getHomologado() {
        return homologado;
    }

    /**
     * @param homologado the homologado to set
     */
    public void setHomologado(String homologado) {
        this.homologado = homologado;
    }
}
