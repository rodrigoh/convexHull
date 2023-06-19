package convexHull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
/**
 * 
 * @author Rodrigo Henrich
 */

public class MonotoneChain {
  private static int contIteracoes = 0;
  /**
   * Calcula o produto vetorial entre (O, A) e (O, B)
   * @param O 
   * @param A
   * @param B
   * @return positivo se o giro entre os pontos for anti-horário e negativo se for horário
   */
  public static float produtoVetorial(Ponto O, Ponto A, Ponto B) {
    return (A.x - O.x) * (B.y - O.y) - (A.y - O.y) * (B.x - O.x);
  }
  
  /**
   * Retorna a lista de pontos ordenados em ordem anti-horário
   * @param A lista de pontos a ser analisada
   * @return lista de pontos que fazem parte do convexHull
   */
  public static List<Ponto> convexHull(List<Ponto> A) {
    int n = A.size(), k = 0;

    if (n <= 3) {
      return A;
    }

    List<Ponto> hullList = new ArrayList<>();

    //Ordena os pontos
    Collections.sort(A);

    //Parte de baixo do convexHull (percorrendo do ponto mais a esquerda até o ponto mais a direita)
    for (int i = 0; i < n; ++i) {
      //Contando as iterações de todos os loopings
      contIteracoes++;
      //Se o ponto na posição K-1 não faz parte do casco como vetor de hullList[k-2] para hullList[k-1] e hullList[k-2] para A[i] tem um giro no sentido horário
      while (k >= 2 && produtoVetorial(hullList.get(k - 2), hullList.get(k - 1), A.get(i)) <= 0) {
        hullList.remove(--k);
      }
      hullList.add(A.get(i));
      k++;
    }

    //Parte de cima do convexHull (porém percorrendo do ponto mais a direita até ponto mais a esquerda)
    for (int i = n - 2, t = k; i >= 0; --i) {
      //Contando as iterações de todos os loopings
      contIteracoes++;
      //Se o ponto na posição K-1 não fizer parte do casco como vetor de hullList[k-2] para hullList[k-1] e hullList[k-2] para A[i] tem uma volta no sentido horário
      while (k > t && produtoVetorial(hullList.get(k - 2), hullList.get(k - 1), A.get(i)) <= 0) {
        hullList.remove(--k);
      }
      hullList.add(A.get(i));
      k++;
    }

    //Remove o elemento duplicado no final da lista
    hullList.remove(hullList.size() - 1);

    return hullList;
  }

  //Chamando o método
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
    //Tempo inicial
    long ti = System.currentTimeMillis();
    //Calculando o convexHull
    List<Ponto> hull = convexHull(pontos);
    //Memória depois
    long mf = execucao.totalMemory() - execucao.freeMemory();
    long mt = mf-mi;
    //Tempo final
    long tf = System.currentTimeMillis();
    long td = tf - ti;
    System.out.println("Foram necessários " + td + "ms");
    System.out.println("Foram usados "+ mt +" bytes de memória");
    System.out.println("Foram executadas "+ contIteracoes+ " iterações de looping.");
    System.out.println("Pontos que formam o casco convexo "+ hull.size());
    Collections.sort(hull);
    //Mostrando a lista de pontos
    for (Ponto p : hull) {
      System.out.println("(" + p.getX() + ", " + p.getY() + ")");
    }
  }
}
