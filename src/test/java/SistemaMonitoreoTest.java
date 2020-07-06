import com.atixlabs.sistemamonitoreo.Mensaje;
import com.atixlabs.sistemamonitoreo.SistemaMonitoreo;
import com.atixlabs.sistemamonitoreo.acciones.Accion;
import com.atixlabs.sistemamonitoreo.acciones.ControlarM;
import com.atixlabs.sistemamonitoreo.acciones.ControlarS;
import com.atixlabs.sistemamonitoreo.acciones.LoguearProcesamiento;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.LogManager;

import static org.junit.Assert.*;

public class SistemaMonitoreoTest {

    private SistemaMonitoreo sistemaMonitoreo;

    @Before
    public void setUp() throws IOException {
        Set<Accion> acciones = new HashSet<Accion>(Arrays.asList(new ControlarM(), new ControlarS(), new LoguearProcesamiento()));
        sistemaMonitoreo = new SistemaMonitoreo(70.00, 50.00, acciones);
        //Reseteo el LogManager para desactivar los logs durante los tests.
        LogManager.getLogManager().reset();
        sistemaMonitoreo.recibirMensaje(new Mensaje(1, 80.00));
        sistemaMonitoreo.recibirMensaje(new Mensaje(2, 60.00));
        sistemaMonitoreo.prepararMensajesAProcesar();
    }

    @Test
    public void getColaMensajesTest(){
        assertEquals(sistemaMonitoreo.getColaMensajes().size(), 2);
    }

    @Test
    public void prepararMensajesAProcesarTest(){
        assertEquals(sistemaMonitoreo.getCantidadMediciones().intValue(), 2);
    }

    @Test
    public void getSumaMedicionesTest(){
        assertEquals(sistemaMonitoreo.getSumaMediciones().intValue(), 140);
    }

    @Test
    public void getPromedioMedicionesTest(){
        assertEquals(sistemaMonitoreo.getPromedioMediciones().intValue(), 70);
    }

    @Test
    public void getMedicionMaximaTest(){
        assertEquals(sistemaMonitoreo.getMedicionMaxima().intValue(), 80);
    }

    @Test
    public void getMedicionMinimaTest(){
        assertEquals(sistemaMonitoreo.getMedicionMinima().intValue(), 60);
    }

    @Test
    public void getDiferenciaMedicionMaximaMinimaTest(){
        assertEquals(sistemaMonitoreo.getDiferenciaMedicionMaximaMinima().intValue(), 20);
    }

    @Test
    public void controlarSTest(){
        assertFalse(sistemaMonitoreo.controlarS());
    }

    @Test
    public void controlarMTest(){
        assertTrue(sistemaMonitoreo.controlarM());
    }
}
