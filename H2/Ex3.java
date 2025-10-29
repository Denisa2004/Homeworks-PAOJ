public class Ex3 {
    static class RezervareException extends Exception {
        public RezervareException(String mesaj) { super(mesaj); }
    }
    static class LocIndisponibilException extends RezervareException {
        public LocIndisponibilException() { super("Loc indisponibil!"); }
    }
    static class DateInvalideException extends RezervareException {
        public DateInvalideException() { super("Data invalidă!"); }
    }

    static void rezervaLoc(String data, int loc) throws RezervareException {
        if (loc < 0 || loc > 100) throw new LocIndisponibilException();
        if (!data.matches("\\d{2}-\\d{2}-\\d{4}")) throw new DateInvalideException();
        System.out.println("Rezervare reușită pentru loc " + loc + " la data " + data);
    }

    public static void ruleaza() {
        try {
            rezervaLoc("28-04-2025", 50);
            rezervaLoc("2025-04-28", 50);
        } catch (LocIndisponibilException e) {
            System.out.println(e.getMessage());
        } catch (DateInvalideException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println("Eroare necunoscută: " + e.getMessage());
        } finally {
            System.out.println("Încheiere rezervare.\n");
        }
    }
}
