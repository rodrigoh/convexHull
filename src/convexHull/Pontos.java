
package convexHull;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *
 * @author Rodrigo Henrich
 */
public class Pontos {
  public static Ponto gerarPonto(){
    Random aleatorio = new Random();
    int x = aleatorio.nextInt(-3000, 3000);
    int y = aleatorio.nextInt(-3000, 3000);
    return new Ponto(x, y);
  }
  
  public static List<Ponto> gerarPontos(int quantidade){
    List<Ponto> pontos = new ArrayList<>();
    for (int i = 0; i < quantidade; i++) {
      pontos.add(gerarPonto());
    }
    return pontos;
  }
  /**
   * Lê uma lista de pontos x <sep> y de um arquivo (cada ponto deve estar em uma linha do arquivo)
   * @param arquivo nome do arquivo 
   * @param separador String usada em cada ponto <sep>
   * @return lista de pontos contina no arquivo
   */
  public static List<Ponto> importarPontos(String arquivo, String separador){
    List<Ponto> pontos = new ArrayList<>();
    try{
      BufferedReader arquivoLeitura = new BufferedReader(new FileReader(arquivo));
      String linha = new String();
      while(linha != null){
        linha = arquivoLeitura.readLine();
        if(linha!=null){
          String[] linhaSplit = linha.split(separador);
          float x = Float.parseFloat(linhaSplit[0]);
          float y = Float.parseFloat(linhaSplit[1]);
          pontos.add(new Ponto(x,y));
        }
      }
    }
    catch(IOException e){
      System.out.println("Problemas ao carregar o arquivo...");
    }
    return pontos;
  }
}
