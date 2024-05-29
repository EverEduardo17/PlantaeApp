package com.uv.app_plantae.pojo;

import android.security.identity.IdentityCredentialStore;

import com.uv.app_plantae.Adapter.AdapterMisPlantas;
import com.uv.app_plantae.BusquedaPlantas;

import java.io.Serializable;

public class Plantas  implements Serializable {


    private String cientifico;
    private String img;
    private String nombre;

    private String id;

    //Para agregar la descripcion al detalle
    private String descripcion;

    public Plantas() {
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Plantas(String cientifico, String img, String nombre, String descripcion, String id) {
        this.cientifico = cientifico;
        this.img = img;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.id = id;
    }

    public String getCientifico() {
        return cientifico;
    }

    public void setCientifico(String cientifico) {
        this.cientifico = cientifico;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
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


}
