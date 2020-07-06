package com.atixlabs.sistemamonitoreo;

import java.time.LocalDateTime;

public class Mensaje {

    private Integer sensorID;
    private Double medicion;
    private LocalDateTime timestamp;

    public Mensaje(Integer sensorID, Double medicion){
        this.sensorID = sensorID;
        this.medicion = medicion;
        this.timestamp = LocalDateTime.now();
    }

    public Integer getSensorID() { return this.sensorID; }

    public Double getMedicion(){
        return this.medicion;
    }

    public LocalDateTime getTimestamp(){ return this.timestamp; }

}
