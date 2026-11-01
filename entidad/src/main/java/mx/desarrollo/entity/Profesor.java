package mx.desarrollo.entity;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "profesor")
public class Profesor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_profesor")
    private Integer id;

    @Column(name = "correo_institucional", nullable = false, length = 60, unique = true)
    private String correoInstitucional;

    @Column(name = "contraseña", nullable = false, length = 60, columnDefinition = "CHAR(60)")
    private String contraseña;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_tipo", nullable = false)
    private TipoProfesor tipo;

    @OneToMany(mappedBy = "profesor")
    private List<Firma> firmas = new ArrayList<>();

    @OneToMany(mappedBy = "profesor")
    private List<Tarea> tareas = new ArrayList<>();

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getCorreoInstitucional() { return correoInstitucional; }
    public void setCorreoInstitucional(String correoInstitucional) { this.correoInstitucional = correoInstitucional; }

    public String getContrasena() { return contraseña; }
    public void setContrasena(String contrasena) { this.contraseña = contrasena; }

    public TipoProfesor getTipo() { return tipo; }
    public void setTipo(TipoProfesor tipo) { this.tipo = tipo; }

    public List<Firma> getFirmas() { return firmas; }
    public void setFirmas(List<Firma> firmas) { this.firmas = firmas; }

    public List<Tarea> getTareas() { return tareas; }
    public void setTareas(List<Tarea> tareas) { this.tareas = tareas; }
}