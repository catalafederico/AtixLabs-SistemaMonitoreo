package com.atixlabs.sistemamonitoreo;

import com.atixlabs.sistemamonitoreo.acciones.Accion;
import com.atixlabs.sistemamonitoreo.acciones.ControlarM;
import com.atixlabs.sistemamonitoreo.acciones.ControlarS;
import com.atixlabs.sistemamonitoreo.acciones.LoguearProcesamiento;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.logging.LogManager;

public class App
{
    public static void main(String[] args) throws IOException {

        LogManager.getLogManager().reset();
        Double constanteS = 0.0;
        Double constanteM = 0.0;

        Scanner inputScanner = new Scanner(System.in);
        System.out.println("Ingrese el valor de la constante S:");
        try { constanteS = inputScanner.nextDouble(); } catch (Exception e) { System.out.println("Por favor, vuelva a intentar ingresando un valor nasdúmerico."); return; }
        System.out.println("Ingrese el valor de la constante M:");
        try { constanteM = inputScanner.nextDouble(); } catch (Exception e){ System.out.println("Por favor, vuelva a intentar ingresando un valor númerico."); return; }
        inputScanner.close();

        //Inicializo el sistema de monitoreo
        Set<Accion> acciones = new HashSet<Accion>(Arrays.asList(new ControlarM(), new ControlarS(), new LoguearProcesamiento()));
        SistemaMonitoreo sistemaMonitoreo = new SistemaMonitoreo(constanteS, constanteM, acciones);

        //Inicializo los sensores
        Sensor sensor_1 = new Sensor(1, sistemaMonitoreo);
        Sensor sensor_2 = new Sensor(2, sistemaMonitoreo);
        Sensor sensor_3 = new Sensor(3, sistemaMonitoreo);
        Sensor sensor_4 = new Sensor(4, sistemaMonitoreo);

        //Creo los threads
        Thread sistemaMonitoreoThread = new Thread(sistemaMonitoreo, "Sistema de Monitoreo");
        Thread sensor1Thread = new Thread(sensor_1, "Sensor 1");
        Thread sensor2Thread = new Thread(sensor_2, "Sensor 2");
        Thread sensor3Thread = new Thread(sensor_3, "Sensor 3");
        Thread sensor4Thread = new Thread(sensor_4, "Sensor 4");

        //Starteo los threads
        sistemaMonitoreoThread.start();
        System.out.println("Ejecutando sistema de monitoreo.");
        sensor1Thread.start();
        sensor2Thread.start();
        sensor3Thread.start();
        sensor4Thread.start();
    }
}