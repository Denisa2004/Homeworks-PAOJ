import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class Ex2 {
    public static void main(String[] args) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("comenzi.dat"))) {
            for (int i = 1; i <= 15; i++) {
                Comanda c = new Comanda(i, "Client" + (i % 5), i * 1000, false);
                oos.writeObject(c);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<Comanda> comenzi = new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("comenzi.dat"))) {
            while (true) {
                comenzi.add((Comanda) ois.readObject());
            }
        } catch (EOFException e) {
            System.out.println("Sfarsit fisier.");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        comenzi.forEach(c -> {
            if (c.getValoare() > 5000) {
                c.setFinalizata(true);
            }
        });

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("comenzi.dat"))) {
            for (Comanda c : comenzi) {
                oos.writeObject(c);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<Comanda> comenziFinal = new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("comenzi.dat"))) {
            while (true) {
                comenziFinal.add((Comanda) ois.readObject());
            }
        } catch (EOFException e) {
            System.out.println("Sfarsit fisier.");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        comenziFinal.stream()
                .filter(Comanda::isFinalizata)
                .forEach(System.out::println);

        double sumaFinalizate = comenziFinal.stream()
                .filter(Comanda::isFinalizata)
                .mapToDouble(Comanda::getValoare)
                .sum();
        System.out.println("Suma comenzilor finalizate: " + sumaFinalizate);

        Map<String, List<Comanda>> grupate = comenziFinal.stream()
                .filter(Comanda::isFinalizata)
                .collect(Collectors.groupingBy(Comanda::getNumeClient));
        grupate.forEach((client, lista) -> {
            System.out.println(client + " => " + lista);
        });
    }

    static class Comanda implements Serializable {
        private int id;
        private String numeClient;
        private double valoare;
        private boolean finalizata;

        public Comanda(int id, String numeClient, double valoare, boolean finalizata) {
            this.id = id;
            this.numeClient = numeClient;
            this.valoare = valoare;
            this.finalizata = finalizata;
        }

        public double getValoare() { return valoare; }
        public String getNumeClient() { return numeClient; }
        public boolean isFinalizata() { return finalizata; }
        public void setFinalizata(boolean finalizata) { this.finalizata = finalizata; }

        @Override
        public String toString() {
            return id + " | " + numeClient + " | " + valoare + " RON | " + (finalizata ? "Finalizata" : "Neprocesata");
        }
    }
}
