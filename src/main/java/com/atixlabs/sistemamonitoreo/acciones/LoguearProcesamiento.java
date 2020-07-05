import java.io.IOException;
import java.util.logging.*;

public class LoguearProcesamiento implements Accion {

    private final static Logger LOGGER = Logger.getLogger(LoguearProcesamiento.class.getName());

    public LoguearProcesamiento() throws IOException {
        this.inicializarLogger();
    }

    public void inicializarLogger() throws IOException {
        Handler consoleHandler = new ConsoleHandler();
        Handler fileHandler = new FileHandler("./procesamientoInformacion.log", false);
        SimpleFormatter simpleFormatter = new SimpleFormatter();
        fileHandler.setFormatter(simpleFormatter);
        consoleHandler.setLevel(Level.OFF);
        fileHandler.setLevel(Level.INFO);
        LOGGER.addHandler(consoleHandler);
        LOGGER.addHandler(fileHandler);
    }

    @Override
    public void ejecutar(SistemaMonitoreo sistemaMonitoreo) {
        LOGGER.info("Procesamiento de información." +
                "\nValor máximo:" + sistemaMonitoreo.getMedicionMaxima() +
                "\nValor mínima:" + sistemaMonitoreo.getMedicionMinima() +
                "\nDiferencia entre valor máximo y mínimo: " + sistemaMonitoreo.getDiferenciaMedicionMaximaMinima() +
                "\nSuma de mediciones: " + sistemaMonitoreo.getSumaMediciones() +
                "\nCantidad de mediciones: " + sistemaMonitoreo.getCantidadMediciones() +
                "\nPromedio de mediciones: " + sistemaMonitoreo.getPromedioMediciones());
    }
}
