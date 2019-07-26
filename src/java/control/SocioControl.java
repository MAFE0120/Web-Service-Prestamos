package control;

import excepciones.AppException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import modelo.dao.SocioDAO;
import modelo.vo.Socio;

public class SocioControl {

    Connection cnn;
    SocioDAO socDAO;

    public SocioControl(Connection cnn) {
        this.cnn = cnn;
        socDAO = new SocioDAO(cnn);
    }

    public List<String> socioPrestamista(String monto) throws AppException {
        try {
            Socio socioPrestamo = null;
            List<String> lista = new ArrayList<>();
            String valorTasa = socDAO.obtenerSocios().get(0).getTasa();

            for (Socio datos : socDAO.obtenerSocios()) {
                double maximo = Double.parseDouble(datos.getMonto_maximo());
                double montoPedido = Double.parseDouble(monto);
                if (maximo >= montoPedido) {
                    if (Float.parseFloat(datos.getTasa()) <= Float.valueOf(valorTasa)) {
                        socioPrestamo = datos;
                        valorTasa = datos.getTasa();
                    }
                }
            }

            if (socioPrestamo != null) {
                int VA = 0, n = 36, total = 0, cuota = 0;
                double i = 0.0, VF = 0.0;

                VA = Integer.parseInt(monto);
                i = Double.parseDouble(socioPrestamo.getTasa()) / 100;
                VF = VA * (1 + n * i);
                total = (int) VF;
                cuota = total / n;
                
                lista.add(socioPrestamo.getNombre_socio());
                lista.add(String.valueOf(total));
                lista.add(String.valueOf(cuota));
                lista.add(socioPrestamo.getTasa());
            } else {
                lista.add("No hay socio disponible");
            }
            return lista;
        } catch (Exception e) {
            throw new AppException(e);
        }
    }

}
