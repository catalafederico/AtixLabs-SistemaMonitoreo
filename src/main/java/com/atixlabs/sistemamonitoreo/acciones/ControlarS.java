public class ControlarS implements Accion {

    @Override
    public void ejecutar(SistemaMonitoreo sistemaMonitoreo) {
        if(sistemaMonitoreo.controlarS()){
            System.out.println("La diferencia entre la medición máxima y mínima es mayor a S.");
        }
    }
}
