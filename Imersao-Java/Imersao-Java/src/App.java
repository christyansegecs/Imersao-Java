import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

public class App {
    public static void main(String[] args) throws Exception {

        API api = API.IMDB_TOP_MOVIES;

        String url = api.getUrl();
        ExtratorDeConteudo extrator = api.getExtrator();
        
        var http = new ClienteHttp();
        String json = http.buscaDados(url);

        var diretorio = new File("saida/");
            diretorio.mkdir();

        // exibir e manipular os dados
        List<Conteudo> conteudos = extrator.extraiConteudos(json);

        var geradora = new GeradoraDeFigurinhas();

        for (int i = 0; i < 3; i++) {

            Conteudo conteudo = conteudos.get(i);

            InputStream inputStream = new URL(conteudo.urlImagem()).openStream();
            String nomeArquivo = "saida/" + conteudo.titulo() + ".png";
            String texto = "TOPZERA";

            geradora.cria(inputStream, nomeArquivo, texto);

            System.out.println(conteudo.titulo());
        }
    }
}
