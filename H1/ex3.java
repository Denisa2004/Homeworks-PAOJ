abstract class Vehicul {
    protected String marca;
    protected String model;
    protected int anFabricatie;

    public Vehicul(String marca, String model, int anFabricatie) {
        this.marca = marca;
        this.model = model;
        this.anFabricatie = anFabricatie;
    }

    public abstract String descriere();
}

class Masina extends Vehicul {
    private String transmisie;

    public Masina(String marca, String model, int anFabricatie, String transmisie) {
        super(marca, model, anFabricatie);
        this.transmisie = transmisie;
    }

    @Override
    public String descriere() {
        return "Masina: " + marca + " " + model + ", An: " + anFabricatie + ", Transmisie: " + transmisie;
    }
}

class Motocicleta extends Vehicul {
    private String clasaPutere;

    public Motocicleta(String marca, String model, int anFabricatie, String clasaPutere) {
        super(marca, model, anFabricatie);
        this.clasaPutere = clasaPutere;
    }

    @Override
    public String descriere() {
        return "Motocicleta: " + marca + " " + model + ", An: " + anFabricatie + ", Clasa putere: " + clasaPutere;
    }
}

public class ex3 {
    public static void run() {
        Vehicul[] vehicule = new Vehicul[] {
                new Masina("Toyota", "Corolla", 2015, "Automata"),
                new Motocicleta("Yahama", "R1", 2020, "A")
        };

        for (Vehicul v : vehicule) {
            System.out.println(v.descriere());
        }
    }
}
