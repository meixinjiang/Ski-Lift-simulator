
import java.util.List;
import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import javax.swing.*;

public class MySwingWorker extends SwingWorker<String, String> {
    JTextArea textArea;
    String sim;

    public MySwingWorker(JTextArea textArea){
        this.textArea = textArea;

    }

	@Override
    public String doInBackground() throws Exception {
		
		BlockingQueue<String> waitQueue = new LinkedBlockingQueue<String>();
		BlockingQueue<String> liftQueue = new LinkedBlockingQueue<String>();
		
    	for (int i=1; i<skiSimulation.getSeatsNumber()+1;i++){
    		liftQueue.add("EMPTY");
    	}
    	
    	for (int k=0; k<skiSimulation.getSkiersNumber();k++){
    		waitQueue.add(Integer.toString(k+1));
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
			
			publish("On Lift " + "(" + onLift +"): "  + liftQueue);
			publish("In Queue " + "(" + inWait +"): "  + waitQueue);
			
	    	boolean happens = random.nextDouble() < skiSimulation.getProbability();

			if(happens){
				long time = (long) (Math.random() * 8000);
				publish("Lift stops temporarily for " + time + " milliseconds.");
				Thread.sleep(time);
				publish("Lift continues operation.");
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
	
    @Override
    protected void process(List<String> strings) {
        for (String i : strings)
            textArea.append(i + "\n");
    }

}
