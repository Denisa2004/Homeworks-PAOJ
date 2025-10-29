public class ex6 {
    static class Profesor {
        private String nume;
        private String specializare;
        private final int codIdentificare;

        public Profesor(String nume, String specializare, int codIdentificare) {
            this.nume = nume;
            this.specializare = specializare;
            this.codIdentificare = codIdentificare;
        }

        public String getNume() { return nume; }
        public void setNume(String nume) { this.nume = nume; }

        public String getSpecializare() { return specializare; }
        public void setSpecializare(String specializare) { this.specializare = specializare; }

        public int getCodIdentificare() { return codIdentificare; }
    }

    static class Curs {
        private Profesor profesor;
        private String[] studenti;

        public Curs(Profesor profesor, String[] studenti) {
            this.profesor = profesor;
            this.studenti = studenti;
        }

        public void afiseaza() {
            System.out.println("Curs predat de: " + profesor.getNume() + ", specializare: " + profesor.getSpecializare());
            System.out.println("Studenti: ");
            for (String s : studenti) {
                System.out.println("- " + s);
            }
        }
    }

    public static void run() {
        Profesor prof = new Profesor("Popescu Ion", "Matematica", 123);
        String[] studenti = {"Ana", "Bogdan", "Carmen"};
        Curs curs = new Curs(prof, studenti);
        curs.afiseaza();
    }
}