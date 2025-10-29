public class Ex2 {

    public static void ruleaza() {
        MetodaPlata card = new Card("123", "12/26");
        MetodaPlata cash = new Cash();
        MetodaPlata transfer = new TransferBancar("RO49AAAA1B31007593840000");

        proceseazaPlata(card);
        proceseazaPlata(cash);
        proceseazaPlata(transfer);
    }

    public static void proceseazaPlata(MetodaPlata plata) {
        if (plata instanceof Card c) {
            c.valideaza();
        } else if (plata instanceof Cash c) {
            c.valideaza();
        } else if (plata instanceof TransferBancar t) {
            t.valideaza();
        } else {
            System.out.println("Metoda de plata necunoscuta.");
        }
    }
}

sealed abstract class MetodaPlata permits Card, Cash, TransferBancar {
    public abstract void valideaza();
}

final class Card extends MetodaPlata {
    private String cvv;
    private String dataExpirare;

    public Card(String cvv, String dataExpirare) {
        this.cvv = cvv;
        this.dataExpirare = dataExpirare;
    }

    @Override
    public void valideaza() {
        if (cvv.length() == 3 && dataExpirare.matches("\\d{2}/\\d{2}")) {
            System.out.println("Card validat cu succes.");
        } else {
            System.out.println("Card invalid!");
        }
    }
}

final class Cash extends MetodaPlata {
    @Override
    public void valideaza() {
        System.out.println("Plata cash procesata instant.");
    }
}

final class TransferBancar extends MetodaPlata {
    private String iban;

    public TransferBancar(String iban) {
        this.iban = iban;
    }

    @Override
    public void valideaza() {
        if (iban != null && iban.startsWith("RO") && iban.length() >= 15) {
            System.out.println("IBAN valid.");
        } else {
            System.out.println("IBAN invalid!");
        }
    }
}
