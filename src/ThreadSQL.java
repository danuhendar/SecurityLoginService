
public class ThreadSQL extends Thread {
	SecurityLoginService idm;
     
    public ThreadSQL(int num){
    	idm = new SecurityLoginService();
    }
    
    public void run(){
        for(int l = 0;l<1;l++){
           try{
        	   idm.Run();
           }catch(Exception exc){
               
           }
           
        }
    } 
}
