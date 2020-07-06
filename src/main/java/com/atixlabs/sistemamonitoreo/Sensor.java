package com.atixlabs.sistemamonitoreo;

import java.util.Timer;
import java.util.TimerTask;

public class Sensor extends Thread {
    private Integer ID;
    private SistemaMonitoreo sistemaMonitoreo;

    public Sensor(Integer ID, SistemaMonitoreo sistemaMonitoreo){
        this.ID = ID;
        this.sistemaMonitoreo = sistemaMonitoreo;
    }

    private Integer getID(){
        return this.ID;
    }

    private SistemaMonitoreo getSistemaMonitoreo() {
        return this.sistemaMonitoreo;
    }

    @Override
    public void run(){

        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            public void run()
            {
                enviarMensaje(new Mensaje(getID(), realizarMedicion()));
                enviarMensaje(new Mensaje(getID(), realizarMedicion()));
            }
        };
        timer.scheduleAtFixedRate( task, 0L ,1000L);

    }

    public Double realizarMedicion(){
        return Math.random()*100;
    }

    private void enviarMensaje(Mensaje mensaje) {
        this.getSistemaMonitoreo().recibirMensaje(mensaje);
    }

}
