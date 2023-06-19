package convexHull;

/**
 *
 * @author Rodrigo Henrich
 */
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ConvexHullRodrigo {
  
  private static int contIteracoes = 0;
  
  public static double calculaDistancia(float x1, float y1, float x2, float y2){
    double distancia = Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
    return distancia;
  }

  public static List<Ponto> convexHull(List<Ponto> pontos) {
    List<Ponto> listHull = new ArrayList<>();
    Collections.sort(pontos);
    //Calculando o ponto central...
    int somaX = 0;
    int somaY = 0; 
    int tamanho = pontos.size();
    for (int i = 0; i < tamanho; i++) {
      Ponto p = pontos.get(i);
      somaX+=p.x;
      somaY+=p.y;
    }
    float xCentral = somaX/tamanho;
    float yCentral = somaY/tamanho;
    System.out.println(pontos);
    System.out.println(xCentral+" "+yCentral);
    double maiorDistancia = 0;
    Ponto maisLonge = new Ponto();
    for (int e = 0; e < pontos.size(); e++) {
      maiorDistancia = 0;
      for(int i=0; i<pontos.size();i++){
        Ponto p = pontos.get(i);
        double distancia = calculaDistancia(xCentral, yCentral, p.x,p.y);
        if(distancia>maiorDistancia){
          maiorDistancia = distancia;
          maisLonge = pontos.get(i);
        }
      }
      boolean adicionar = true;
      for (int i = 0; i < listHull.size(); i++) {
        Ponto p = listHull.get(i);
        //Se o mais longe for menor que um ponto já existente na lista não adiciona ele
        if(p.compareTo(maisLonge)<0);
         // adicionar = false;
      }
      if(adicionar)
        listHull.add(maisLonge);
      pontos.remove(maisLonge);
    }
        
    return listHull;
  }

  public static void main(String[] args) {
    //Importando os pontos do arquivo de texto
    List<Ponto> pontos = Pontos.importarPontos("pontos10k.txt", " ");
    //Para verificar o consumo de memória
    Runtime execucao = Runtime.getRuntime();
    //Chamar o coletor de lixo
    execucao.gc();
    //Memória antes
    long mi = execucao.totalMemory() - execucao.freeMemory();
    //Tempo antes
    long ti = System.currentTimeMillis();
    //Calculando o convexHull
    List<Ponto> hull = convexHull(pontos);
    //Memória depois
    long mf = execucao.totalMemory() - execucao.freeMemory();
    long mt = mf-mi;
    long tf = System.currentTimeMillis();
    long td = tf - ti;
    System.out.println("Foram necessários " + td + "ms");
    System.out.println("Foram usados "+ mt +" bytes de memória");
    System.out.println("Foram executadas "+ contIteracoes+ " iterações de looping.");

    //Mostrando a lista de pontos
    for (Ponto p : hull) {
      System.out.println("(" + p.getX() + ", " + p.getY() + ")");
    }
  }
}
