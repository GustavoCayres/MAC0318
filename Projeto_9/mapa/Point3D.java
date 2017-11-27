
public class Point3D {
	public double x, y, z;
	public Point3D(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z; // em radianos
	}
	public Point3D() {
		this.x = (int )(Math.random() * 100 - 50);
		this.y = (int )(Math.random() * 100 - 50);
		this.z = (int )(Math.random() * Math.PI * 2.0);
	}
}
