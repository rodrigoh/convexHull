
package convexHull;

/**
 *
 * @author Rodrigo henrich
 */
public class Ponto implements Comparable<Ponto> {

  float x;
  float y;

  public Ponto() {
  }

  
  public Ponto(float x, float y) {
    this.x = x;
    this.y = y;
  }
  

  public float getX() {
    return x;
  }

  public void setX(float x) {
    this.x = x;
  }

  public float getY() {
    return y;
  }

  public void setY(float y) {
    this.y = y;
  }
  
  
  /**
   * permite comparar dois pontos
   * @param p
   * @return se p.x==x realiza a ordenação pelo y, se os valores de x forem diferentes ordena pelo x. Se x<p.x o retorno é menor que 0 se x>p.x o retorno é maior que 0
   */
  @Override
  public int compareTo(Ponto p) {
    return Float.compare(x, p.x) != 0 ? Float.compare(x, p.x): Float.compare(y, p.y);
  }

  @Override
  public String toString() {
    return "("+x+", " + y + ")";
  }
  
  
}
