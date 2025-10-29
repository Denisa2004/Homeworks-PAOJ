import java.util.ArrayList;
import java.util.List;

public class Ex5 {
    static abstract class OrganismViu {
        abstract void respira();
        abstract void seHraneste();
    }

    static abstract class Animal extends OrganismViu {
        @Override final void respira() {
            System.out.println(this.getClass().getSimpleName() +"ul" + " respiră.");
        }
    }

    static abstract class Mamifer extends Animal {
        abstract void arePar();
    }

    static class Urs extends Mamifer {
        public void arePar() { System.out.println("Ursul are blană."); }
        public void seHraneste() { System.out.println("Ursul mănâncă miere."); }
    }

    static class Delfin extends Mamifer {
        public void arePar() { System.out.println("Delfinul nu are păr."); }
        public void seHraneste() { System.out.println("Delfinul mănâncă pește."); }
    }

    public static void ruleaza() {
        List<OrganismViu> lista = new ArrayList<>();
        lista.add(new Urs());
        lista.add(new Delfin());

        for (OrganismViu o : lista) {
            o.respira();
            o.seHraneste();
            if (o instanceof Mamifer m) m.arePar();
            System.out.println();
        }
    }
}
