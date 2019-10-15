package dsada;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class pai
{
	private String card;
	private char color;
	private String value;
	public pai(String s)
	{
		card=s;
		setcv();
	}
	private void setcv()
	{
		color=card.charAt(0);
		value=card.substring(1);
	}
	public String getcard()
	{
		return card;
	}
	public char getcolor()
	{
		return color;
	}
	public String getvalue()
	{
		return value;
	}
}
class Pocker
{
	private Vector<pai> v=new Vector<pai>();
	public Pocker(String s)
	{
		Pattern r=Pattern.compile("[$&*#]{1}[0-9AJQK]{1,2}");
		Matcher m=r.matcher(s);
		while(m.find())
		{
			pai p=new pai(m.group());
			v.add(p);
		}
	}
	public void getpai()
	{
		Enumeration<pai> element=v.elements();
		while(element.hasMoreElements())
		{
			element.nextElement().getcard();
		}
	}
	public int judgespecial()
	{
		int x=0;
		if(judgezhizun()) x=13;
		else if(judgeyitiaolong()) x=12;
		else if(judgetwelvehuang()) x=11;
		else if(judgesantonghuashun()) x=10;
		else if(judgesanfentianxia()) x=9;
		else if(judgeallbig()) x=8;
		else if(judgeallsmall()) x=7;
		else if(judgeshuangguai()) x=6;
		else if(judgesitao()) x=5;
		else if(judgewuduisantiao()) x=4;
		else if(judgesixdui()) x=3;
		else if(judgesanshun()) x=2;
		else if(judgesantonghua()) x=1;
		return x;
	}
	private boolean judgezhizun()
	{
		boolean y=true;int i=1;char x = 0;
		Enumeration<pai> element=v.elements();
		while(element.hasMoreElements())
		{
			if(i==1) x=element.nextElement().getcolor();
			else
			{
				if(x!=element.nextElement().getcolor()) 
				{
					y=false;
					break;
				}
			}
			i++;
		}
		return y;
	}
	private boolean judgeyitiaolong()
	{
		boolean y=true;String value;Integer i=2;
		Vector<Integer> num=new Vector<Integer>();
		Enumeration<pai> element=v.elements();
		while(element.hasMoreElements())
		{
			value=element.nextElement().getvalue();
			if(value.equals("A")) num.add(14);
			else if(value.equals("J")) num.add(11);
			else if(value.equals("Q")) num.add(12);
			else if(value.equals("K")) num.add(13);
			else
			{
				num.add(Integer.parseInt(value));
			}
		}
		Collections.sort(num);
		Enumeration<Integer> element2=num.elements();
		while(element2.hasMoreElements())
		{
			if(element2.nextElement()!=i) 
			{
				y=false;
				break;
			}
			i++;
		}
		return y;
	}
	private boolean judgetwelvehuang()
	{
		boolean y=true;String s;
		Enumeration<pai> element=v.elements();
		while(element.hasMoreElements())
		{
			s=element.nextElement().getvalue();
			if(!(s.equals("A")||s.equals("J")||s.equals("Q")||s.equals("K"))) 
			{
				y=false;
				break;
			}
		}
		return y;
	}
	class MyComparator implements Comparator<pai>
	{
		public int compare(pai A,pai B)
		{
			Integer a=0;Integer b=0;
			if(A.getvalue().equals("A")) a=14;
			else if(A.getvalue().equals("K")) a=13;
			else if(A.getvalue().equals("Q")) a=12;
			else if(A.getvalue().equals("J")) a=11;
			else a=Integer.parseInt(A.getvalue());
			if(B.getvalue().equals("A")) b=14;
			else if(B.getvalue().equals("K")) b=13;
			else if(B.getvalue().equals("Q")) b=12;
			else if(B.getvalue().equals("J")) b=11;
			else b=Integer.parseInt(B.getvalue());
			if(a>b) return 1;
			else if(a<b) return -1;
			else 
			{
				if(A.getcolor()=='$') return -1;
				else if(A.getcolor()=='&')
				{
					if(B.getcolor()=='$') return 1;
					else return -1;
				}
				else if(A.getcolor()=='*')
				{
					if(B.getcolor()=='#') return -1;
					else return 1;
				}
				else return 1;
			}
		}
	}
	private Integer change(String s)
	{
		Integer x=0;
		if(s.equals("A")) x=14;
		else if(s.equals("K")) x=13;
		else if(s.equals("Q")) x=12;
		else if(s.equals("J")) x=11;
		else x=Integer.parseInt(s);
		return x;
	}
	private boolean judgesantonghuashun()
	{
		char lastcolor = 0;Integer lastvalue = null;
		Comparator<pai> ct=new MyComparator();
		Collections.sort(v,ct);
		Vector<pai> vv=new Vector<pai>();
		Vector<pai> zu=new Vector<pai>();
		Enumeration<pai> element=v.elements();
		while(element.hasMoreElements())
		{
			vv.add(element.nextElement());
		}
		int sig5=0,sig3=0;
		element=vv.elements();
		for(int i=1;i<=3;i++)
		{		
			pai a;
			Integer x;
			char y;
			a=element.nextElement();
			zu.add(a);
			lastvalue=change(a.getvalue());
			lastcolor=a.getcolor();
			vv.remove(0);
			element=vv.elements();
		int j=0;	while(element.hasMoreElements()&&zu.size()!=5)
					{
						a=element.nextElement();
						x=change(a.getvalue());
						y=a.getcolor();
						if(x>lastvalue+1) 
						{
							j=0;
							element=vv.elements();
							break;
						}
						else
						{
							if(y==lastcolor)
							{
								zu.add(a);
								vv.remove(j);
								lastvalue=x;
								j=0;
								element=vv.elements();
							}
							else 
							{
								j++;
							}
						}
					}
					if(zu.size()==5) sig5++;
					else if(zu.size()==3) sig3++;
					else return false;
					zu.removeAllElements();
		}
		if(sig5==2&&sig3==1) return true;
		else return false;
	}
	private boolean judgesanfentianxia()
	{
		int []num=new int [15];Integer x=null;int boom = 0;
		for(int i=0;i<=14;i++) num[i]=0;
		Enumeration<pai> element=v.elements();
		while(element.hasMoreElements())
		{
			x=change(element.nextElement().getvalue());
			num[x]++;
		}
		for(int i=2;i<=14;i++)
		{
			if(num[i]==4) boom++;
		}
		if(boom==3) return true;
		else return false;
	}
	private boolean judgeallbig()
	{
		Enumeration<pai> element=v.elements();
		Integer x=null;
		while(element.hasMoreElements())
		{
			x=change(element.nextElement().getvalue());
			if(x<8) return false; 
		}
		return true;
	}
	private boolean judgeallsmall()
	{
		Enumeration<pai> element=v.elements();
		Integer x=null;
		while(element.hasMoreElements())
		{
			x=change(element.nextElement().getvalue());
			if(x>8) return false; 
		}
		return true;
	}
	private boolean judgeshuangguai()
	{
		int []num=new int [15];Integer x=null;int dui=0,santiao=0;
		for(int i=0;i<=14;i++) num[i]=0;
		Enumeration<pai> element=v.elements();
		while(element.hasMoreElements())
		{
			x=change(element.nextElement().getvalue());
			num[x]++;
		}
		for(int i=2;i<=14;i++)
		{
			if(num[i]==3) santiao++;
			else if(num[i]==2) dui++;
		}
		if(santiao==2&&dui==3) return true;
		else return false;
	}
	private boolean judgesitao()
	{
		int []num=new int [15];Integer x=null;int santiao=0;
		for(int i=0;i<=14;i++) num[i]=0;
		Enumeration<pai> element=v.elements();
		while(element.hasMoreElements())
		{
			x=change(element.nextElement().getvalue());
			num[x]++;
		}
		for(int i=2;i<=14;i++)
		{
			if(num[i]==3) santiao++;
		}
		if(santiao==4) return true;
		else return false;
	}
	private boolean judgewuduisantiao()
	{
		int []num=new int [15];Integer x=null;int dui=0,santiao=0;
		for(int i=0;i<=14;i++) num[i]=0;
		Enumeration<pai> element=v.elements();
		while(element.hasMoreElements())
		{
			x=change(element.nextElement().getvalue());
			num[x]++;
		}
		for(int i=2;i<=14;i++)
		{
			if(num[i]==3) santiao++;
			if(num[i]==2) dui++;
		}
		if(santiao==1&&dui==5) return true;
		else return false;
	}
	private boolean judgesixdui()
	{
		int []num=new int [15];Integer x=null;int dui=0;
		for(int i=0;i<=14;i++) num[i]=0;
		Enumeration<pai> element=v.elements();
		while(element.hasMoreElements())
		{
			x=change(element.nextElement().getvalue());
			num[x]++;
		}
		for(int i=2;i<=14;i++)
		{
			if(num[i]==2) dui++;
		}
		if(dui==6) return true;
		else return false;
	}
	private boolean judgesanshun()
	{
		int []num=new int [15];Integer x=null;
		for(int i=0;i<=14;i++) num[i]=0;
		Enumeration<pai> element=v.elements();
		while(element.hasMoreElements())
		{
			pai i=element.nextElement();
			x=change(i.getvalue());
			num[x]++;
		}
		int sig5=0,sig3=0;
		for(int i=1;i<=3;i++)
		{
			int size=0;int start=0;
			for(int stop=0,j=2;j<=14;j++)
			{
				if(num[j]==1&&stop==0)
				{
					stop=1;
					start=j;
				}
				else if(num[j]>=2)
				{
					start=j;
					break;
				}
			}
			num[start]--;
			size++;
			for(int ii=start+1;ii<=14&&size!=5;ii++)
			{
				if(num[ii]==0) break;
				else 
				{
					size++;
					num[ii]--;
				}
			}
			if(size==5) sig5++;
			else if(size==3) sig3++;
			else return false;
		}
		if(sig5==2&&sig3==1) return true;
		else return false;
	}
	private boolean judgesantonghua()
	{
		int[] num= {0,0,0,0};int sig3=0,sig5=0,sig8=0,sig10=0;
		Enumeration<pai> element=v.elements();
		while(element.hasMoreElements())
		{
			char a=element.nextElement().getcolor();
			if(a=='$') num[0]++;
			else if(a=='&') num[1]++;
			else if(a=='*') num[2]++;
			else if(a=='#') num[3]++;
		}
		if(num[0]!=0&&num[1]!=0&&num[2]!=0&&num[3]!=0) return false;
		else
		{
			for(int i=0;i<4;i++)
			{
				if(num[i]==3) sig3++;
				else if(num[i]==5) sig5++;
				else if(num[i]==8) sig8++;
				else if(num[i]==10) sig10++;
			}
			if(sig3==1&&sig5==2) return true;
			else if(sig5==1&&sig8==1) return true;
			else if(sig3==1&&sig10==1) return true;
			else return false;
		}
	}
	
