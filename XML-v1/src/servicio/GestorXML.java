package servicio;

public class GestorXML {
    public List<Pelicula> listar() {
        List<Pelicula> lista = new ArrayList<>();
        try {
            File f = new File(fichero);

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(f);

            NodeList peliculasNode = doc.getElementsByTagName("pelicula");

            for (int i = 0; i < peliculasNode.getLength(); i++) {
                Node node = peliculasNode.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element e = (Element) node;

                    int id = Integer.parseInt(e.getElementsByTagName("id").item(0).getTextContent());
                    String titulo = e.getElementsByTagName("titulo").item(0).getTextContent();
                    String genero = e.getElementsByTagName("genero").item(0).getTextContent();
                    int minutos = Integer.parseInt(e.getElementsByTagName("minutos").item(0).getTextContent());

                    lista.add(new Pelicula(id, titulo, genero, minutos));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }
}
