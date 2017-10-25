class Buffer
{
    private Reading reading;
    private float[] readings;
    private boolean available = false;

    Buffer()
    {
        reading = new Reading();
        readings = new float[181]; 
    }   

  public synchronized boolean wasModified()
  {
    return available;
  }

    public synchronized Reading get()
    {
        while (available == false)
        {
            try
            {
                wait();
            }
            catch (InterruptedException e) {}
        }
        available = false;
        notifyAll();
        return reading;
    }

  public synchronized float read(int i)
    {
      return readings[i];
    }

    public synchronized void put(int angle, int dist)
    {
        while (available == true)
        {
        try
            {
                wait();
            }
            catch (InterruptedException e) {}
        }
        reading.set(angle, dist);
        readings[angle] = dist;
        available = true;
        notifyAll();
    }
}
