public class ex7 {
    static class Animal {
        public void sunet() {
            System.out.println("Animalul scoate un sunet.");
        }
    }

    static class Mamifer extends Animal {}

    static class Caine extends Mamifer {
        public void sunet() {
            System.out.println("Cainele latra.");
        }

        public void latra() {
            System.out.println("Ham ham!");
        }
    }

    static class Pisica extends Mamifer {
        public void sunet() {
            System.out.println("Pisica miauna.");
        }

        public void miauna() {
            System.out.println("Miau!");
        }
    }

    public static void run() {
        Animal[] animale = {new Caine(), new Pisica()};

        for (Animal a : animale) {
            a.sunet();
            if (a instanceof Caine) {
                ((Caine) a).latra();
            } else if (a instanceof Pisica) {
                ((Pisica) a).miauna();
            }
        }
    }
}