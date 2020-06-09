package ar.com.ada.creditos.services;

import java.math.BigDecimal;
import java.util.Date;


import ar.com.ada.creditos.entities.Cancelacion;

import ar.com.ada.creditos.managers.CancelacionManager;
import ar.com.ada.creditos.managers.PrestamoManager;

public class CancelacionService {
    CancelacionManager cancelacionManager;
    PrestamoManager pManager;

    public CancelacionService(CancelacionManager cancelacionManager, PrestamoManager pManager) {
        this.cancelacionManager = cancelacionManager;
        this.pManager = pManager;
    }


    
    public Cancelacion generarImprimirCancelacion(int prestamoId, BigDecimal importe,int cuota,Date fechaCancelacion){
        Cancelacion cancelacion = new Cancelacion();
        cancelacion.setPrestamo(pManager.buscarPorIdPrestamo(prestamoId));
        cancelacion.setCuota(cuota);
        cancelacion.setFechaCancelacion(fechaCancelacion);
        cancelacion.setImporte(importe);
        cancelacion.setEliminado(0);
       
        cancelacionManager.create(cancelacion);

System.out.println("Se ha generado exitosamente una nueva cancelacion en base a los siguientes datos: ");
System.out.println(cancelacion.getCancelacionId() + " "+ cancelacion.getCuota()+ " "+ cancelacion.getImporte()+ " "+cancelacion.getPrestamo().getPrestamoId());
return cancelacion;

    
    
    }

    public void eliminarUnaCancelacion(int cancelacionId) {
        
     //Forma 1
     if(cancelacionManager.eliminacionLogica(cancelacionId)){
        System.out.println(" Se ha desactivado exitosamente el registro de cancelacion con id : "+ cancelacionId);
    
    }else {
        System.out.println("Ocurrio un error al actualizarlo");
    }

    //Forma 2
    /*cancelacion.setEliminado(1);
       cancelacionManager.update(cancelacion);
       System.out.println(" Se ha desactivado exitosamente el registro de cancelacion con id : "+ cancelacionId);
*/
        
    }
    



    

    

	
    
}