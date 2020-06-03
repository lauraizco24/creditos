package ar.com.ada.creditos.entities.reportes;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import ar.com.ada.creditos.entities.Prestamo;
@Entity
public class ReportePrestamoPorCliente {

@Id
@Column(name = "cliente_id") // Nombre con el que se mapea a la base de datos
private int clienteid;
private String nombre;
private int cantidad;
private BigDecimal maximo;
private BigDecimal total;

public int getClienteid() {
    return clienteid;
}

public void setClienteid(int clienteid) {
    this.clienteid = clienteid;
}

public String getNombre() {
    return nombre;
}

public void setNombre(String nombre) {
    this.nombre = nombre;
}

public int getCantidad() {
    return cantidad;
}

public void setCantidad(int cantidad) {
    this.cantidad = cantidad;
}

public BigDecimal getMaximo() {
    return maximo;
}

public void setMaximo(BigDecimal maximo) {
    this.maximo = maximo;
}

public BigDecimal getTotal() {
    return total;
}

public void setTotal(BigDecimal total) {
    this.total = total;
}


  

}