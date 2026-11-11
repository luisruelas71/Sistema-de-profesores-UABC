package mx.desarrollo.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import jakarta.persistence.Transient;

@Entity
@Table(name = "tarea")
public class Tarea {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tarea")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_profesor", nullable = false)
    private Profesor profesor;

    @Column(name = "titulo", nullable = false, length = 50)
    private String titulo;

    @Column(name = "descripcion", nullable = false, length = 200)
    private String descripcion;

    @Column(name = "fecha_limite", nullable = false)
    private LocalDateTime fechaLimite;

    @Column(name = "prioridad", nullable = false, length = 7)
    private String prioridad;

    @Column(name = "completado", nullable = false)
    private Boolean completado = false;


    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public Profesor getProfesor() { return profesor; }
    public void setProfesor(Profesor profesor) { this.profesor = profesor; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public LocalDateTime getFechaLimite() { return fechaLimite; }
    public void setFechaLimite(LocalDateTime fechaLimite) { this.fechaLimite = fechaLimite; }

    public String getPrioridad() { return prioridad; }
    public void setPrioridad(String prioridad) { this.prioridad = prioridad; }

    public Boolean getCompletado() { return completado; }
    public void setCompletado(Boolean completado) { this.completado = completado; }

    @Transient
    public Integer getPesoPrioridad() {
        if (this.prioridad == null) return 0;

        switch (this.prioridad.toLowerCase()) {
            case "media":
                return 1;
            case "alta":
                return 2;
            case "urgente":
                return 3;
            default:
                return 0;
        }
    }
}