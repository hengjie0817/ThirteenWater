package dsada;
import java.util.regex.Pattern;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

public class http {
	public static void main(String []args) throws Throwable
	{
		
				http h=new http();
				
				//thirteenwater hengjie=new thirteenwater();
				//String a=hengjie.workcard("&8 *8 #8$9 *9 $10 #10 &J&Q #Q $K #K $A");
				//System.out.println(a);
				//h.zhuce();
				String tocken=h.dengru();
				//System.out.println(tocken);
				//System.out.println(bodyout);
				while(true)
				{
					//System.out.println(tocken);
					
				try{
					String body=h.start(tocken);
					int s=body.indexOf("card\":\"");
					int e=body.indexOf("\"}");
					String card=body.substring(s+7, e);
					thirteenwater work=new thirteenwater();
					String cardout=work.workcard(card);
					s=body.indexOf("id\":");
					e=body.indexOf(",\"card");
					String id=body.substring(s+4, e);
					String bodyout=new String();
					bodyout="{\"id\":"+id+",\"card\":[\""+cardout;
					bodyout=bodyout+"\"]}";
					h.handin(tocken,bodyout);
				}catch(Exception e1){}
				}
				

				
				
			
	}
	
	public void zhuce() throws UnirestException
	{
		HttpResponse<String> response = Unirest.post("https://api.shisanshui.rtxux.xyz/auth/bind")
				  .header("content-type", "application/json")
				  .header("x-auth-token", "80ded46b-03f0-445b-8899-01734ff2fa35")
				  .body("{\"student_number\":\"x\",\"student_password\":\"wt8769054\"}")
				  .asString();
		System.out.println(response.getBody());
	}
	
	public String dengru() throws UnirestException
	{
		HttpResponse<String> response = Unirest.post("https://api.shisanshui.rtxux.xyz/auth/login")
				  .header("content-type", "application/json")
				  .body("{\"username\":\"jayer\",\"password\":\"hhj123456789\"}")
				  .asString();
		System.out.println(response.getBody());
		String body=response.getBody();
		int addstart=body.indexOf("user_id\":");
		int addend=body.indexOf(",\"token");
		String id=body.substring(addstart+9,addend);
		addstart=body.indexOf("token\":\"");
		addend=body.indexOf("\"}");
		String tocken=body.substring(addstart+8, addend);
		//System.out.println(id);
		//System.out.println(tocken);
		return tocken;
	}
	
	public String start(String a) throws UnirestException
	{
		HttpResponse<String> response = Unirest.post("https://api.shisanshui.rtxux.xyz/game/open")
				  .header("x-auth-token", a)
				  .asString();
		String body=response.getBody();
		return body;
	}
	
	public int handin(String a,String b) throws UnirestException
	{
		HttpResponse<String> response = Unirest.post("https://api.shisanshui.rtxux.xyz/game/submit")
				  .header("content-type", "application/json")
				  .header("x-auth-token", a)
				  .body(b)
				  .asString();
		String res=response.getBody();//Illegal combinations
		String r="Illegal combinations";
		System.out.println(b);
		System.out.println(response.getBody());
		if(Pattern.matches(".*"+r+".*", res)) 
			{
				System.out.println(b);
				return 0;
			}
		else return 1;
	}
}
