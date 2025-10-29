import java.util.Scanner;

public class ex1 {
    public static void run() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Introduceti doua numere intregi si un numar real, separate prin spatiu:");
        String[] input = scanner.nextLine().split("\\s+");

        if (input.length < 3) {
            System.out.println("Eroare: Introdu cel putin doua numere intregi si unul real.");
            return;
        }

        try {
            int a = Integer.parseInt(input[0]);
            int b = Integer.parseInt(input[1]);
            double c = Double.parseDouble(input[2]);

            double suma = a + b + c;
            double media = suma / 3;
            double produs = a * b * c;

            System.out.printf("Suma: %.2f, Media: %.2f, Produsul: %.2f\n", suma, media, produs);
        } catch (NumberFormatException e) {
            System.out.println("Eroare: Toate argumentele trebuie sa fie numere valide.");
        } catch (ArithmeticException e) {
            System.out.println("Eroare: " + e.getMessage());
        }
    }
}