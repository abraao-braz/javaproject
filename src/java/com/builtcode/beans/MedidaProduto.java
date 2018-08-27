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
public class MedidaProduto {
    private String id;
    private Medida medida;
    private Produto produto;
    private String ip7;
    private String carga;

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
     * @return the produto
     */
    public Produto getProduto() {
        return produto;
    }

    /**
     * @param produto the produto to set
     */
    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    /**
     * @return the ip7
     */
    public String getIp7() {
        return ip7;
    }

    /**
     * @param ip7 the ip7 to set
     */
    public void setIp7(String ip7) {
        this.ip7 = ip7;
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