	private Vector<pai> v1=new Vector<pai>();
	private Vector<pai> v2= new Vector<pai>();
	private Vector<pai> v3=new Vector<pai>();
	public void judgenormal()
	{
		Enumeration<pai> element=v.elements();
		Vector<pai> vv=new Vector<pai>();
		while(element.hasMoreElements())
		{
			vv.add(element.nextElement());
		}
		getzuhe(vv,13,1);
		
	}
	private void getzuhe(Vector<pai> vv,int x,int start)
	{
		if(x>8)
		{
				for(int i=start;i<=vv.size();i++)
				{
					if(vv.size()-i+1<x-8) return;
					Enumeration<pai> element=vv.elements();
					element=vv.elements();
					for(int j=1;j<i;j++)
						element.nextElement();
					pai a=element.nextElement();
					v1.add(a);
					if(x==9)
					{
						Vector<pai> vx=new Vector<pai>();
						element=vv.elements();
						while(element.hasMoreElements()) vx.add(element.nextElement());
						element=v1.elements();
						while(element.hasMoreElements()) vx.remove(element.nextElement());
						getzuhe(vx,x-1,1);
					}
					else getzuhe(vv,x-1,i+1);
					v1.remove(v1.size()-1);
				}
			
		}
		else if(x>3)
		{
			for(int i=start;i<=vv.size();i++)
			{
				if(vv.size()-i+1<x-3) return;
				Enumeration<pai> element=vv.elements();
				element=vv.elements();
				for(int j=1;j<i;j++)
					element.nextElement();
				pai a=element.nextElement();
				v2.add(a);
				if(x==4)
				{
					Vector<pai> vx=new Vector<pai>();
					element=vv.elements();
					while(element.hasMoreElements()) vx.add(element.nextElement());
					element=v2.elements();
					while(element.hasMoreElements()) vx.remove(element.nextElement());
					getzuhe(vx,x-1,1);
				}
				else getzuhe(vv,x-1,i+1);
				v2.remove(v2.size()-1);
			}
		}
		else
		{
			Enumeration<pai> element=vv.elements();
				while(element.hasMoreElements())
					v3.add(element.nextElement());
				//
				double fen1,fen2,fen3;
				Vector<pai> nowv=new Vector<pai>();
				element=v1.elements();
				while(element.hasMoreElements())
					nowv.add(element.nextElement());
				Comparator<pai> ct=new MyComparator();
				Collections.sort(nowv,ct);
				fen1=getscore(nowv);
				nowv.removeAllElements();
				element=v2.elements();
				while(element.hasMoreElements())
					nowv.add(element.nextElement());
				ct=new MyComparator();
				Collections.sort(nowv,ct);
				fen2=getscore(nowv);
				if(fen2>fen1) 
				{
					v3.removeAllElements();
					return;
				}
				nowv.removeAllElements();
				element=v3.elements();
				while(element.hasMoreElements())
					nowv.add(element.nextElement());
				ct=new MyComparator();
				Collections.sort(nowv,ct);
				fen3=getscore(nowv);
				if(fen3>fen2) 
				{
					v3.removeAllElements();
					return;
				}
				
				//到这里fen1>fen2>fen3
				if(fen1+fen2+fen3>allscore)
				{
					allscore=fen1+fen2+fen3;
					getv1.removeAllElements();
					getv2.removeAllElements();
					getv3.removeAllElements();
					element=v1.elements();
					while(element.hasMoreElements())
						getv1.add(element.nextElement());
					element=v2.elements();
					while(element.hasMoreElements())
						getv2.add(element.nextElement());
					element=v3.elements();
					while(element.hasMoreElements())
						getv3.add(element.nextElement());
					
				}
				//
				v3.removeAllElements();
		}
	}
	
