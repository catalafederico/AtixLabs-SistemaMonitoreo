package com.atixlabs.sistemamonitoreo.acciones;

import com.atixlabs.sistemamonitoreo.SistemaMonitoreo;

public class ControlarM implements Accion {

    @Override
    public void ejecutar(SistemaMonitoreo sistemaMonitoreo) {
        if(sistemaMonitoreo.controlarM()){
            System.out.println("La promedio de las mediciones es mayor a M.");
        }
    }
}
