package br.com.alura.screenmatch.principal;

import br.com.alura.screenmatch.excecao.ErroDeConversaoDeAnoException;
import br.com.alura.screenmatch.modelos.Titulo;
import br.com.alura.screenmatch.modelos.TituloOmdb;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PrincipalComBusca {
    public static void main(String[] args) throws IOException, InterruptedException {

        Scanner leitura = new Scanner(System.in);
        var busca = "";
        List<Titulo> listaDeTitulos = new ArrayList<>();
        Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE)
                .setPrettyPrinting()
                .create();

        while(!busca.equalsIgnoreCase("sair")) {
            System.out.println("Informe o nome do filme que deseja buscar: ");
            busca = leitura.nextLine();

            if(busca.equalsIgnoreCase("sair")){
                break;
            }

            String endereco = "https://www.omdbapi.com/?t=" + busca + "&apiKey=67abc709";
            endereco = endereco.replace(" ", "+");

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(endereco))
                    .build();
            HttpResponse<String> response = client
                    .send(request, HttpResponse.BodyHandlers.ofString());
            String json = response.body();
            System.out.println(json);

            TituloOmdb meuTituloOmdb = gson.fromJson(json, TituloOmdb.class);
            System.out.println(meuTituloOmdb);

            try{
                Titulo meuTitulo = new Titulo(meuTituloOmdb);
                System.out.println("Meu titulo já convertido");
                System.out.println(meuTitulo);

                listaDeTitulos.add(meuTitulo);
            }catch (NumberFormatException exception){
                System.out.println("Aconteceu um erro: ");
                System.out.println(exception.getMessage());
            }catch (ErroDeConversaoDeAnoException exception){
                System.out.println(exception.getMessage());
            }
        }
        System.out.println(listaDeTitulos);

        FileWriter escrita = new FileWriter("filmes.json");
        escrita.write(gson.toJson(listaDeTitulos));
        escrita.close();
        System.out.println("O programa finalizou corretamente!");
    }
}