	private Vector<pai> getv1=new Vector<pai>();
	private Vector<pai> getv2=new Vector<pai>();
	private Vector<pai> getv3=new Vector<pai>();
	private double allscore=0;
	
	
	//判断普通牌前新建Vector对象并进行排列 
	private double getscore(Vector<pai> vv)
	{
		double score=0;
		if(vv.size()==3)
		{
			score=judgetonghua(vv);
			if(score!=0) return score;
			score=judgeshunzi(vv);
			if(score!=0) return score;
			score=judgesantiao(vv);
			if(score!=0) return score;
			score=judgeliandui(vv);
			if(score!=0) return score;
			score=judgeshuangdui(vv);
			if(score!=0) return score;
			score=judgedandui(vv);
			if(score!=0) return score;
			score=judgesan(vv);
			return score;
		}
		else
		{
			score=judgetonghuashun(vv);
			if(score!=0) return score;
			score=judgeboom(vv);
			if(score!=0) return score;
			score=judgehulu(vv);
			if(score!=0) return score;
			score=judgetonghua(vv);
			if(score!=0) return score;
			score=judgeshunzi(vv);
			if(score!=0) return score;
			score=judgesantiao(vv);
			if(score!=0) return score;
			score=judgeliandui(vv);
			if(score!=0) return score;
			score=judgeshuangdui(vv);
			if(score!=0) return score;
			score=judgedandui(vv);
			if(score!=0) return score;
			score=judgesan(vv);
			return score;
		}
			
	}
	private int judgetonghuashun(Vector<pai> vv)
	{
		int score=100000;
		if(vv.size()==3) return 0;
		Integer lastvalue=null;char lastcolor=0;int j=0;
		Integer x=null;char y=0;
		Enumeration<pai> element=vv.elements();
		while(element.hasMoreElements())
		{
			if(j==0)
			{
				pai a=element.nextElement();
				lastvalue=change(a.getvalue());
				lastcolor=a.getcolor();
				j=1;
			}
			else
			{
				pai a=element.nextElement();
				x=change(a.getvalue());
				y=a.getcolor();
				if(x!=lastvalue+1) return 0;
				else
				{
					if(y!=lastcolor) return 0;
				}
				lastvalue=x;
			}
		}
		return score+lastvalue;
	}
	private int judgeboom(Vector<pai> vv)
	{
		int score=90000;
		if(vv.size()==3) return 0;
		int []num=new int [20];Integer x=null;
		for(int i=1;i<=14;i++) num[i]=0;
		Enumeration<pai> element=vv.elements();
		while(element.hasMoreElements())
		{
			x=change(element.nextElement().getvalue());
			num[x]++;
			if(num[x]==4) return score+x;
		}
		return 0;
	}
	private int judgehulu(Vector<pai> vv)
	{
		int score=80000;
		if(vv.size()==3) return 0;Integer santiao=0,dui=0;
		int []num=new int [20];Integer x=null;
		for(int i=0;i<=14;i++) num[i]=0;
		Enumeration<pai> element=vv.elements();
		while(element.hasMoreElements())
		{
			x=change(element.nextElement().getvalue());
			num[x]++;
		}
		for(int i=0;i<=14;i++)
		{
			if(num[i]==3) santiao=i;
			if(num[i]==2) dui=i;
		}
		if(santiao!=0&&dui!=0&&santiao!=dui) 
		{
			score=score+santiao*100+dui;
			return score;
		}
		else return 0;
	}
	private int judgetonghua(Vector<pai> vv)
	{
		int score=70000;
		Integer lastvalue = null;char lastcolor=0;int j=0;
		Enumeration<pai> element=vv.elements();
		while(element.hasMoreElements())
		{
			if(j==0)
			{
				lastcolor=element.nextElement().getcolor();
				j=1;
			}
			else
			{
				pai a=element.nextElement();
				lastvalue=change(a.getvalue());
				if(a.getcolor()!=lastcolor) return 0;
			}
		}
		return score+lastvalue;
	}
	private int judgeshunzi(Vector<pai> vv)
	{
		int score=60000;
		Integer lastvalue=null;int j=0;
		Enumeration<pai> element=vv.elements();
		while(element.hasMoreElements())
		{
			if(j==0)
			{
				lastvalue=change(element.nextElement().getvalue());
				j=1;
			}
			else
			{
				Integer x=change(element.nextElement().getvalue());
				if(x!=lastvalue+1) return 0;
				else lastvalue=x;
			}
		}
		return score+lastvalue;
	}
	private int judgesantiao(Vector<pai> vv)
	{
		int score=50000;
		Integer santiao=0;
		int []num=new int [20];Integer x=null;
		for(int i=0;i<=14;i++) num[i]=0;
		Enumeration<pai> element=vv.elements();
		while(element.hasMoreElements())
		{
			x=change(element.nextElement().getvalue());
			num[x]++;
			if(num[x]==3) santiao=x;
		}
		if(santiao!=0)
			return score+santiao;
		else return 0;
	}
	private int judgeliandui(Vector<pai> vv)
	{
		int score=40000;
		int count=0;int dui1=0,dui2=0;
		int []num=new int [20];Integer x=null;
		for(int i=0;i<=14;i++) num[i]=0;
		Enumeration<pai> element=vv.elements();
		while(element.hasMoreElements())
		{
			x=change(element.nextElement().getvalue());
			num[x]++;
			if(num[x]==2) 
			{
				if(dui1==0) dui1=x;
				else dui2=x;
				count++;
			}
		}
		if(dui1>dui2)
		{
			int xx=dui1;dui1=dui2;dui2=xx;
		}
		if(count==2&&dui2-dui1==1) return score+dui2;
		else return 0;
	}
	private int judgeshuangdui(Vector<pai> vv)
	{
		int score=30000;
		int count=0;int dui1=0,dui2=0;
		int []num=new int [20];Integer x=null;
		for(int i=0;i<=14;i++) num[i]=0;
		Enumeration<pai> element=vv.elements();
		while(element.hasMoreElements())
		{
			x=change(element.nextElement().getvalue());
			num[x]++;
			if(num[x]==2) 
			{
				if(dui1==0) dui1=x;
				else dui2=x;
				count++;
			}
		}
		if(dui1>dui2)
		{
			int xx=dui1;dui1=dui2;dui2=xx;
		}
		if(count==2&&dui2-dui1!=1) return score+dui2;
		else return 0;
	}
	private int judgedandui(Vector<pai> vv)
	{
		int score=20000;
		int count=0;int dui=0;
		int []num=new int [20];Integer x=null;
		for(int i=0;i<=14;i++) num[i]=0;
		Enumeration<pai> element=vv.elements();
		while(element.hasMoreElements())
		{
			x=change(element.nextElement().getvalue());
			num[x]++;
			if(num[x]==2) 
			{
				dui=x;
				count++;
			}
			
		}
		if(count==1) return score+dui;
			else return 0;
	}
	private double judgesan(Vector<pai> vv)
	{
		Enumeration<pai> element=vv.elements();
		double score=0;int[] num= {0,0,0,0,0};int count=0;
		while(element.hasMoreElements())
		{
			Integer x=change(element.nextElement().getvalue());
			num[count]=x;
			count++;
		}
		for(int i=count-1;i>=0;i--)
			score=(num[i]+score)*100;
		while(score>=10000) score=score/10;
		return score;
	}

	public String getresult()
	{
		String card=new String();
		Enumeration<pai> element=getv3.elements();
		while(element.hasMoreElements())
		{
			card=card+element.nextElement().getcard();
			if(element.hasMoreElements()) card=card+" ";
			else card=card+"\",\"";
		}
		element=getv2.elements();
		while(element.hasMoreElements())
		{
			card=card+element.nextElement().getcard();
			if(element.hasMoreElements()) card=card+" ";
			else card=card+"\",\"";
		}
		element=getv1.elements();
		while(element.hasMoreElements())
		{
			card=card+element.nextElement().getcard();
			if(element.hasMoreElements()) card=card+" ";
		}
		return card;
	}
	
}

public class thirteenwater {
	public String workcard(String s)
	{
		String card=new String();
		Pocker p=new Pocker(s);
		if(p.judgespecial()>0) 
		{
			System.out.println("------"+s);
			return s;
		}
		else
		{
			p.judgenormal();
			card=p.getresult();
		}
	return card;
	}
}
