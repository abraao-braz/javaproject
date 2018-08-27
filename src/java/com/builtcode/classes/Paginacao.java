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
public class Paginacao {
    private int pagina;
    private int total;
    
    public Paginacao(){
        
    }
    
    public Paginacao(int pagina, int total){
        this.pagina = pagina;
        if(total==0) this.total = 1;
        else this.total = total;
    }

    /**
     * @return the pagina
     */
    public int getPagina() {
        return pagina;
    }

    /**
     * @param pagina the pagina to set
     */
    public void setPagina(int pagina) {
        this.pagina = pagina;
    }

    /**
     * @return the total
     */
    public int getTotal() {
        return total;
    }

    /**
     * @param total the total to set
     */
    public void setTotal(int total) {
        if(total==0) this.total = 1;
        else this.total = total;
    }
    
    
    
}
