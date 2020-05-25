package ar.com.ada.creditos.entities;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.*;


@Entity // Indica que la clase actuara como entidad
@Table(name = "prestamo") // Indica a que tabla va a referirse ese objeto
public class Prestamo {
    @Id //Es una PK
    @Column(name = "prestamo_id") // Nombre con el que se mapea a la base de datos
    @GeneratedValue(strategy = GenerationType.IDENTITY) //Significa que es autoincremental
    private int prestamoId;
    private BigDecimal importe;
    private Date fecha;
    @Column(name = "cuota")
    private int cuotas;
    @Column(name = "fecha_alta")
    private Date fechaAlta;

    @ManyToOne //Relacion de Muchos a uno
    @JoinColumn(name = "cliente_id", referencedColumnName = "cliente_id") // une las tablas en la columna que designo
    //en name coloque el nombre de la columna en la tabla Prestamo y en referencia el nombre de la columna en la tabla cliente
    private Cliente cliente;


    

    public int getPrestamoId() {
        return prestamoId;
    }

    public void setPrestamoId(int prestamoId) {
        this.prestamoId = prestamoId;
    }

    public BigDecimal getImporte() {
        return importe;
    }

    public void setImporte(BigDecimal importe) {
        this.importe = importe;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public int getCuotas() {
        return cuotas;
    }

    public void setCuotas(int cuotas) {
        this.cuotas = cuotas;
    }

    public Date getFechaAlta() {
        return fechaAlta;
    }

    public void setFechaAlta(Date fechaAlta) {
        this.fechaAlta = fechaAlta;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
        this.cliente.getPrestamos().add(this);//Agrega el prestamo actual a la lista del cliente
    }

    public Prestamo() {
    }

    public Prestamo(int prestamoId, BigDecimal importe, Date fecha, int cuotas, Date fechaAlta, Cliente cliente) {
        this.prestamoId = prestamoId;
        this.importe = importe;
        this.fecha = fecha;
        this.cuotas = cuotas;
        this.fechaAlta = fechaAlta;
        this.cliente = cliente;
    }

    

}