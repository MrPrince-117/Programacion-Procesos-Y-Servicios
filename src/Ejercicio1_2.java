public class Ejercicio1_2 {
    public static void main(String[] args) {
        if (args.length > 0){
            System.exit(1);
        }
        try {
            int number = Integer.parseInt(args[0]);

            // Si el número es menor que 0
            if (number < 0) {
                System.exit(3); // Devuelve 3 si el número es menor que 0
            }
        } catch (NumberFormatException e) {
            // Si el argumento no es un número, es una cadena
            System.exit(2); // Devuelve 2 si el argumento es una cadena
        }

        System.exit(0);
    }
}
