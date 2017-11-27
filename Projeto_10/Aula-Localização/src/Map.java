import java.util.ArrayList;
import java.util.Iterator;

public class Map implements Iterable<Double[]>{
	ArrayList<Double[]> data;
	Map () {
		data =  new ArrayList<Double[]>();
	}
	
	void add (double ini, double end) {
		Double[] x = {ini, end};
		data.add(x);
	}
	
	@Override
	public Iterator<Double[]> iterator() {
		return data.iterator();
	}
	
	int size () {
		return data.size();
	}
}
