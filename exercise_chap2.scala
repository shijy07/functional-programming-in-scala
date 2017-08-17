object Solution{
	def fib(n: Int): Int = {
		def go(n: Int): Int = {
			if (n == 0 || n == 1) n
			else go(n-1) + go(n-2)
		}

        go(n)
	}

	private def formatResults(name: String, n: Int, f: Int => Int) = {
		val msg = "The %dth %s is %d."
		msg.format(n, name, f(n))
	}
	def main(args: Array[String]): Unit = {
        //2.1
		println(formatResults("Fibonacci number", 6, fib))
	}
}