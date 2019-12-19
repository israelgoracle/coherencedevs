package com.telefonica.coco.api.cargamasivavar;

public class ArchivoCSV {

    private String TipoVar;
    private String RutaFicheroVAR;
    private String RutaFicheroKEY;
    
    public ArchivoCSV(String tipoVar, String rutaFicheroVAR, String rutaFicheroKEY) {
        super();
        TipoVar = tipoVar;
        RutaFicheroVAR = rutaFicheroVAR;
        RutaFicheroKEY = rutaFicheroKEY;
    }
    public String getTipoVar() {
        return TipoVar;
    }
    public void setTipoVar(String tipoVar) {
        TipoVar = tipoVar;
    }
    public String getRutaFicheroVAR() {
        return RutaFicheroVAR;
    }
    public void setRutaFicheroVAR(String rutaFicheroVAR) {
        RutaFicheroVAR = rutaFicheroVAR;
    }
    public String getRutaFicheroKEY() {
        return RutaFicheroKEY;
    }
    public void setRutaFicheroKEY(String rutaFicheroKEY) {
        RutaFicheroKEY = rutaFicheroKEY;
    }   
    
}
