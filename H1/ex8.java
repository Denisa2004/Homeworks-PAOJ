import java.util.*;

public class ex8 {
    static final class Student implements Comparable<Student> {
        private final String nume;
        private final int varsta;
        private final double medie;

        public Student(String nume, int varsta, double medie) {
            this.nume = nume;
            this.varsta = varsta;
            this.medie = medie;
        }

        public String getNume() { return nume; }
        public int getVarsta() { return varsta; }
        public double getMedie() { return medie; }

        @Override
        public int compareTo(Student o) {
            return Double.compare(this.medie, o.medie);
        }

        @Override
        public boolean equals(Object o) {
            if (!(o instanceof Student)) return false;
            Student s = (Student) o;
            return nume.equals(s.nume) && varsta == s.varsta && Double.compare(medie, s.medie) == 0;
        }

        @Override
        public int hashCode() {
            return Objects.hash(nume, varsta, medie);
        }

        @Override
        public String toString() {
            return nume + ", varsta: " + varsta + ", medie: " + medie;
        }
    }

    static class Grup<T extends Student> {
        private List<T> studenti = new ArrayList<>();

        public void adaugaStudent(T s) {
            studenti.add(s);
        }

        public T cautaDupaNume(String nume) {
            for (T s : studenti) {
                if (s.getNume().equals(nume)) return s;
            }
            return null;
        }

        public void sorteaza() {
            Collections.sort(studenti);
        }

        public void afiseazaRaport() {
            StringBuilder sb = new StringBuilder();
            for (T s : studenti) {
                sb.append(s.toString()).append("\n");
            }
            System.out.println("Raport:\n" + sb);

            StringBuffer syncBuffer = new StringBuffer(sb);
            System.out.println("Raport sincronizat:\n" + syncBuffer);
        }
    }

    public static void run() {
        Grup<Student> grup = new Grup<>();
        grup.adaugaStudent(new Student("Ana", 20, 9.5));
        grup.adaugaStudent(new Student("Bogdan", 21, 8.7));
        grup.adaugaStudent(new Student("Carmen", 22, 9.1));

        grup.sorteaza();
        grup.afiseazaRaport();
    }
}