package ar.com.ada.creditos.entities.reportes;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class ReporteCancelaciones {
    
    @Id
    @Column(name="cancelacion_id")
    private int cancelacionId;
    private int cuota;
@Column(name="fecha_cancelacion")
private Date fechaCancelacion;
@Column(name="importe")
private BigDecimal importe;
@Column(name="prestamo_id")
private int prestamoId;
@Column (name = "eliminado")
private int status;

public int getCancelacionId() {
    return cancelacionId;
}

public void setCancelacionId(int cancelacionId) {
    this.cancelacionId = cancelacionId;
}

public int getCuota() {
    return cuota;
}

public void setCuota(int cuota) {
    this.cuota = cuota;
}

public Date getFechaCancelacion() {
    return fechaCancelacion;
}

public void setFechaCancelacion(Date fechaCancelacion) {
    this.fechaCancelacion = fechaCancelacion;
}

public BigDecimal getImporte() {
    return importe;
}

public void setImporte(BigDecimal importe) {
    this.importe = importe;
}

public int getPrestamoId() {
    return prestamoId;
}

public void setPrestamoId(int prestamoId) {
    this.prestamoId = prestamoId;
}

public int getStatus() {
    return status;
}

public void setStatus(int status) {
    this.status = status;
}




}