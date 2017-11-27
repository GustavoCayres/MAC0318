import java.util.ArrayList;

public class DiscreteSpace extends ArrayList<Double>{
	/* faz com que a soma seja 1 */
	void normalize () {
		double sum = sum();
		for (int i = 0; i < size(); i++) {
			set(i, get(i)/sum);
		}
	}
	
	/* retorna a soma de todos os elementos */
	double sum () {
		double sum = 0;
		for(Double v: this) {
			sum += v;
		}
		return sum;
	}

	/* retorna a soma de todos os elementos */
	double max () {
		double max = 0;
		for(Double v: this) {
			max = Math.max(v, max);
		}
		return max;
	}
}
