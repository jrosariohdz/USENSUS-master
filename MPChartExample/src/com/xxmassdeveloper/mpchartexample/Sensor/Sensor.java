package com.xxmassdeveloper.mpchartexample.Sensor;

/**
 * Created by NativoLink on 15/11/16.
 */
public class Sensor {
            float id_sensor,
            id_modulo,
            temperatura,
            rayos_uv,
            monoxido_carbono,
            sonido,
            humedad;

    public float getId_sensor() {
        return id_sensor;
    }

    public void setId_sensor(float id_sensor) {
        this.id_sensor = id_sensor;
    }

    public float getId_modulo() {
        return id_modulo;
    }

    public void setId_modulo(float id_modulo) {
        this.id_modulo = id_modulo;
    }

    public float getTemperatura() {
        return temperatura;
    }

    public void setTemperatura(float temperatura) {
        this.temperatura = temperatura;
    }

    public float getRayos_uv() {
        return rayos_uv;
    }

    public void setRayos_uv(float rayos_uv) {
        this.rayos_uv = rayos_uv;
    }

    public float getMonoxido_carbono() {
        return monoxido_carbono;
    }

    public void setMonoxido_carbono(float monoxido_carbono) {
        this.monoxido_carbono = monoxido_carbono;
    }

    public float getSonido() {
        return sonido;
    }

    public void setSonido(float sonido) {
        this.sonido = sonido;
    }

    public float getHumedad() {
        return humedad;
    }

    public void setHumedad(float humedad) {
        this.humedad = humedad;
    }
}
