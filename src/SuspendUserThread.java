import java.util.Calendar;

public class SuspendUserThread extends Thread{
	SuspendUserActivity act;
	public SuspendUserThread() {
		act = new SuspendUserActivity();
	}
	
	public void run(){
		 for(int l = 0;l<1;l++){
	           try{
	        	   
	        	   int jam = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
	        	   int menit = Calendar.getInstance().get(Calendar.MINUTE);
	        	   if((jam == 8 && menit <= 45)) {
	        		  act.Run();
	        	   }else {
	        		   System.out.println("Tidak melakukan rekap, Belum Jadwalnya !!!");
	        	   }
	        	   
	           }catch(Exception exc){
	               
	           }
	           
	        }
            
	} 
}
