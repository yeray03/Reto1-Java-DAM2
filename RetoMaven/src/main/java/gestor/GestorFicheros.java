package gestor;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import controlador.EjercicioControlador;
import controlador.HistoricoControlador;
import controlador.UsuarioControlador;
import controlador.WorkoutControlador;
import pojos.Ejercicio;
import pojos.Historico;
import pojos.Usuario;
import pojos.Workout;

public class GestorFicheros {

	private static GestorFicheros instancia = null;
	private Path rutaDirectorio = Paths.get(System.getProperty("user.home"), "Documents", "SpinningCat");;

	public static GestorFicheros getInstance() {
		if (instancia == null) {
			instancia = new GestorFicheros();
		}
		return instancia;
	}

	public void guardarDatos() {
		// El fichero se va a guardar en el directorio SpinningCat en la carpeta
		// Documentos del usuario (nadie usa "trastero" :P)
		try {
			Files.createDirectories(rutaDirectorio);
			guardarUsuarios();
			guardarWorkouts();
			guardarEjercicios();
			guardarHistorico();
		} catch (IOException e) {
			System.err.println("Error al crear el directorio: " + rutaDirectorio);
			e.printStackTrace();
		}

	}

	// GUARDAR USUARIOS
	private void guardarUsuarios() {
		File file = null;
		FileOutputStream fos = null;
		ObjectOutputStream oos = null;

		try {
			// crear el fichero usuarios.dat
			file = new File(rutaDirectorio.toString(), "Usuarios.dat");
			// abrir el flujo de salida
			fos = new FileOutputStream(file);
			// abrir el flujo de objetos
			oos = new ObjectOutputStream(fos);

			// recogemos todos los usuarios de la base de datos
			UsuarioControlador controlador = UsuarioControlador.getInstance();
			ArrayList<Usuario> usuarios = controlador.obtenerTodosUsuarios();
			// escribimos cada usuario en el fichero
			for (Usuario usuario : usuarios) {
				oos.writeObject(usuario);
			}

		} catch (IOException | ExecutionException | InterruptedException e) {
			System.out.println("Error al guardar los usuarios en el fichero.");
			e.printStackTrace();
		} finally {
			// Close the ObjectOutputStream
			try {
				if (oos != null)
					oos.close();
			} catch (IOException e) {
				// Nothing to do here...
			}
			// Close the FileOutputStream
			try {
				if (fos != null)
					fos.close();
			} catch (IOException e) {
				// Nothing to do here...
			}
		}
	}

	// GUARDAR WORKOUTS
	private void guardarWorkouts() {
		File file = null;
		FileOutputStream fos = null;
		ObjectOutputStream oos = null;
		// lo mismo que lo de arriba pero con workouts (te lo lees)
		try {
			file = new File(rutaDirectorio.toString(), "Workouts.dat");
			fos = new FileOutputStream(file);
			oos = new ObjectOutputStream(fos);

			// recogemos todos los usuarios de la base de datos
			WorkoutControlador controlador = WorkoutControlador.getInstanceControlador();
			ArrayList<Workout> workouts = controlador.getWorkouts();

			for (Workout workout : workouts) {
				oos.writeObject(workout);
			}

		} catch (IOException | ExecutionException | InterruptedException e) {
			System.out.println("Error al guardar los workouts en el fichero.");
			e.printStackTrace();
		} finally {
			// Close the ObjectOutputStream
			try {
				if (oos != null)
					oos.close();
			} catch (IOException e) {
				// Nothing to do here...
			}
			// Close the FileOutputStream
			try {
				if (fos != null)
					fos.close();
			} catch (IOException e) {
				// Nothing to do here...
			}
		}
	}

