import java.util.*;

// Clase principal que gestiona las notas de los estudiantes
public class LibretaDeNotas {
    // Mapa que almacena el nombre del estudiante y su lista de notas
    private HashMap<String, ArrayList<Double>> notasAlumnos = new HashMap<>();
    private final Scanner scanner = new Scanner(System.in);

    // Método para pedir datos al usuario como cant.alumnos y cant.notas por alumno.
    public void iniciar() {
        System.out.print("Ingrese la cantidad de alumnos: ");
        int cantidadAlumnos = validarEnteroPositivo();

        System.out.print("Ingrese la cantidad de notas por alumno: ");
        int cantidadNotas = validarEnteroPositivo();

        // Recolección de datos de cada alumno y sus notas
        for (int i = 0; i < cantidadAlumnos; i++) {
            System.out.print("Ingrese el nombre del alumno " + (i + 1) + ": ");
            String nombre = scanner.nextLine();
            ArrayList<Double> notas = new ArrayList<>();

            for (int j = 0; j < cantidadNotas; j++) {
                System.out.print("Ingrese la nota #" + (j + 1) + " para " + nombre + ": ");
                double nota = validarNota();
                notas.add(nota);
            }

            // Almacenar las notas en el mapa usando el nombre como clave
            notasAlumnos.put(nombre, notas);
        }

        // Lanzar el menú de opciones
        menu();
    }

    // Menú interactivo con opciones para el usuario
    private void menu() {
        int opcion;
        do {
            System.out.println("\n--- MENÚ DE OPCIONES ---");
            System.out.println("1. Mostrar Promedio de Notas por Estudiante");
            System.out.println("2. Verificar si una Nota es Aprobatoria");
            System.out.println("3. Comparar Nota con Promedio del Curso");
            System.out.println("0. Salir");
            System.out.print("Seleccione una opción: ");
            opcion = validarEnteroPositivo();

            switch (opcion) {
                case 1: mostrarPromedios();
                case 2: verificarNotaAprobatoria();
                case 3: compararNotaConPromedioCurso();
                case 0: System.out.println("Saliendo del menú...");
                default: System.out.println("Opción inválida.");
            }
        } while (opcion != 0);
    }

    // Mostrar estadísticas de cada estudiante: promedio, nota máxima y mínima
    private void mostrarPromedios() {
        for (Map.Entry<String, ArrayList<Double>> entrada : notasAlumnos.entrySet()) {
            String alumno = entrada.getKey();
            ArrayList<Double> notas = entrada.getValue();
            double promedio = calcularPromedio(notas);
            double max = Collections.max(notas);
            double min = Collections.min(notas);
            System.out.printf("\n%s -> Promedio: %.2f | Máxima: %.2f | Mínima: %.2f%n", alumno, promedio, max, min);
        }
    }

    // Verifica si una nota puntual ingresada es aprobatoria (≥ 4.0)
    private void verificarNotaAprobatoria() {
        System.out.print("Ingrese nombre del estudiante: ");
        String alumno = scanner.nextLine();

        if (!notasAlumnos.containsKey(alumno)) {
            System.out.println("Estudiante no encontrado.");
            return;
        }

        System.out.print("Ingrese la nota a evaluar: ");
        double nota = validarNota();

        if (nota >= 4.0) {
            System.out.println("✅ Nota Aprobatoria");
        } else {
            System.out.println("❌ Nota Reprobatoria");
        }
    }

    // Compara una nota con el promedio general del curso
    private void compararNotaConPromedioCurso() {
        System.out.print("Ingrese nombre del estudiante: ");
        String alumno = scanner.nextLine();

        if (!notasAlumnos.containsKey(alumno)) {
            System.out.println("Estudiante no encontrado.");
            return;
        }

        System.out.print("Ingrese la nota a comparar: ");
        double nota = validarNota();

        double promedioCurso = calcularPromedioCurso();

        if (nota > promedioCurso) {
            System.out.printf("✅ La nota %.2f está por sobre el promedio general (%.2f)%n", nota, promedioCurso);
        } else if (nota < promedioCurso) {
            System.out.printf("❌ La nota %.2f está por debajo del promedio general (%.2f)%n", nota, promedioCurso);
        } else {
            System.out.println("La nota es igual al promedio general.");
        }
    }

    // Calcula el promedio de un alumno a partir de su lista de notas
    private double calcularPromedio(ArrayList<Double> notas) {
        double suma = 0;
        for (double n : notas) suma += n;
        return notas.size() > 0 ? suma / notas.size() : 0;
    }

    // Calcula el promedio general del curso (de todas las notas)
    private double calcularPromedioCurso() {
        double total = 0;
        int cantidadNotas = 0;

        for (ArrayList<Double> notas : notasAlumnos.values()) {
            for (double nota : notas) {
                total += nota;
                cantidadNotas++;
            }
        }

        return cantidadNotas > 0 ? total / cantidadNotas : 0;
    }

    // Valida que el usuario ingrese un número entero positivo
    private int validarEnteroPositivo() {
        while (true) {
            try {
                int valor = Integer.parseInt(scanner.nextLine());
                if (valor > 0) return valor;
                else System.out.print("Debe ser un número positivo. Intente de nuevo: ");
            } catch (NumberFormatException e) {
                System.out.print("Entrada inválida. Intente de nuevo: ");
            }
        }
    }

    // Valida que la nota esté entre 1.0 y 7.0 (como en Chile)
    private double validarNota() {
        while (true) {
            try {
                double nota = Double.parseDouble(scanner.nextLine());
                if (nota >= 1.0 && nota <= 7.0) return nota;
                else System.out.print("La nota debe estar entre 1.0 y 7.0: ");
            } catch (NumberFormatException e) {
                System.out.print("Entrada inválida. Ingrese un número válido: ");
            }
        }
    }

    // Método main que arranca el programa
    public static void main(String[] args) {
        new LibretaDeNotas().iniciar();
    }
}
