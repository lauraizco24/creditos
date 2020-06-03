package ar.com.ada.creditos.entities;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="cancelacion")
public class Cancelacion {

    @Id
    @Column(name="cancelacion_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int cancelacionId;
    @Column(name="fecha_cancelacion")
    private Date fechaCancelacion;
    @ManyToOne //Relacion de Muchos a uno
    @JoinColumn(name = "prestamo_id", referencedColumnName = "prestamo_id") // une las tablas en la columna que designo
    //en name coloque el nombre de la columna en la tabla Prestamo y en referencia el nombre de la columna en la tabla cliente
    private Prestamo prestamo;
    private BigDecimal importe;
    private int cuota;

    @ManyToOne
    @JoinColumn(name = "prestamo_id", referencedColumnName = "prestamo_id")
    @JoinColumn(name = "cliente_id", referencedColumnName = "cliente_id")
    private Cliente cliente;

    public Cancelacion(int cancelacionId, Date fechaCancelacion, Prestamo prestamo, BigDecimal importe, int cuota) {
        this.cancelacionId = cancelacionId;
        this.fechaCancelacion = fechaCancelacion;
        this.prestamo = prestamo;
        this.importe = importe;
        this.cuota = cuota;
    }

    public Cancelacion() {
    }

    public int getCancelacionId() {
        return cancelacionId;
    }

    public void setCancelacionId(int cancelacionId) {
        this.cancelacionId = cancelacionId;
    }

    public Date getFechaCancelacion() {
        return fechaCancelacion;
    }

    public void setFechaCancelacion(Date fechaCancelacion) {
        this.fechaCancelacion = fechaCancelacion;
    }

    public Prestamo getPrestamo() {
        return prestamo;
    }

    public void setPrestamo(Prestamo prestamo) {
        this.prestamo = prestamo;
    }

    public BigDecimal getImporte() {
        return importe;
    }

    public void setImporte(BigDecimal importe) {
        this.importe = importe;
    }

    public int getCuota() {
        return cuota;
    }

    public void setCuota(int cuota) {
        this.cuota = cuota;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }



    

    
}