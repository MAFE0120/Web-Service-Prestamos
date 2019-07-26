package web.services;

import control.SocioControl;
import excepciones.AppException;
import java.sql.Connection;
import java.util.List;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import modelo.vo.Socio;


@WebService(serviceName = "prestamosWS")
public class prestamosWS {
    
    @WebMethod(operationName = "verificarPrestamo")

    public List<String> verificarPrestamo(@WebParam(name = "monto") String monto) throws Exception {
        Connection cnn=modelo.conexion.Conexion.getConexionDB();
        SocioControl sc = new SocioControl(cnn);
        List<String> socio= sc.socioPrestamista(monto);
        return socio;
    }
}
