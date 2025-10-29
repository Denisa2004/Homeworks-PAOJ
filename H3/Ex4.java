import java.io.*;
import java.util.*;
import java.util.stream.*;

public class Ex4 {
    public static void main(String[] args) {
        List<Persoana> persoane = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader("date.txt"))) {
            String linie;
            while ((linie = br.readLine()) != null) {
                String[] parti = linie.split(";");
                if (parti.length == 3) {
                    String nume = parti[0];
                    int varsta = Integer.parseInt(parti[1]);
                    String oras = parti[2];
                    persoane.add(new Persoana(nume, varsta, oras));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<Persoana> filtrate = persoane.stream()
                .filter(p -> p.getVarsta() > 30 && p.getOras().startsWith("B"))
                .sorted(Comparator.comparing(Persoana::getNume).thenComparing(Persoana::getVarsta))
                .collect(Collectors.toList());

        Map<String, List<Persoana>> grupate = persoane.stream()
                .collect(Collectors.groupingBy(Persoana::getOras));

        Map<String, Double> medii = persoane.stream()
                .collect(Collectors.groupingBy(Persoana::getOras,
                        Collectors.averagingInt(Persoana::getVarsta)));

        persoane.stream()
                .max(Comparator.comparingInt(Persoana::getVarsta))
                .ifPresent(p -> System.out.println("Cea mai în vârstă persoană: " + p));

        try (PrintWriter pw = new PrintWriter(new FileWriter("rezultat.txt"))) {
            pw.println("Persoane filtrate si sortate:");
            filtrate.forEach(pw::println);

            pw.println("\nMedia varstelor per oras:");
            medii.forEach((oras, medie) -> pw.println(oras + ": " + String.format("%.2f", medie)));

            pw.println("\nPersoane grupate pe orase:");
            grupate.forEach((oras, lista) -> {
                pw.println(oras + ":");
                lista.forEach(p -> pw.println("  - " + p));
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static class Persoana {
        private String nume;
        private int varsta;
        private String oras;

        public Persoana(String nume, int varsta, String oras) {
            this.nume = nume;
            this.varsta = varsta;
            this.oras = oras;
        }

        public String getNume() { return nume; }
        public int getVarsta() { return varsta; }
        public String getOras() { return oras; }

        @Override
        public String toString() {
            return nume + " - " + varsta + " ani - " + oras;
        }
    }
}
