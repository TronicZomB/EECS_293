package sandbox;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class sandbox {
	sandbox() {
		Queue<Integer> q = new ArrayBlockingQueue<Integer>(3);
		
		q.add(1);
		q.add(2);
		q.add(3);
		q.add(4);
		
		System.out.println(q);
	}
}
