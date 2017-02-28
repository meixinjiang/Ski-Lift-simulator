import java.util.concurrent.BlockingQueue;

class skiing extends Thread{
    	String skier;
    	BlockingQueue<String> waitQueue;
    	int slopeTime;
    	
	    public skiing(String skier, BlockingQueue<String> waitQueue, int slopeTime){
	    	this.skier = skier;
	    	this.waitQueue = waitQueue;
	    	this.slopeTime = slopeTime;
	    }
	    
		public void run(){
			try {
				Thread.sleep(slopeTime);
				waitQueue.put(skier);
			} catch (InterruptedException e) {}
		}
    }
    	