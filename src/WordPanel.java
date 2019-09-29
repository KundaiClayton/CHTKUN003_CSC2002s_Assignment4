/**
 * @author Kundai C Chatambudza`
 */
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.concurrent.CountDownLatch;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JLabel;



public class WordPanel extends JPanel implements Runnable {
	public class Threadd implements Runnable{
		WordRecord w;
		//AtomicInteger at  = new AtomicInteger(w.getSpeed()/100);
		//int current=at.get();
		public Threadd(WordRecord w){
			this.w=w;
		}
		public synchronized void run(){
			
			w.drop(w.getSpeed()/100);
		}
	}
		public static volatile boolean done;
		private WordRecord[] words;
		private int noWords;
		private int maxY;
		private Score score;
		private  JLabel missed;
		private  int totalWords;
		
		public void paintComponent(Graphics g) {
		    int width = getWidth();
		    int height = getHeight();
		    g.clearRect(0,0,width,height);
		    g.setColor(Color.red);
		    g.fillRect(0,maxY-10,width,height);

		    g.setColor(Color.black);
		    g.setFont(new Font("Helvetica", Font.PLAIN, 26));
		   //draw the words
		   //animation must be added 
		    for (int i=0;i<noWords;i++){	    	
		    	//g.drawString(words[i].getWord(),words[i].getX(),words[i].getY());
				//words[i].drop(20*i);
		    	g.drawString(words[i].getWord(),words[i].getX(),words[i].getY()+20);  //y-offset for skeleton so that you can see the words
		    }
		   
		  }
		  /**
		   * @method Updater
		   * @param score
		   * @param play
		   * @param missed
		   * @param totalWords
		   * makes sure the scores are up to date
		   */
		  public void Updater(Score score, boolean play,JLabel missed,int totalWords){
			this.score=score;
			done = play;
			 this.missed = missed;
			 this.totalWords = totalWords;
		}

		WordPanel(WordRecord[] words, int maxY) {
			System.out.println(Arrays.toString(words));

			this.words=words; //will this work?

			noWords = words.length;
			System.out.println(Arrays.toString(words)+" "+noWords+""+maxY);
			done=false;
			this.maxY=maxY;		
		}
	
		
		
		
		public void run() {
			
			while(done)
			{   
				
                int i = 0;
				for (i=0;i<noWords;i++){
					//create new thread
					Thread n=new Thread(new Threadd(words[i]));
					//start thread
					n.start();
					//words[i].drop(words[i].getSpeed()/200);
					
				}
				try{
					Thread.sleep(500);
				}
				catch(InterruptedException e){
						System.out.println(e);
				}
				//reset i to 0
				i=0;
				for (i=0;i<noWords;i++){
					if(words[i].getY()+20 > maxY-10){
						score.missedWord();
						missed.setText("Missed:" + score.getMissed()+ "    ");
						words[i].resetWord();
						
					}
				}
				if(totalWords == score.getTotal())
					{done = false;}
				repaint();
			    
			 }
			for (int i = 0;i<noWords;i++){
				words[i].resetWord();
				score.resetScore();
				missed.setText("Missed:" + score.getMissed()+ "    ");
			}
			
			repaint();
		
		}


	}


