package com.uv.app_plantae.pojo;

import com.uv.app_plantae.Sitios;

import java.io.Serializable;

public class SitiosC implements Serializable {

    private String id;
    private String nombre;
    private String descripcion;
    private String plantas;

    public SitiosC() {

    }

    public SitiosC(String id, String nombre, String descripcion, String plantas) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.plantas = plantas;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getPlantas() {
        return plantas;
    }

    public void setPlantas(String plantas) {
        this.plantas = plantas;
    }
}

