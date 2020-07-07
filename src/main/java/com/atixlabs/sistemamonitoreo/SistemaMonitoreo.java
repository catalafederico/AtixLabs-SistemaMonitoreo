package com.atixlabs.sistemamonitoreo;

import com.atixlabs.sistemamonitoreo.acciones.Accion;

import java.io.IOException;
import java.util.*;
import java.util.logging.*;
import java.util.stream.Collectors;

public class SistemaMonitoreo implements Runnable {

    private Double S;
    private Double M;
    private List<Mensaje> colaMensajes = Collections.synchronizedList(new ArrayList<Mensaje>());
    private List<Mensaje> mensajesAProcesar = new ArrayList<Mensaje>();
    private Set<Accion> acciones;
    private final static Logger LOGGER = Logger.getLogger(SistemaMonitoreo.class.getName());

    public SistemaMonitoreo(Double S, Double M, Set<Accion> acciones) throws IOException {
        this.S = S;
        this.M = M;
        this.acciones = acciones;
        this.inicializarLogger();
    }

    public Double getS() {
        return this.S;
    }

    public Double getM() {
        return this.M;
    }

    public Set<Accion> getAcciones() {
        return this.acciones;
    }

    public List<Mensaje> getColaMensajes() {
        return this.colaMensajes;
    }

    public List<Mensaje> getMensajesAProcesar() { return this.mensajesAProcesar; }

    public void inicializarLogger() throws IOException {
        Handler consoleHandler = new ConsoleHandler();
        Handler fileHandler = new FileHandler("./mensajesRecibidos.log", false);
        SimpleFormatter simpleFormatter = new SimpleFormatter();
        fileHandler.setFormatter(simpleFormatter);
        consoleHandler.setLevel(Level.OFF);
        fileHandler.setLevel(Level.INFO);
        LOGGER.addHandler(consoleHandler);
        LOGGER.addHandler(fileHandler);
    }

    @Override
    public void run(){
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            public void run()
            {
                prepararMensajesAProcesar();
                ejecutarAcciones();
            }
        };
        timer.scheduleAtFixedRate( task, 10000L ,10000L);

    }

    public void recibirMensaje(Mensaje mensaje){
        this.agregarMensajeACola(mensaje);
        LOGGER.info("Mensaje recibido. Sensor: " + mensaje.getSensorID() +
                " Medición: " + mensaje.getMedicion());
    }

    public boolean controlarS(){
        return this.getDiferenciaMedicionMaximaMinima() > this.getS();
    }

    public boolean controlarM(){
        return this.getPromedioMediciones() > this.getM();
    }

    public void prepararMensajesAProcesar(){
        this.limpiarMensajesAProcesar();
        this.copiarMensajesAProcesar();
    }

    public void ejecutarAcciones(){
        this.getAcciones().forEach(accion -> accion.ejecutar(this));
    }

    public void agregarMensajeACola(Mensaje mensaje) {
        this.getColaMensajes().add(mensaje);
    }

    public void limpiarMensajesAProcesar() {
        this.getMensajesAProcesar().clear();
    }

    public void copiarMensajesAProcesar() {
        this.getMensajesAProcesar().addAll(this.getColaMensajes());
    }

    public ArrayList<Double> getMediciones(){
        return this.getMensajesAProcesar().stream().map(Mensaje::getMedicion).collect(Collectors.toCollection(ArrayList::new));
    }

    public Double getMedicionMaxima(){ return Collections.max(getMediciones()); }

    public Double getMedicionMinima(){ return Collections.min(getMediciones()); }

    public Double getDiferenciaMedicionMaximaMinima(){
        return (this.getMedicionMaxima() - this.getMedicionMinima());
    }

    public Double getSumaMediciones() {
        return this.getMediciones().stream().mapToDouble(Double::doubleValue).sum();
    }

    public Integer getCantidadMediciones() { return this.getMediciones().size(); }

    public Double getPromedioMediciones() {
        return this.getSumaMediciones()/this.getCantidadMediciones();
    }

}
