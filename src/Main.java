
public class Main {
	public static void main(String args[]) {
		try {
			
			Global_function gf = new Global_function(false);
			String tanggal_jam = gf.get_tanggal_curdate_curtime();
			gf.WriteFile("timemessage.txt", "", tanggal_jam, false);
			
			SecurityLoginService t1 = new SecurityLoginService();
			t1.Run();
			
			CheckThread t2 = new CheckThread();
			t2.start();
			
			
		}catch(Exception exc) {
			exc.printStackTrace();
		}
	}
}
