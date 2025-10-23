package pojos;

import java.util.Date;

public class Historico {
	private String workoutNombre;
	private int nivel;
	private Date fecha;
	private int tiempoTotal;
	private int ejerciciosCompletados;
	private int ejerciciosTotales;
	private double porcentajeCompletado;

	public Historico() {
	}

	public Historico(String workoutNombre, int nivel, Date fecha, int tiempoTotal, int ejerciciosCompletados,
			int ejerciciosTotales) {
		this.workoutNombre = workoutNombre;
		this.nivel = nivel;
		this.fecha = fecha;
		this.tiempoTotal = tiempoTotal;
		this.ejerciciosCompletados = ejerciciosCompletados;
		this.ejerciciosTotales = ejerciciosTotales;
		this.porcentajeCompletado = ejerciciosTotales > 0 ? (ejerciciosCompletados * 100.0 / ejerciciosTotales) : 0;
	}

	public String getWorkoutNombre() {
		return workoutNombre;
	}

	public void setWorkoutNombre(String workoutNombre) {
		this.workoutNombre = workoutNombre;
	}

	public int getNivel() {
		return nivel;
	}

	public void setNivel(int nivel) {
		this.nivel = nivel;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public int getTiempoTotal() {
		return tiempoTotal;
	}

	public void setTiempoTotal(int tiempoTotal) {
		this.tiempoTotal = tiempoTotal;
	}

	public int getEjerciciosCompletados() {
		return ejerciciosCompletados;
	}

	public void setEjerciciosCompletados(int ejerciciosCompletados) {
		this.ejerciciosCompletados = ejerciciosCompletados;
	}

	public int getEjerciciosTotales() {
		return ejerciciosTotales;
	}

	public void setEjerciciosTotales(int ejerciciosTotales) {
		this.ejerciciosTotales = ejerciciosTotales;
	}

	public double getPorcentajeCompletado() {
		return porcentajeCompletado;
	}

	public void setPorcentajeCompletado(double porcentajeCompletado) {
		this.porcentajeCompletado = porcentajeCompletado;
	}

	@Override
	public String toString() {
		return "Historico [workoutNombre=" + workoutNombre + ", nivel=" + nivel + ", fecha=" + fecha + ", tiempoTotal="
				+ tiempoTotal + ", ejerciciosCompletados=" + ejerciciosCompletados + ", ejerciciosTotales="
				+ ejerciciosTotales + ", porcentajeCompletado=" + porcentajeCompletado + "]";
	}
}