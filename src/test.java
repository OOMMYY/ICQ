import java.util.LinkedList;
import java.util.List;

class test{
	public static void main(String[] args) {
		List<String> li = new LinkedList<String>();
		for(int i=0;i<5;i++){
			li.add(null);
		}
		System.out.println(li.size());
		System.out.println(li.get(3));
	}
}