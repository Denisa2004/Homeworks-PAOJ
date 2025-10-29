import java.util.Arrays;
import java.util.Scanner;

public class ex2 {
    public static void run() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Introduceti cel puțin 5 numere intregi separate prin spatiu:");
        String input = scanner.nextLine();
        String[] parts = input.trim().split("\\s+");

        if (parts.length < 5) {
            System.out.println("Trebuie sa introduceti cel putin 5 numere.");
            return;
        }

        int[] array = new int[parts.length];
        for (int i = 0; i < parts.length; i++) {
            array[i] = Integer.parseInt(parts[i]);
        }

        int[] original = array.clone();

        Arrays.sort(array);
        int index = Arrays.binarySearch(array, original[0]);

        System.out.println("Array initial: " + Arrays.toString(original));
        System.out.println("Array sortat: " + Arrays.toString(array));
        System.out.println("Indexul primului număr (\"" + original[0] + "\") in array-ul sortat: " + index);
    }
}
