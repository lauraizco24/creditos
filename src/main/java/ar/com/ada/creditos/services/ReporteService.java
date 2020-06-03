package ar.com.ada.creditos.services;

import java.util.List;

import ar.com.ada.creditos.entities.reportes.ReporteDePrestamos;
import ar.com.ada.creditos.entities.reportes.ReportePrestamoPorCliente;
import ar.com.ada.creditos.managers.PrestamoManager;

public class ReporteService {
    
    PrestamoManager pManager;
    //Constructor
    public ReporteService(PrestamoManager pManager) {
        this.pManager = pManager;
    }
    
    public void imprimirReportePorCliente(int clienteid){
       List<ReportePrestamoPorCliente> lista = pManager.generarReportePrestamoCliente(clienteid); 
        for (ReportePrestamoPorCliente r : lista) {
            System.out.println("****************Datos de Prestamo por Cliente******************");
            System.out.println("El cliente "+ r.getNombre()+ "\n"+" ID: "+ r.getClienteid()+"\n"+ "tiene los siguientes datos: " +"\n"+ "cantidad de Prestamos: "+ r.getCantidad() +"\n"+" Importe Maximo de los Prestamos: "+ r.getMaximo()+"\n"+" Total Prestado:  "+ r.getTotal());
            
        }
    }

    public void imprimirReportePrestamosTotal(){
        List<ReporteDePrestamos> listaPrestamo =pManager.generarReporteDePrestamos();

        for (ReporteDePrestamos rTotal : listaPrestamo) {

            System.out.println("****************Datos de Prestamos Totales******************");
            System.out.println("****************Cantidad de Prestamos: *****************");
            System.out.println(rTotal.getCantidad());
            System.out.println("****************Cantidad Total De Dinero Prestado: ******************");
            System.out.println(rTotal.getTotal());
            
        }
    }

   
}