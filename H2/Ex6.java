public class Ex6 {
    interface Dispozitiv {
        void porneste();
        void opreste();
        default void status() {
            System.out.println("Status: " + detalii());
        }
        static void descriereGenerala() {
            System.out.println("Dispozitive electronice inteligente.");
        }
        private String detalii() {
            return "Funcționează normal.";
        }
    }

    interface Smart extends Dispozitiv {}
    interface Conectabil extends Dispozitiv {}

    static class Telefon implements Smart, Conectabil {
        public void porneste() { System.out.println("Telefon pornit."); }
        public void opreste() { System.out.println("Telefon oprit."); }
    }

    static class Smartwatch implements Smart {
        public void porneste() { System.out.println("Smartwatch pornit."); }
        public void opreste() { System.out.println("Smartwatch oprit."); }
    }

    static class Televizor implements Conectabil {
        public void porneste() { System.out.println("Televizor pornit."); }
        public void opreste() { System.out.println("Televizor oprit."); }
    }

    public static void ruleaza() {
        Dispozitiv.descriereGenerala();

        Telefon t = new Telefon();
        Smartwatch s = new Smartwatch();
        Televizor tv = new Televizor();

        t.porneste(); t.status(); t.opreste();
        s.porneste(); s.status(); s.opreste();
        tv.porneste(); tv.status(); tv.opreste();
    }
}
