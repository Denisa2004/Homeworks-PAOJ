import java.io.*;
import java.util.*;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;

public class Ex1 {
    public static void main(String[] args) {
        List<Produs> produse = new ArrayList<>();

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("produse.dat"))) {
            for (int i = 1; i <= 10; i++) {
                Produs p = new Produs("Produs" + i, i * 100, (i % 3 == 0) ? 0 : i * 5);
                produse.add(p);
                oos.writeObject(p);
            }
        } catch (IOException | InvalidDataException e) {
            try (PrintWriter log = new PrintWriter(new FileWriter("erori.log", true))) {
                log.println("Eroare: " + e.getMessage());
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        List<Produs> produseCitite = new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("produse.dat"))) {
            while (true) {
                produseCitite.add((Produs) ois.readObject());
            }
        } catch (EOFException e) {
            System.out.println("Sfarsit fisier.");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        List<Produs> epuizate = produseCitite.stream()
                .filter(p -> p.getStoc() == 0)
                .collect(Collectors.toList());
        try (PrintWriter pw = new PrintWriter("epuizate.txt")) {
            epuizate.forEach(pw::println);
        } catch (IOException e) {
            e.printStackTrace();
        }

        UnaryOperator<Produs> reduceStoc = p -> {
            p.setStoc((int) (p.getStoc() * 0.9));
            return p;
        };
        produseCitite.replaceAll(reduceStoc);

        produseCitite.stream()
                .max(Comparator.comparingDouble(Produs::getPret))
                .ifPresent(p -> System.out.println("Cel mai scump produs: " + p));
    }

    static class Produs implements Serializable {
        private String nume;
        private double pret;
        private int stoc;

        public Produs(String nume, double pret, int stoc) throws InvalidDataException {
            if (pret < 0 || stoc < 0) {
                throw new InvalidDataException("Pretul sau stocul nu pot fi negative.");
            }
            this.nume = nume;
            this.pret = pret;
            this.stoc = stoc;
        }

        public String getNume() { return nume; }
        public double getPret() { return pret; }
        public int getStoc() { return stoc; }
        public void setStoc(int stoc) { this.stoc = stoc; }

        @Override
        public String toString() {
            return nume + " - " + pret + " RON, stoc: " + stoc;
        }
    }

    static class InvalidDataException extends Exception {
        public InvalidDataException(String mesaj) {
            super(mesaj);
        }
    }
}
