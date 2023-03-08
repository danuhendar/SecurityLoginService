import java.io.FileInputStream;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.Properties;
import java.util.UUID;

import org.bson.Document;
import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.JSONParser;




public class SecurityLoginService {
	String maxvmusepercent;
    String cleansession;
    boolean res_cleansession = false;
    String keepalive;
    int res_keepalive = 70;
    String reconnect;
    boolean res_reconnect = false;
    String limit_read;
    boolean res_limit_read = false;
    String flag_read_initial;
    boolean res_flag_read_initial = false;
    String read_command_topic_hastag;
    boolean res_read_command_topic_hastag = false;
    MqttClient client_transreport_login;
    MqttClient client_transreport;
    String will_retained;
    boolean res_will_retained = false;
    String ip_mongo_db;
    Global_function gf = new Global_function(true);
    Interface_ga inter_login;
    Connection con;
    SQLConnection sqlcon = new SQLConnection();
    int counter = 1;
    
	public SecurityLoginService() {
		 
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
    		
             Parser_CHAT_MESSAGE = topic;
             Parser_SOURCE = "IDMReporter";
             Parser_IP_ADDRESS = "172.24.52.3";
             Parser_FROM = "IDMReporter";
             Parser_TO = "IDMCommandV2Bot";
             Parser_TASK = task;
             
             String res_message_idmcommandv2bot = gf.CreateMessage(Parser_TASK,Parser_ID,Parser_SOURCE,res_message_command,Parser_OTP,Parser_TANGGAL_JAM,Parser_VERSI,Parser_HASIL,Parser_FROM,Parser_TO,Parser_SN_HDD,Parser_IP_ADDRESS,Parser_STATION,Parser_CABANG,"",Parser_NAMA_FILE,Parser_CHAT_MESSAGE,Parser_REMOTE_PATH,Parser_LOCAL_PATH,Parser_SUB_ID);
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
    
    public String get_identitas_user(String nik_bawahan,String ip_login,String type,String via,String pass) {
    	String res = "";
    	try {
    		
    		String tahun_bulan_tanggal = gf.get_tanggal_curdate().replaceAll("-", "");
            String nama_table_create = "transreport"+tahun_bulan_tanggal;
            
    		String query = "SELECT v.*,\r\n"
    				+ "					(SELECT\r\n"
    				+ "						GROUP_CONCAT(l.ID,\"|\",k.NAMA,\"|\",k.CHAT_ID) AS CHAT_ID_ATASAN\r\n"
    				+ "						FROM m_struktur_jabatan l LEFT JOIN idm_org_structure k ON k.JABATAN=l.CONTENT \r\n"
    				+ "						WHERE l.ID > v.ID\r\n"
    				+ "							AND l.KODE = v.KODE\r\n"
    				+ "							AND k.LOCATION = v.LOCATION\r\n"
    				+ "							AND k.JABATAN NOT LIKE 'SUPPORT%'\r\n"
    				+ "							AND k.CHAT_ID IS NOT NULL \r\n"
    				+ "							AND k.CHAT_ID != '0' AND LENGTH(CHAT_ID) > 1\r\n"
    				+ "							) AS CHAT_ID_ATASAN\r\n"
    				+ "							\r\n"
    				+ "    					FROM (SELECT a.LOCATION,\r\n"
    				+ "    					a.NIK,\r\n"
    				+ "    					a.NAMA,\r\n"
    				+ "    					a.JABATAN,\r\n"
    				+ "    					a.BAGIAN,\r\n"
    				+ "    					a.CHAT_ID,\r\n"
    				+ "    					b.ID,\r\n"
    				+ "    					b.KODE,\r\n"
    				+ "    					(SELECT CONTENT FROM m_struktur_jabatan WHERE ID > b.ID AND KODE =  LEFT(a.LOCATION,1) ORDER BY ID ASC LIMIT 0,1) AS JABATAN_ATASAN,\r\n"
    				+ "    				IFNULL((SELECT CONCAT(MAX(ADDTIME),'#',IP) AS LAST_LOGIN FROM "+nama_table_create+" WHERE TASK = 'LOGIN' AND CMD LIKE '"+nik_bawahan+"%' ORDER BY ROWID DESC LIMIT 0,1),':') AS LAST_LOGIN\r\n"
    				+ "    				\r\n"
    				+ "    				FROM idm_org_structure a INNER JOIN m_struktur_jabatan b ON a.JABATAN=b.CONTENT\r\n"
    				+ "    				) v\r\n"
    				+ "    				WHERE v.NIK = '"+nik_bawahan+"'\r\n"
    				+ "    				GROUP BY v.NIK\r\n"
    				+ "    				;";
    		
    		System.out.println("query : "+query);
    		String get_identitas_user = gf.GetTransReport(query, 11,false);
    		String sp_record[] = get_identitas_user.split("~");
    		System.out.println("Jumlah Data : "+sp_record.length);
    		JSONObject obj_command_for_bot = new JSONObject();
    		for(int i = 0;i<sp_record.length;i++) {
    			String sp_field[] = sp_record[i].split("%");
    			String res_location = sp_field[0];
    			String res_nik = sp_field[1];
    			String res_nama = sp_field[2];
    			String res_jabatan = sp_field[3];
    			String res_bagian = sp_field[4];
    			String res_chat_id_user = sp_field[5];
    			String res_id = sp_field[6];
    			String res_kode = sp_field[7];
    			String res_jabatan_atasan = sp_field[8];
    			String res_last_login = sp_field[9];
    			String res_chat_id_atasan = sp_field[10];
    			obj_command_for_bot.put("LOCATION", res_location);
    			obj_command_for_bot.put("NIK", res_nik);
    			obj_command_for_bot.put("NAMA", res_nama);
    			obj_command_for_bot.put("JABATAN", res_jabatan);
    			obj_command_for_bot.put("BAGIAN", res_bagian);
    			obj_command_for_bot.put("LOGIN_DARI_IP", ip_login);
    			obj_command_for_bot.put("CHAT_ID_ATASAN", res_chat_id_atasan);
    			obj_command_for_bot.put("CHAT_ID_USER", res_chat_id_user);
    			obj_command_for_bot.put("LAST_LOGIN", res_last_login);
    			obj_command_for_bot.put("TYPE", type);
    			obj_command_for_bot.put("VIA", via);
    			obj_command_for_bot.put("PASS", pass);
    		}
    		
    		res = obj_command_for_bot.toJSONString();
    	}catch(Exception exc) {
    		exc.printStackTrace();
    		String content_error = exc.toString();
            gf.WriteLog("error_identitas_user", content_error, true);
            
    	}
    	
    	return res;
    }
     
    public void SecurityLoginService(int qos_message_command,String topic_config) {
    	try {
    		String rtopic_config = topic_config;
    		System.out.println("SUBS : "+rtopic_config);
            client_transreport.subscribe(rtopic_config,qos_message_command,new IMqttMessageListener() {
                @Override
                public void messageArrived(final String topic, final MqttMessage message) throws Exception {
                   
                                Date HariSekarang_run = new Date();
                                String payload = new String(message.getPayload());

                                String msg_type = "";
                                String message_ADT_Decompress = "";
                                try{
                                    message_ADT_Decompress = gf.ADTDecompress(message.getPayload());
                                    msg_type = "json";
                                }catch(Exception exc){
                                    message_ADT_Decompress = payload;
                                    msg_type = "non json";
                                }
                                
                                String tanggal_jam = gf.get_tanggal_curdate_curtime();
                                gf.WriteFile("timemessage.txt", "", tanggal_jam, false);
                               

                                counter++;
                                UnpackJSON(message_ADT_Decompress);
                                gf.WriteLog("log_idmreporter","RECV : "+message_ADT_Decompress, true);
                                System.out.println("MESSAGE RECV : "+message_ADT_Decompress);
                                gf.PrintMessage2("RECV > "+rtopic_config+"",counter,msg_type,topic,Parser_TASK,Parser_FROM,Parser_TO,null,HariSekarang_run);
                                
                                String hasil_insert_db = gf.InsTransReport(Parser_TASK, Parser_ID, Parser_SOURCE, Parser_COMMAND, Parser_OTP,
    									Parser_TANGGAL_JAM, Parser_VERSI, Parser_HASIL, Parser_FROM, Parser_TO, Parser_SN_HDD,
    									Parser_IP_ADDRESS, Parser_STATION, Parser_CABANG, Parser_NAMA_FILE, Parser_CHAT_MESSAGE,
    									Parser_REMOTE_PATH, Parser_LOCAL_PATH, Parser_SUB_ID, Boolean.parseBoolean(gf.en.getTampilkan_query_console()), "INSERT", "transreport");
    							
                                
                                if(Parser_SOURCE.equals("IDMCommander")) {
                                	//-- publish ke IDMCommandV2Bot --//
                                    String res_task = "SECURITY_LOGIN";
                                    JSONParser parse = new JSONParser();
                                    JSONObject obj_command = (JSONObject) JSONValue.parse(Parser_COMMAND);
                                    String res_nik = obj_command.get("NIK").toString();
                                    String res_pass = obj_command.get("PASS").toString();
                                    
                                    String ip_login = "";
                                    
                                    String type = "";
                                    String via = "";
                                    if(Parser_HASIL.contains("DUPLICATE")) {
                                    	type = "DUPLICATE";
                                    	ip_login = Parser_HASIL.split(":")[1].trim();
                                    }else if(Parser_HASIL.contains("REMOTE")) {
                                    	type = "REMOTE_LOGIN";
                                    	via = Parser_HASIL.split(":")[1];
                                    	ip_login = Parser_IP_ADDRESS;
                                    }else {
                                    	type = "TIDAK DIKETAHUI";
                                    }
                                    String res_command_for_Bot = get_identitas_user(res_nik,ip_login,type,via,res_pass);
                                    String res_topic_IdmcommandV2Bot = "SECURITY_LOGIN/"+Parser_CABANG+"/IDMCommandV2Bot/";
                                    send_TOIDMCommandV2Bot(res_task,res_topic_IdmcommandV2Bot,res_command_for_Bot);
                                }
                                
                }
            });
    	}catch(Exception exc) {
    		exc.printStackTrace();
    	}
    }
    
    
   
	public void Run() {
		  System.out.println("=================================          START         ==================================");   
	      try {
	    	    client_transreport =  gf.get_ConnectionMQtt(1);
	            //---------------------------- COMMAND -----------------------//
	            int qos_message_command = 0;
	            String topic_config[] = gf.en.getTopic().split(",");
        		SecurityLoginService(qos_message_command,topic_config[0]);
	        }catch(Exception exc){
	            exc.printStackTrace();
	        }  
	    }
}
