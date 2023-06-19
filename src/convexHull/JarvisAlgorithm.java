package convexHull;

/**
 *
 * @author Rodrigo Henrich
 */
import java.util.ArrayList;
import java.util.List;

public class JarvisAlgorithm {
  
  private static int contIteracoes = 0;

  public static List<Ponto> convexHull(List<Ponto> pontos) {
    int n = pontos.size();
    if (n < 3) {
      throw new IllegalArgumentException("Precisa ter pelo menos três pontos");
    }

    List<Ponto> hullList = new ArrayList<>();
    //Achando o ponto mais a esquerda
    int maisEsquerda = 0;
    for (int i = 1; i < n; i++) {
      //Contando as iterações de todos os loopings
      contIteracoes++;
      if (pontos.get(i).x < pontos.get(maisEsquerda).x) {
        maisEsquerda = i;
      }
    }
    //Depois testa a partir dele para determinar os próximos pontos até chegar nele novamente
    int p = maisEsquerda;
    int q;
    do {
      hullList.add(pontos.get(p));
      q = (p + 1) % n;
      for (int i = 0; i < n; i++) {
        //Contando as iterações de todos os loopings
        contIteracoes++;
        if (orientacao(pontos.get(p), pontos.get(i), pontos.get(q)) == 2) {
          q = i;
        }
      }
      p = q;
    } while (p != maisEsquerda);

    return hullList;
  }
  /**
   * Calcula a orientação entre os pontos p, q e r
   * @param p
   * @param q
   * @param r
   * @return 0 se os pontos são colineares 1 se eles ficam em sentido horário e 2 se ficam em sentido anti-horária
   */
  private static int orientacao(Ponto p, Ponto q, Ponto r) {
    float result = (q.y - p.y) * (r.x - q.x) - (q.x - p.x) * (r.y - q.y);
    if (result == 0) {
      return 0;
    } 
    else if (result > 0) {
      return 1;
    } 
    else {
      return 2;
    }
  }

  public static void main(String[] args) {
    //Importando os pontos do arquivo de texto
    List<Ponto> pontos = Pontos.importarPontos("estadoRS.txt", " ");
    //List<Ponto> pontos = Pontos.importarPontos("pontosinicial.txt", " ");
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
    System.out.println("Pontos que formam o conjunto convexo "+ hull.size());

    //Mostrando a lista de pontos
    for (Ponto p : hull) {
      System.out.println("(" + p.getX() + ", " + p.getY() + ")");
    }
  }
}
