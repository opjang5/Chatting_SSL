package chatting.data;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//Group x = new Group("255.255.255.0","G00","%tmw,123%www,222%wdww,12333");
		MyMsg m = new MyMsg();
		m.setMsg("Hello,");
		m.setMsg("nice");
		m.setMsg("to");
		m.setMsg("meet");
		m.setMsg("you.");
		String x = null;
		for(;;){
			x = m.getMsg();
			if(x==null)break;
			System.out.println(x);
		}
		
	}

}
