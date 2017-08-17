object TestChap2{
	import Solution._
	def main(args: Array[String]): Unit = {
        //2.1
		println("Test Fibonacci")
		println("Expected: 0, 1, 1, 2, 3, 5, 8")
        println("Actual:   %d, %d, %d, %d, %d, %d, %d".format(fib(0), fib(1), fib(2), fib(3), fib(4), fib(5), fib(6)))
        //2.2
        val array1 = Array(1,2,3)
        val array2 = Array(2,1,3)
        val array3 = Array(2.1,1.5,3.2)
        println("Test isSorted()")
		println("Expected: true, false,false")
        println("Actual: %s, %s, %s".format(isSorted(array1, (x: Int, y: Int) => (x<=y)), 
        	isSorted(array2, (x: Int, y: Int) => (x <= y)), 
        	isSorted(array3, (x: Double, y: Double) => (x <= y))))
     
	
	}
}