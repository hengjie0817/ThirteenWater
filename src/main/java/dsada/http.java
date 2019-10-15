package dsada;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

public class http {
	public static void main(String []args) throws Throwable
	{
		http h=new http();
		for(int i=0;i<=100000;i++) h.dengru();
	}
	
	public void zhuce() throws UnirestException
	{
		HttpResponse<String> response = Unirest.post("https://api.shisanshui.rtxux.xyz/auth/register")
				  .header("content-type", "application/json")
				  .body("{\"username\":\"hhj0817\",\"password\":\"hhj1007\"}")
				  .asString();
		
		System.out.println("{\"username\":\"hhj0817\",\"password\":\"hhj1007\"}");
	}
	
	public void dengru() throws UnirestException
	{
		HttpResponse<String> response = Unirest.post("https://api.shisanshui.rtxux.xyz/auth/login")
				  .header("content-type", "application/json")
				  .body("{\"username\":\"hhj0817\",\"password\":\"hhj1007\"}")
				  .asString();
		String body=response.getBody();
		int addstart=body.indexOf("user_id\":");
		int addend=body.indexOf(",\"token");
		String id=body.substring(addstart+9,addend);
		addstart=body.indexOf("token\":\"");
		addend=body.indexOf("\"}");
		String tocken=body.substring(addstart+8, addend);
		//System.out.println(id);
		//System.out.println(tocken);
		start(tocken);
	}
	
	public void start(String a) throws UnirestException
	{
		HttpResponse<String> response = Unirest.post("https://api.shisanshui.rtxux.xyz/game/open")
				  .header("x-auth-token", a)
				  .asString();
		String body=response.getBody();
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
		//System.out.println(bodyout);
		handin(a,bodyout);
	}
	
	public void handin(String a,String b) throws UnirestException
	{
		HttpResponse<String> response = Unirest.post("https://api.shisanshui.rtxux.xyz/game/submit")
				  .header("content-type", "application/json")
				  .header("x-auth-token", a)
				  .body(b)
				  .asString();
		System.out.println(response.getBody());
	}
}
