import java.sql.Connection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.chrono.ChronoLocalDate;
import java.util.Calendar;
import java.util.Date;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class SuspendUserActivity {
	
	MqttClient client_transreport;
	Global_function gf = new Global_function(true);
    Interface_ga inter_login;
    Connection con;
    SQLConnection sqlcon = new SQLConnection();
    int counter = 1;
    
	public SuspendUserActivity() {
		
	}
	
	String Parser_TASK,
    Parser_ID,
    Parser_SOURCE,
    Parser_COMMAND,
    Parser_OTP,
    Parser_TANGGAL_JAM,
    Parser_VERSI,
    Parser_HASIL,
    Parser_FROM,
    Parser_TO,
    Parser_SN_HDD,
    Parser_IP_ADDRESS,
    Parser_STATION,
    Parser_CABANG,
    Parser_NAMA_FILE,
    Parser_CHAT_MESSAGE,
    Parser_REMOTE_PATH,
    Parser_LOCAL_PATH,
    Parser_SUB_ID; 
    
    public void UnpackJSON(String json_message){
        
        JSONParser parser = new JSONParser();
        JSONObject obj = null;
        try {
            obj = (JSONObject) parser.parse(json_message);
        } catch (org.json.simple.parser.ParseException ex) {
            System.out.println("message json : "+json_message);
            System.out.println("message error : "+ex.getMessage());
            //ex.printStackTrace();
            //Logger.getLogger(IDMReport.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            Parser_TASK = obj.get("TASK").toString();
        } catch (Exception ex) {
             Parser_TASK = "";
        }       
        try{
            Parser_ID = obj.get("ID").toString();
        }catch(Exception exc){
            Parser_ID = "";
        }
        try{
            Parser_SOURCE = obj.get("SOURCE").toString();
        }catch(Exception exc){
            Parser_SOURCE = "";
        }
        try{
            Parser_COMMAND = obj.get("COMMAND").toString();
        }catch(Exception exc){
            Parser_COMMAND = "";
        }
          try{
           Parser_OTP = obj.get("OTP").toString();
        }catch(Exception exc){
            Parser_OTP = "";
        }
        
        
        try{
           Parser_TANGGAL_JAM = obj.get("TANGGAL_JAM").toString();
        }catch(Exception exc){
            Parser_TANGGAL_JAM = "";
        }
        try{
            Parser_VERSI = obj.get("RESULT").toString().split("_")[7];
        }catch(Exception exc){
            try{
                Parser_VERSI = obj.get("VERSI").toString();
            }catch(Exception exc1){ Parser_VERSI = "";}
            
        }

        try{
            Parser_HASIL = obj.get("HASIL").toString();
            Parser_FROM = obj.get("FROM").toString();
            Parser_TO = obj.get("TO").toString();

        }catch(Exception exc){
            Parser_HASIL = "";
            Parser_FROM = "";
            Parser_TO = "";
        }
       
        try{
            Parser_SN_HDD = obj.get("SN_HDD").toString();
        }catch(Exception exc){
            try{
                Parser_SN_HDD = obj.get("SN_HDD").toString();
            }catch(Exception exc1){Parser_SN_HDD = "";}
            
        }
        try{
            Parser_IP_ADDRESS = obj.get("IP_ADDRESS").toString();
        }catch(Exception exc){
            try{
                Parser_IP_ADDRESS = obj.get("IP_ADDRESS").toString();    
            }catch(Exception exc1){
                Parser_IP_ADDRESS = "";
            }

        }
        
        try{
            Parser_STATION = obj.get("STATION").toString();
        }catch(Exception exc){
            try{
                Parser_STATION = obj.get("STATION").toString();
            }catch(Exception exc1){Parser_STATION = "";}
            
        }
        
        try{
           Parser_CABANG = obj.get("CABANG").toString();
        }catch(Exception exc){
            try{
                Parser_CABANG = obj.get("CABANG").toString();
            }catch(Exception exc1){Parser_CABANG = "";}
        }
        
        try{
            Parser_NAMA_FILE = obj.get("NAMA_FILE").toString();
        }catch(Exception exc){
            Parser_NAMA_FILE = "";
        }
        try{
            Parser_CHAT_MESSAGE = obj.get("CHAT_MESSAGE").toString();
        }catch(Exception exc){
            Parser_CHAT_MESSAGE = "";
        }
        try{
            Parser_REMOTE_PATH = obj.get("REMOTE_PATH").toString();
        }catch(Exception exc){
            Parser_REMOTE_PATH = "";
        }
        try{
            Parser_LOCAL_PATH = obj.get("LOCAL_PATH").toString();
        }catch(Exception exc){
            Parser_LOCAL_PATH = "";
        }
        try{
            Parser_SUB_ID = obj.get("SUB_ID").toString();
        }catch(Exception exc){
            Parser_SUB_ID = "";
        }
        
     }
	
	public void send_TOIDMCommandV2Bot(String task,String topic,String res_message_command) {
    	try {
    		
    		 Parser_TASK = task;
    		 Parser_ID = gf.get_id(false);
    		 Parser_SOURCE = "IDMReporter";
    		 
    		 Parser_COMMAND = res_message_command;
    		 Parser_OTP = "-";
    		 Parser_TANGGAL_JAM = gf.get_tanggal_curdate_curtime();
    		 Parser_VERSI = "1.0.1";
    		 Parser_HASIL = "-";
    		 Parser_FROM = "IDMReporter";
             Parser_IP_ADDRESS = "172.24.52.3";
             Parser_STATION = "-";
             Parser_CABANG = "HO";
             Parser_NAMA_FILE = "-";
             Parser_CHAT_MESSAGE = topic;
             Parser_TO = "IDMCommandV2Bot";
             Parser_TASK = task;
             Parser_REMOTE_PATH = "-";
             Parser_LOCAL_PATH = "-";
             Parser_SUB_ID = gf.get_id(true);
             Parser_SN_HDD = "-";
             
             
             
             String res_message_idmcommandv2bot = gf.CreateMessage(Parser_TASK,Parser_ID,Parser_SOURCE,Parser_COMMAND,Parser_OTP,Parser_TANGGAL_JAM,Parser_VERSI,Parser_HASIL,Parser_FROM,Parser_TO,Parser_SN_HDD,Parser_IP_ADDRESS,Parser_STATION,Parser_CABANG,"",Parser_NAMA_FILE,Parser_CHAT_MESSAGE,Parser_REMOTE_PATH,Parser_LOCAL_PATH,Parser_SUB_ID);
             //System.err.println("Message SENT IDMCommandV2Bot : "+res_message_idmcommandv2bot);
             
             byte[] convert_message_idmcommandv2bot = res_message_idmcommandv2bot.getBytes("US-ASCII");
             byte[] bytemessage_idmcommandv2bot = gf.compress(convert_message_idmcommandv2bot);
            
             gf.PublishMessageDocumenter(true,1,topic,bytemessage_idmcommandv2bot,counter,res_message_idmcommandv2bot,1); 
             gf.WriteLog("log_idmreporter", "SEND TO IDMCommandV2Bot : "+res_message_idmcommandv2bot+"\n"+topic, true);
             System.out.println("SEND TO IDMCommandV2Bot : "+res_message_idmcommandv2bot+"\n"+topic);
    	}catch(Exception exc) {
    		exc.printStackTrace();
    		String content_error = exc.toString();
            gf.WriteLog("error_send_bot", content_error, true);
    	}
    }
	
	public void Publish_log(String pesan_log) {
		String task = "FLAG_SUSPEND";
		String topic = "MONITORING_BACKEND/";
		JSONObject obj_command = new JSONObject();
		obj_command.put("TANGGAL", gf.get_tanggal_curdate_curtime());
		obj_command.put("TASK",task);
		obj_command.put("TOPIC",topic);
		obj_command.put("PESAN",pesan_log);
		obj_command.put("CHAT_ID", "532860640");
		String command_message = obj_command.toJSONString();
		send_TOIDMCommandV2Bot(task,topic,command_message);
	}
	
	public void FlagSuspend_User() {
		try {
			
			String get_min_date = gf.GetTransReport("SELECT DATE(MIN(LAST_AKSES)) AS TANGGAL_MIN FROM rekap_penggunaan_idmcommand_last_akses", 1, true);
			String query_list_nik = "SELECT a.NIK AS NIK\r\n"
					+ "	FROM idm_org_structure a LEFT JOIN rekap_penggunaan_idmcommand_last_akses b ON b.NIK=a.NIK\r\n"
					+ "	WHERE (b.NIK IS NULL OR DATE(LAST_AKSES) = '"+get_min_date+"')\r\n"
					+ "	AND a.NAMA NOT LIKE '%SIMULASI%'\r\n"
					+ "	AND a.LOCATION != 'HO'\r\n"
					+ "	ORDER BY a.LOCATION\r\n"
					+ "	;";
			
			gf.WriteLog("suspend_activity", "Query List NIK : "+query_list_nik, true);
			Publish_log("Query List NIK : "+query_list_nik);
			
			String res_nik = "";
			String get_list_nik = gf.GetTransReport(query_list_nik, 1, false);
			String sp_record[] = get_list_nik.split("~");
			for(int i = 0;i<sp_record.length;i++) {
				String sp_field[] = sp_record[i].split("%");
				String nik = sp_field[0];
				
				
				if(i == (sp_record.length-1)) {
					res_nik = res_nik+nik+"";
				}else {
					res_nik = res_nik+nik+",";
				}
				
			}
			
			
			String query_upd_suspend = "UPDATE idm_org_structure SET IS_AKTIF = '0' WHERE NIK IN("+res_nik+");";
			gf.WriteLog("suspend_activity", "Flag Query : "+query_upd_suspend, true);
			Publish_log("Flag Query : "+query_upd_suspend);
			gf.ChangeData(query_upd_suspend);
			
			
			
			gf.WriteLog("suspend_activity", "Selesai Flag Suspend", true);
			Publish_log("Selesai Flag Suspend");
			
		}catch(Exception exc) {
			exc.printStackTrace();
			gf.WriteLog("error_suspend_activity", "ERROR FLAG SUSPEND : "+exc.toString(), true);
			Publish_log( "ERROR FLAG SUSPEND : "+exc.toString());
		}
	}
	
	public void iterateBetweenDatesJava7(Date start, Date end) {
		try {
	    Date current = start;
	    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		
	    	while (current.before(end)) {
		        
		        Calendar calendar = Calendar.getInstance();
		        calendar.setTime(current);
		        calendar.add(Calendar.DATE, 1);
		        current = calendar.getTime();
		        //System.out.println("current : "+current);
		        String current_loop = formatter.format(current);
		        //System.out.println("current_conv : "+current_loop);
		        String query_last_akses = "CALL CURSOR_REKAP_PENGGUNAAN_IDMCOMMAND_LAST_AKSES('"+current_loop+"','"+current_loop+"','%');";
		        //System.out.println("query_last_akses : "+query_last_akses);
		        gf.WriteLog("suspend_activity", query_last_akses, true);
		        //Publish_log(query_last_akses);
		        
		        String hasil_ins = gf.GetTransReport(query_last_akses, 1, true);
		        
		        //System.out.println("Selesai Proses : "+current_loop+" : "+hasil_ins);
		        gf.WriteLog("suspend_activity", "Selesai Proses : "+current_loop+" : "+hasil_ins, true);
		        Publish_log("Selesai Proses : "+current_loop+" : "+hasil_ins);
		        String tanggal_jam = gf.get_tanggal_curdate_curtime();
                gf.WriteFile("timemessage.txt", "", tanggal_jam, false);
		        Thread.sleep(30000);
		        
		        
		        
		    }
	    	
	    	FlagSuspend_User();
		
		}catch(Exception exc) {
			exc.printStackTrace();
			gf.WriteLog("error_suspend_activity", "ERROR ITERASI TANGGAL : "+exc.toString(), true);
			Publish_log("ERROR ITERASI TANGGAL : "+exc.toString());
		}
	}
	
	public void GetDataLastAksesUser() {
		try {
			
			String query_date_sub = "SELECT DATE_SUB(CURDATE(),INTERVAL 1 MONTH) AS TANGGAL_SUB,CURDATE() AS TANGGAL_KINI";
			String get_tanggal[] = gf.GetTransReport(query_date_sub, 2, false).split("~")[0].split("%");
			String tanggal_awal = get_tanggal[0];
			String tanggal_kini = get_tanggal[1];
			
			//System.out.println("tanggal awal : "+tanggal_awal);
			//System.out.println("tanggal akhir : "+tanggal_kini);
			
			String pesan_log = "Initial tanggal awal : "+tanggal_awal+" S/d "+"tanggal akhir : "+tanggal_kini;
			gf.WriteLog("suspend_activity",pesan_log , true);
			Publish_log(pesan_log);
			
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			Date startDate = formatter.parse(tanggal_awal);
			Date endDate = formatter.parse(tanggal_kini);
			
			iterateBetweenDatesJava7(startDate,endDate);
			
			
			
		}catch(Exception exc) {
			exc.printStackTrace();
			gf.WriteLog("error_suspend_activity", "ERROR INITIAL PROSES : "+exc.toString(), true);
			Publish_log("ERROR INITIAL PROSES : "+exc.toString());
		}
	}
	
	
	public void Run() {
		  System.out.println("=================================          START SUSPEND ACTIVITY         ==================================");   
	      try {
	    	  client_transreport = gf.get_ConnectionMQtt(1);
	    	  GetDataLastAksesUser();
	        }catch(Exception exc){
	            exc.printStackTrace();
	        }  
	}
	
}
