package com.uv.app_plantae.pojo;

import java.io.Serializable;

public class PlagasC implements Serializable {

    private String img;
    private String introduccion;
    private String nombre;
    private String prevencion;
    private String sintomas;
    private String soluciones;

    public PlagasC() {
    }


    public PlagasC(String img, String introduccion, String nombre, String prevencion, String sintomas, String soluciones) {
        this.img = img;
        this.introduccion = introduccion;
        this.nombre = nombre;
        this.prevencion = prevencion;
        this.sintomas = sintomas;
        this.soluciones = soluciones;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getIntroduccion() {
        return introduccion;
    }

    public void setIntroduccion(String introduccion) {
        this.introduccion = introduccion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPrevencion() {
        return prevencion;
    }

    public void setPrevencion(String prevencion) {
        this.prevencion = prevencion;
    }

    public String getSintomas() {
        return sintomas;
    }

    public void setSintomas(String sintomas) {
        this.sintomas = sintomas;
    }

    public String getSoluciones() {
        return soluciones;
    }

    public void setSoluciones(String soluciones) {
        this.soluciones = soluciones;
    }

}
