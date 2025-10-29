public class ex5 {
    static class Carte implements Cloneable {
        private String titlu;
        private String autor;
        private int anAparitie;

        public Carte(String titlu, String autor, int anAparitie) {
            this.titlu = titlu;
            this.autor = autor;
            this.anAparitie = anAparitie;
        }

        public Carte(Carte c) {
            this(c.titlu, c.autor, c.anAparitie);
        }

        public void afiseazaDetalii() {
            System.out.println(this);
        }

        public void afiseazaDetalii(boolean complet) {
            if (complet) {
                System.out.println(toString());
            } else {
                System.out.println("Carte: " + titlu + " de " + autor);
            }
        }

        @Override
        public String toString() {
            return "Carte{titlu='" + titlu + "', autor='" + autor + "', anAparitie=" + anAparitie + "}";
        }

        public Carte shallowCopy() {
            return this;
        }

        public Carte deepCopy() {
            return new Carte(this.titlu, this.autor, this.anAparitie);
        }
    }

    public static void run() {
        Carte original = new Carte("Ion", "Liviu Rebreanu", 1920);
        Carte copieShallow = original.shallowCopy();
        Carte copieDeep = original.deepCopy();

        System.out.println("Original: " + original);
        System.out.println("Shallow Copy: " + copieShallow);
        System.out.println("Deep Copy: " + copieDeep);
    }
}