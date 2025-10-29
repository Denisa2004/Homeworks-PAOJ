import java.util.*;
import java.util.function.*;
import java.util.stream.Collectors;

public class Ex3 {
    public static void main(String[] args) {
        List<Client> clienti = Arrays.asList(
                new Client("Andrei", 22, 3500, Optional.of("VIP")),
                new Client("Maria", 30, 4200, Optional.of("Standard")),
                new Client("Ion", 27, 3000, Optional.of("Nou")),
                new Client("Elena", 24, 5100, Optional.empty()),
                new Client("Alex", 19, 2700, Optional.of("Nou")),
                new Client("Mihai", 35, 7000, Optional.of("VIP")),
                new Client("Cristina", 40, 6200, Optional.of("VIP")),
                new Client("Paul", 23, 3300, Optional.of("Standard")),
                new Client("Diana", 26, 2800, Optional.of("Nou")),
                new Client("Radu", 21, 3500, Optional.of("VIP")),
                new Client("Sorin", 45, 6800, Optional.empty()),
                new Client("Simona", 20, 2900, Optional.of("Standard"))
        );

        double media = clienti.stream()
                .mapToDouble(Client::getSumaCont)
                .average()
                .orElse(0);
        System.out.println("Media sumelor: " + media);

        Predicate<Client> eVIP = c -> c.getTipClient().orElse("Necunoscut").equals("VIP");
        clienti.stream()
                .filter(eVIP.and(c -> c.getSumaCont() > media))
                .forEach(System.out::println);

        Function<Client, String> mapare = c -> c.getNume() + " - " + c.getVarsta() + " ani";
        clienti.stream()
                .map(mapare)
                .forEach(System.out::println);

        double sumaTotala = clienti.stream()
                .mapToDouble(Client::getSumaCont)
                .sum();
        System.out.println("Suma totala conturi: " + sumaTotala);

        Map<String, Long> grupare = clienti.stream()
                .collect(Collectors.groupingBy(c -> c.getTipClient().orElse("Necunoscut"), Collectors.counting()));
        System.out.println(grupare);

        String sub25 = clienti.stream()
                .filter(c -> c.getVarsta() < 25)
                .map(Client::getNume)
                .collect(Collectors.joining(", "));
        System.out.println("Sub 25 ani: " + sub25);
    }

    static class Client {
        private String nume;
        private int varsta;
        private double sumaCont;
        private Optional<String> tipClient;

        public Client(String nume, int varsta, double sumaCont, Optional<String> tipClient) {
            this.nume = nume;
            this.varsta = varsta;
            this.sumaCont = sumaCont;
            this.tipClient = tipClient;
        }

        public String getNume() { return nume; }
        public int getVarsta() { return varsta; }
        public double getSumaCont() { return sumaCont; }
        public Optional<String> getTipClient() { return tipClient; }

        @Override
        public String toString() {
            return nume + " | " + varsta + " | " + sumaCont + " | " + tipClient.orElse("Necunoscut");
        }
    }
}
