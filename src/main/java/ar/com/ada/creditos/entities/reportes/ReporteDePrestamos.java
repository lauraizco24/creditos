package ar.com.ada.creditos.entities.reportes;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
public class ReporteDePrestamos {

    @Id
private int cantidad;
private BigDecimal total;



public int getCantidad() {
    return cantidad;
}

public void setCantidad(int cantidad) {
    this.cantidad = cantidad;
}

public BigDecimal getTotal() {
    return total;
}

public void setTotal(BigDecimal total) {
    this.total = total;
}



    
}