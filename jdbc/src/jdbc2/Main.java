package jdbc2;

public class Main {

	public static void main(String[] args) {
		for (int i = 0; i < 200; i++) {

			Runnable runA = new A();
			Runnable runB = new B();

			Thread threadA = new Thread(runA);
			Thread threadB = new Thread(runB);
			threadB.start();
			threadA.start();
			if (threadA.isAlive()) {
				((A) runA).dzialam();
				((B) runB).czekam();
			}

			if (threadB.isAlive()) {
				((B) runB).dzialam();
				((A) runA).czekam();
			}

			System.out.println("\n" + i + " ------------------------------\n");
		}
	}
}

class A implements Runnable {

	@Override
	public void run() {
	}

	public void dzialam() {
		System.out.println("Robie sie - Watek A");
	}

	public void czekam() {
		System.out.println("Czekam - Watek A");
	}
}

class B implements Runnable {

	@Override
	public void run() {

	}

	public void dzialam() {
		System.out.println("Robie sie - Watek B");
	}

	public void czekam() {
		System.out.println("Czekam - Watek B");
	}
}
