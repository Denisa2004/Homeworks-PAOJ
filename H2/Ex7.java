import java.util.*;

public class Ex7 {
    static class Produs {
        private String cod;
        private String nume;
        private double pret;

        public Produs(String cod, String nume, double pret) {
            this.cod = cod;
            this.nume = nume;
            this.pret = pret;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Produs)) return false;
            Produs p = (Produs) o;
            return cod.equals(p.cod);
        }

        @Override
        public int hashCode() {
            return Objects.hash(cod);
        }

        @Override
        public String toString() {
            return nume + " (" + cod + ")";
        }
    }

    public static void ruleaza() {
        Set<Produs> produse = new HashSet<>();
        produse.add(new Produs("P1", "Laptop", 3500));
        produse.add(new Produs("P2", "Mouse", 100));
        produse.add(new Produs("P1", "Laptop duplicat", 3600));

        System.out.println("Produse unice:");
        produse.forEach(System.out::println);

        Map<Produs, Integer> stoc = new HashMap<>();
        for (Produs p : produse) stoc.put(p, 10);

        System.out.println("\nStocuri:");
        stoc.entrySet().forEach(e -> System.out.println(e.getKey() + " - stoc: " + e.getValue()));
    }
}