	// GUARDAR EJERCICIOS
	private void guardarEjercicios() {
		File file = null;
		FileOutputStream fos = null;
		ObjectOutputStream oos = null;
		// lo mismo que lo de arriba pero con workouts (te lo lees)
		try {
			file = new File(rutaDirectorio.toString(), "Ejercicios.dat");
			fos = new FileOutputStream(file);
			oos = new ObjectOutputStream(fos);

			// recogemos todos los usuarios de la base de datos
			EjercicioControlador controlador = EjercicioControlador.getInstanceControlador();
			ArrayList<Ejercicio> ejercicios = controlador.getTodosEjercicios();

			for (Ejercicio ejercicio : ejercicios) {
				oos.writeObject(ejercicio);
			}

		} catch (IOException | ExecutionException | InterruptedException e) {
			System.out.println("Error al guardar los ejercicios en el fichero.");
			e.printStackTrace();
		} finally {
			// Close the ObjectOutputStream
			try {
				if (oos != null)
					oos.close();
			} catch (IOException e) {
				// Nothing to do here...
			}
			// Close the FileOutputStream
			try {
				if (fos != null)
					fos.close();
			} catch (IOException e) {
				// Nothing to do here...
			}
		}
	}

	private void guardarHistorico() {
		try {
			// Crear la fábrica de constructores de documentos
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			// Crear el constructor de documentos
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			// Crear el documento (en memoria)
			Document doc = dBuilder.newDocument();
			// Crear el elemento raíz
			Element root = doc.createElement("historicos");
			doc.appendChild(root); // añadir root al documento
			// recoger todos los usuarios de la base de datos
			UsuarioControlador controlador = UsuarioControlador.getInstance();
			ArrayList<Usuario> usuarios = controlador.obtenerTodosUsuarios();
			// Obtener el histórico de cada usuario
			for (Usuario usuario : usuarios) {
				// obtener el histórico del usuario
				HistoricoControlador historicoControlador = HistoricoControlador.getInstance();
				ArrayList<Historico> historicos = historicoControlador.getHistoricoUsuario(usuario);
				// Crear el elemento hijo (usuario)
				Element user = doc.createElement("usuario");
				// añadir atributo "id" al elemento usuario
				Attr attr = doc.createAttribute("id");
				attr.setValue(String.valueOf(usuario.getNickname()));
				user.setAttributeNode(attr);
				// crear los elementos hijo del usuario
				for (Historico historico : historicos) {
					Element historicoElement = doc.createElement("historico");
					user.appendChild(historicoElement);

					Element ejerciciosCompletados = doc.createElement("ejerciciosCompletados");
					ejerciciosCompletados
							.appendChild(doc.createTextNode(String.valueOf(historico.getEjerciciosCompletados())));
					historicoElement.appendChild(ejerciciosCompletados);
					// lo mismo con los demás atributos
					Element ejerciciosTotales = doc.createElement("ejerciciosTotales");
					ejerciciosTotales.appendChild(doc.createTextNode(String.valueOf(historico.getEjerciciosTotales())));
					historicoElement.appendChild(ejerciciosTotales);
					Element fecha = doc.createElement("fecha");
					fecha.appendChild(doc.createTextNode(historico.getFecha()));
					historicoElement.appendChild(fecha);
					Element nivel = doc.createElement("nivel");
					nivel.appendChild(doc.createTextNode(String.valueOf(historico.getNivel())));
					historicoElement.appendChild(nivel);
					Element porcentajeCompletado = doc.createElement("porcentajeCompletado");
					porcentajeCompletado
							.appendChild(doc.createTextNode(String.valueOf(historico.getPorcentajeCompletado())));
					historicoElement.appendChild(porcentajeCompletado);
					Element tiempoPrevisto = doc.createElement("tiempoPrevisto");
					tiempoPrevisto.appendChild(doc.createTextNode(String.valueOf(historico.getTiempoPrevisto())));
					historicoElement.appendChild(tiempoPrevisto);
					Element tiempoTotal = doc.createElement("tiempoTotal");
					tiempoTotal.appendChild(doc.createTextNode(String.valueOf(historico.getTiempoTotal())));
					historicoElement.appendChild(tiempoTotal);
					Element workoutNombre = doc.createElement("workoutNombre");
					workoutNombre.appendChild(doc.createTextNode(historico.getWorkoutNombre()));
					historicoElement.appendChild(workoutNombre);
					// añadir el histórico al usuario
					user.appendChild(historicoElement);
				}
				root.appendChild(user);
				// escribir el contenido en un fichero XML
				TransformerFactory transformerFactory = TransformerFactory.newInstance();
				Transformer transformer = transformerFactory.newTransformer();
				// DOM
				DOMSource source = new DOMSource(doc);
				StreamResult result = new StreamResult(new File(rutaDirectorio.toString(), "historico.xml"));
				transformer.transform(source, result);
			}

		} catch (Exception e) {
			System.out.println("Error al guardar el histórico en el fichero XML.");
			e.printStackTrace();
		}
	}

