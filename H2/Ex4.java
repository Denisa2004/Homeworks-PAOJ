public class Ex4 {
    static class SistemExistent {
        void afiseazaXML(String xml) {
            System.out.println("Afișare XML: " + xml);
        }
    }

    static class SistemNou {
        String genereazaJSON() {
            return "{\"nume\":\"Alex\",\"varsta\":25}";
        }
    }

    static class AdaptorJsonToXml {
        private SistemExistent sistem;

        public AdaptorJsonToXml(SistemExistent sistem) {
            this.sistem = sistem;
        }

        void trimiteDate(SistemNou sistemNou) {
            String json = sistemNou.genereazaJSON();
            String xml = "<date>" + json + "</date>";
            sistem.afiseazaXML(xml);
        }
    }

    public static void ruleaza() {
        SistemExistent vechi = new SistemExistent();
        SistemNou nou = new SistemNou();
        AdaptorJsonToXml adaptor = new AdaptorJsonToXml(vechi);
        adaptor.trimiteDate(nou);
    }
}
