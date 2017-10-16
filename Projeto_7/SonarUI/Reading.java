public class Reading
{
	private int angle;
	private int dist;

	Reading()
	{
		this.angle = 0;
		this.dist = 0;
	}

	Reading(int a, int d)
	{
		this.angle = a;
		this.dist = d;
	}

	public int getAngle()
	{
		return angle;
	}

	public int getDistance()
	{
		return dist;
	}

	public void set(int a, int d)
	{
		this.angle = a;
		this.dist = d;
	}

	public String toString()
	{
		return "Angle: " + this.angle + ", Distance: " + this.dist;
	}
}