	public void guardarHistoricoUsuario(Usuario usuario, Historico historico) {
		try {
			// Crear la fábrica de constructores de documentos
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			// Crear el constructor de documentos
			DocumentBuilder builder = factory.newDocumentBuilder();
			// Parsear el fichero XML existente
			Document doc = builder.parse(new File(rutaDirectorio.toString(), "historico.xml"));
			doc.getDocumentElement().normalize();
			// Crear el elemento hijo (usuario)
			NodeList rootNodeList = doc.getElementsByTagName("usuario");
			// buscar el usuario en el XML
			for (int i = 0; i < rootNodeList.getLength(); i++) {
				Node node = rootNodeList.item(i);
				if (node.getNodeType() == Node.ELEMENT_NODE) {
					Element element = (Element) node;
					String id = element.getAttribute("id");
					if (id.equalsIgnoreCase(usuario.getNickname())) {
						Element historicoElement = doc.createElement("historico");
						node.appendChild(historicoElement);
						Element ejerciciosCompletados = doc.createElement("ejerciciosCompletados");
						ejerciciosCompletados
								.appendChild(doc.createTextNode(String.valueOf(historico.getEjerciciosCompletados())));
						historicoElement.appendChild(ejerciciosCompletados);
						// lo mismo con los demás atributos
						Element ejerciciosTotales = doc.createElement("ejerciciosTotales");
						ejerciciosTotales
								.appendChild(doc.createTextNode(String.valueOf(historico.getEjerciciosTotales())));
						historicoElement.appendChild(ejerciciosTotales);
						Element fecha = doc.createElement("fecha");
						fecha.appendChild(doc.createTextNode(historico.getFecha()));
						historicoElement.appendChild(fecha);
						Element nivel = doc.createElement("nivel");
						nivel.appendChild(doc.createTextNode(String.valueOf(historico.getNivel())));
						historicoElement.appendChild(nivel);
						Element porcentajeCompletado = doc.createElement("porcentajeCompletado");
						porcentajeCompletado
								.appendChild(doc.createTextNode(String.valueOf(historico.getPorcentajeCompletado())));
						historicoElement.appendChild(porcentajeCompletado);
						Element tiempoPrevisto = doc.createElement("tiempoPrevisto");
						tiempoPrevisto.appendChild(doc.createTextNode(String.valueOf(historico.getTiempoPrevisto())));
						historicoElement.appendChild(tiempoPrevisto);
						Element tiempoTotal = doc.createElement("tiempoTotal");
						tiempoTotal.appendChild(doc.createTextNode(String.valueOf(historico.getTiempoTotal())));
						historicoElement.appendChild(tiempoTotal);
						Element workoutNombre = doc.createElement("workoutNombre");
						workoutNombre.appendChild(doc.createTextNode(historico.getWorkoutNombre()));
						historicoElement.appendChild(workoutNombre);
						// añadir el histórico al usuario
						node.appendChild(historicoElement);

					}
				}
			}

			// escribir el contenido en un fichero XML
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			// DOM
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File(rutaDirectorio.toString(), "historico.xml"));
			transformer.transform(source, result);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	// LEER USUARIOS
	public void leerUsuarios() {
		File file = null;
		FileInputStream fis = null;
		ObjectInputStream ois = null;
		try {
			file = new File(rutaDirectorio.toString(), "usuarios.dat");
			fis = new FileInputStream(file);
			ois = new ObjectInputStream(fis);

			while (fis.getChannel().position() < fis.getChannel().size()) {
				Usuario usuario = (Usuario) ois.readObject();
				System.out.println("Usuario leído: " + usuario.toString());
			}
		} catch (Exception e) {
			System.out.println("Error al leer el fichero usuarios.dat.");
		}
	}

	// LEER WORKOUTS
	public ArrayList<Workout> leerWorkoutsHastaNivel(int nivel) {
		File file = null;
		FileInputStream fis = null;
		ObjectInputStream ois = null;
		ArrayList<Workout> workouts = new ArrayList<Workout>();
		try {
			file = new File(rutaDirectorio.toString(), "workouts.dat");
			fis = new FileInputStream(file);
			ois = new ObjectInputStream(fis);
			while (fis.getChannel().position() < fis.getChannel().size()) {
				Workout workout = (Workout) ois.readObject();
				if (workout != null && workout.getNivel() <= nivel)
					workouts.add(workout);
			}
		} catch (Exception e) {
			System.out.println("Error al leer el fichero workouts.dat.");
			e.printStackTrace();
		}
		return workouts;
	}

	// LEER WORKOUTS POR NIVEL
	public ArrayList<Workout> leerWorkoutsPorNivel(int nivel) {
		File file = null;
		FileInputStream fis = null;
		ObjectInputStream ois = null;
		ArrayList<Workout> workouts = new ArrayList<Workout>();
		try {
			file = new File(rutaDirectorio.toString(), "workouts.dat");
			fis = new FileInputStream(file);
			ois = new ObjectInputStream(fis);
			while (fis.getChannel().position() < fis.getChannel().size()) {
				Workout workout = (Workout) ois.readObject();
				if (workout != null && workout.getNivel() == nivel)
					workouts.add(workout);
			}
		} catch (Exception e) {
			System.out.println("Error al leer el fichero workouts.dat.");
			e.printStackTrace();
		}
		return workouts;
	}

	// LEER EJERCICIOS
	public void leerEjercicios() {
		File file = null;
		FileInputStream fis = null;
		ObjectInputStream ois = null;
		try {
			file = new File(rutaDirectorio.toString(), "ejercicios.dat");
			fis = new FileInputStream(file);
			ois = new ObjectInputStream(fis);

			while (fis.getChannel().position() < fis.getChannel().size()) {
				Ejercicio ejercicio = (Ejercicio) ois.readObject();
				System.out.println("Ejercicio leído: " + ejercicio.toString());
			}

		} catch (Exception e) {
			System.out.println("Error al leer el fichero ejercicios.dat.");
			e.printStackTrace();
		}

	}

	// LEER HISTORICO
	public ArrayList<Historico> leerHistorico(Usuario usuario) {
		ArrayList<Historico> ret = new ArrayList<Historico>();
		try {
			// Crear la fabrica de constructores de documentos
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			// Parsear el fichero XML
			Document doc = dBuilder.parse(new File(rutaDirectorio.toString(), "historico.xml"));
			doc.getDocumentElement().normalize();
			// ROOT
			Element root = doc.getDocumentElement();
			System.out.println("Elemento raíz: " + root.getNodeName());

			// OBTENER LA LISTA DE NODOS "usuario"
			NodeList usuarios = doc.getElementsByTagName("usuario");
			// OBTENER CADA USUARIO
			for (int i = 0; i < usuarios.getLength(); i++) {
				Node nodoUsuario = usuarios.item(i);
				if (nodoUsuario.getNodeType() == Node.ELEMENT_NODE) {
					Element element = (Element) nodoUsuario;
					if (element.getAttribute("id").equalsIgnoreCase(usuario.getNickname())) {
						// crear objeto historico
						// OBTENER LA LISTA DE NODOS "historico"
						NodeList historicos = element.getElementsByTagName("historico");
						// OBTENER CADA HISTORICO
						for (int j = 0; j < historicos.getLength(); j++) {
							Node nodoHistorico = historicos.item(j);
							if (nodoHistorico.getNodeType() == Node.ELEMENT_NODE) {
								Element historicoElement = (Element) nodoHistorico;
								Historico historico = new Historico();
								// OBTENER LA LISTA DE NODOS "ejerciciosCompletados"
								NodeList completados = historicoElement.getElementsByTagName("ejerciciosCompletados");
								for (int k = 0; k < completados.getLength(); k++) {
									// OBTENER CADA NODO "ejerciciosCompletados"
									Node nodeCompletados = completados.item(k);
									if (nodeCompletados.getNodeType() == Node.ELEMENT_NODE) {
										Element ejerciciosCompletadosElement = (Element) completados.item(k);
										// ASIGNAR VALOR Al OBJETO HISTORICO
										historico.setEjerciciosCompletados(
												Integer.parseInt(ejerciciosCompletadosElement.getTextContent()));
										// OBTENER LA LISTA DE NODOS "ejerciciosTotales"
										NodeList totales = historicoElement.getElementsByTagName("ejerciciosTotales");
										for (int l = 0; l < totales.getLength(); l++) {
											// OBTENER CADA NODO "ejerciciosTotales"
											Node nodeTotales = totales.item(l);
											if (nodeTotales.getNodeType() == Node.ELEMENT_NODE) {
												Element ejerciciosTotalesElement = (Element) totales.item(l);
												// ASIGNAR VALOR Al OBJETO HISTORICO
												historico.setEjerciciosTotales(
														Integer.parseInt(ejerciciosTotalesElement.getTextContent()));
												// OBTENER LA LISTA DE NODOS "fecha"
												NodeList fechas = historicoElement.getElementsByTagName("fecha");
												for (int m = 0; m < fechas.getLength(); m++) {
													// OBTENER CADA NODO "fecha"
													Node nodeFechas = fechas.item(m);
													if (nodeFechas.getNodeType() == Node.ELEMENT_NODE) {
														Element fechaElement = (Element) fechas.item(m);
														// ASIGNAR VALOR Al OBJETO HISTORICO
														historico.setFecha(fechaElement.getTextContent());
														// OBTENER LA LISTA DE NODOS "nivel"
														NodeList niveles = historicoElement
																.getElementsByTagName("nivel");
														for (int n = 0; n < niveles.getLength(); n++) {
															// OBTENER CADA NODO "nivel"
															Node nodeNiveles = niveles.item(n);
															if (nodeNiveles.getNodeType() == Node.ELEMENT_NODE) {
																Element nivelElement = (Element) niveles.item(n);
																// ASIGNAR VALOR Al OBJETO HISTORICO
																historico.setNivel(Integer
																		.parseInt(nivelElement.getTextContent()));
																// OBTENER LA LISTA DE NODOS "porcentajeCompletado"
																NodeList porcentajes = historicoElement
																		.getElementsByTagName("porcentajeCompletado");
																for (int o = 0; o < porcentajes.getLength(); o++) {
																	// OBTENER CADA NODO "porcentajeCompletado"
																	Node nodePorcentajes = porcentajes.item(o);
																	if (nodePorcentajes
																			.getNodeType() == Node.ELEMENT_NODE) {
																		Element porcentajeElement = (Element) porcentajes
																				.item(o);
																		// ASIGNAR VALOR Al OBJETO HISTORICO
																		historico.setPorcentajeCompletado(
																				Double.parseDouble(porcentajeElement
																						.getTextContent()));
																		// OBTENER LA LISTA DE NODOS "tiempoPrevisto"
																		NodeList tiemposPrevistos = historicoElement
																				.getElementsByTagName("tiempoPrevisto");
																		for (int p = 0; p < tiemposPrevistos
																				.getLength(); p++) {
																			// OBTENER CADA NODO "tiempoPrevisto"
																			Node nodeTiemposPrevistos = tiemposPrevistos
																					.item(p);
																			if (nodeTiemposPrevistos
																					.getNodeType() == Node.ELEMENT_NODE) {
																				Element tiempoPrevistoElement = (Element) tiemposPrevistos
																						.item(p);
																				// ASIGNAR VALOR Al OBJETO HISTORICO
																				historico.setTiempoPrevisto(Integer
																						.parseInt(tiempoPrevistoElement
																								.getTextContent()));
																				// OBTENER LA LISTA DE NODOS
																				// "tiempoTotal"
																				NodeList tiemposTotales = historicoElement
																						.getElementsByTagName(
																								"tiempoTotal");
																				for (int q = 0; q < tiemposTotales
																						.getLength(); q++) {
																					// OBTENER CADA NODO "tiempoTotal"
																					Node nodeTiemposTotales = tiemposTotales
																							.item(q);
																					if (nodeTiemposTotales
																							.getNodeType() == Node.ELEMENT_NODE) {
																						Element tiempoTotalElement = (Element) tiemposTotales
																								.item(q);
																						// ASIGNAR VALOR Al OBJETO
																						historico.setTiempoTotal(
																								Integer.parseInt(
																										tiempoTotalElement
																												.getTextContent()));
																						// OBTENER LA LISTA DE NODOS
																						NodeList workoutNombres = historicoElement
																								.getElementsByTagName(
																										"workoutNombre");
																						for (int r = 0; r < workoutNombres
																								.getLength(); r++) {
																							// OBTENER CADA NODO
																							Node nodeWorkoutNombres = workoutNombres
																									.item(r);
																							if (nodeWorkoutNombres
																									.getNodeType() == Node.ELEMENT_NODE) {
																								Element workoutNombreElement = (Element) workoutNombres
																										.item(r);
																								// ASIGNAR VALOR Al
																								// OBJETO
																								historico
																										.setWorkoutNombre(
																												workoutNombreElement
																														.getTextContent());
																							}
																						}
																					}
																				}
																			}
																		}
																	}
																}
															}
														}
													} else {
														System.out.println("hola borjita");
													}
												}
											}
										}
									}
								}
								ret.add(historico);
							}
						}
						return ret;
					}
				}
			}
		} catch (Exception e) {
			System.out.println("Error al leer el fichero historico.xml.");
			e.printStackTrace();
		}
		return null;
	}

	// BUSCAR USUARIO OFFLINE
	public Usuario buscarUsuarioOffline(String nickname) throws FileNotFoundException {
		File file = null;
		FileInputStream fis = null;
		ObjectInputStream ois = null;

		try {
			file = new File(rutaDirectorio.toString(), "Usuarios.dat");
			fis = new FileInputStream(file);
			ois = new ObjectInputStream(fis);

			while (fis.getChannel().position() < fis.getChannel().size()) {
				Usuario usuario = (Usuario) ois.readObject();
				if (usuario.getNickname().equalsIgnoreCase(nickname.trim())) {
					return usuario;
				}
			}
		} catch (FileNotFoundException fnf) {
			throw fnf;
		} catch (Exception e) {
			System.out.println("Error al leer el fichero Usuarios.dat.");
			e.printStackTrace();
		} finally {
			// Cerrar el ObjectInputStream
			try {
				if (ois != null)
					ois.close();
			} catch (IOException e) {
				// borjita te queremos
			}
			// Close the FileInputStream
			try {
				if (fis != null)
					fis.close();
			} catch (IOException e) {
				// merecemos un 10
			}
		}
		return null;
	}
}