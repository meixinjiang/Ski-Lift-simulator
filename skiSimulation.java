import java.util.*;
import java.util.concurrent.*;

public class skiSimulation {
	
	static int seatsNumber = 10;
	static int liftSpeed = 1000;
	static int skiersNumber = 30;
	static int slopeTime = 12000;
	static double probability = 0.05;
    
    public skiSimulation(){
    	this(10, 1000, 30, 12000, 0.05);
    }
    
	public skiSimulation(int sN, int lS, int skN, int sT, double p) {
		seatsNumber = sN;
		liftSpeed = lS;
		skiersNumber = skN;
		slopeTime = sT;
		probability = p;
	}
	
	public static int getSeatsNumber() {return seatsNumber;}
	public static int getLiftSpeed() {return liftSpeed;}
	public static int getSkiersNumber() {return skiersNumber;}
	public static int getSlopeTime() {return slopeTime;}
	public static double getProbability() {return probability;}
	
	public static void setSeatsNumber(int sN) {seatsNumber = sN;}
	public static void setLiftSpeed(int lS) {liftSpeed = lS;}
	public static void setSkiersNumber(int skN) {skiersNumber = skN;}
	public static void setSlopeTime(int sT) {slopeTime = sT;}
	public static void setProbability(double p) {probability = p;}
	
	public static void main(String[] args) throws InterruptedException {
		skiSimulation.simulate();
	}

	public static String simulate() throws InterruptedException {
		
		BlockingQueue<String> waitQueue = new LinkedBlockingQueue<String>();
		BlockingQueue<String> liftQueue = new LinkedBlockingQueue<String>();
		
    	for (int i=1; i<skiSimulation.getSeatsNumber()+1;i++){
    		liftQueue.add("EMPTY");
    	}
    	
    	for (int k=0; k<skiSimulation.getSkiersNumber();k++){
    		waitQueue.add(Integer.toString(k));
    	}
    	
    	Random random = new Random();
    	
		while (true){
			int onLift = 0;
			for (String e : liftQueue) {
				if (!e.equals("EMPTY")){
					onLift += 1;
				}
			}
			
			int inWait = 0;
			for (String a : waitQueue) {
				inWait += 1;
			}
			
			System.out.println("On Lift " + "(" + onLift +"): "  + liftQueue);
			System.out.println("In Queue " + "(" + inWait +"): "  + waitQueue);
			
	    	boolean happens = random.nextDouble() < skiSimulation.getProbability();

			if(happens){
				long time = (long) (Math.random() * 8000);
				System.out.println("Lift stops temporarily for " + time + " milliseconds.");
				Thread.sleep(time);
				System.out.println("Lift continues operation.");
			}
			else{
				String skier = liftQueue.take();
				if (!skier.equals("EMPTY")){
					Random r = new Random();
					int i1 = r.nextInt(skiSimulation.getSlopeTime() - 2000 + 1) + 2000;
					skiing slope = new skiing(skier, waitQueue, i1);
					slope.start();
				}
				if (waitQueue.isEmpty()){
					liftQueue.put("EMPTY");
				}
				else{
					liftQueue.put(waitQueue.take());
					Thread.sleep(skiSimulation.getLiftSpeed());
				}
			}
		}
	}	
}
