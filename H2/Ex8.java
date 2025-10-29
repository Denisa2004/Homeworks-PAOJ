import java.util.*;

public class Ex8 {
    enum NivelAcces {
        ADMIN(1, "Acces complet"),
        EDITOR(2, "Modificare conținut"),
        USER(3, "Utilizare basic"),
        GUEST(4, "Vizualizare");

        private int cod;
        private String descriere;

        NivelAcces(int cod, String descriere) {
            this.cod = cod;
            this.descriere = descriere;
        }

        public int getCod() { return cod; }
        public String getDescriere() { return descriere; }

        public static NivelAcces dinCod(int cod) {
            for (NivelAcces n : values())
                if (n.getCod() == cod) return n;
            return null;
        }
    }

    static class ContUtilizator {
        private String nume;
        private NivelAcces acces;

        public ContUtilizator(String nume, NivelAcces acces) {
            this.nume = nume;
            this.acces = acces;
        }

        public NivelAcces getAcces() { return acces; }
        public String toString() { return nume + " - " + acces.getDescriere(); }
    }

    public static void ruleaza() {
        List<ContUtilizator> conturi = List.of(
                new ContUtilizator("Alex", NivelAcces.ADMIN),
                new ContUtilizator("Mara", NivelAcces.EDITOR),
                new ContUtilizator("Vlad", NivelAcces.USER),
                new ContUtilizator("Ioana", NivelAcces.GUEST)
        );

        System.out.println("Conturi cu acces superior USER:");
        conturi.stream()
                .filter(c -> c.getAcces().ordinal() < NivelAcces.USER.ordinal())
                .forEach(System.out::println);
    }
}
