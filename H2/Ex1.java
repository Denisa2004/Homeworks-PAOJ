public class Ex1 {
    interface PoateEdita {}
    interface PoateSterge {}
    interface PoateVizualiza {}

    static class Utilizator {}
    static class Administrator extends Utilizator implements PoateEdita, PoateSterge, PoateVizualiza {}
    static class Editor extends Utilizator implements PoateEdita, PoateVizualiza {}
    static class Vizitator extends Utilizator implements PoateVizualiza {}

    public static void ruleaza() {
        Utilizator admin = new Administrator();
        Utilizator editor = new Editor();
        Utilizator vizitator = new Vizitator();

        afiseazaPermisiuni(admin);
        afiseazaPermisiuni(editor);
        afiseazaPermisiuni(vizitator);
    }

    static void afiseazaPermisiuni(Utilizator u) {
        System.out.println("Permisiuni pentru " + u.getClass().getSimpleName());
        if (u instanceof PoateVizualiza) System.out.println("- Poate vizualiza");
        if (u instanceof PoateEdita) System.out.println("- Poate edita");
        if (u instanceof PoateSterge) System.out.println("- Poate sterge");
        System.out.println();
    }
}
