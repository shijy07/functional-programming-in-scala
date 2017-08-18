
sealed trait List[+A]
case object Nil extends List[Nothing]
case class Cons[+A] (head: A, tail: List[A]) extends List[A]

object List { // `List` companion object. Contains functions for creating and working with lists.
    def sum(ints: List[Int]): Int = ints match { // A function that uses pattern matching to add up a list of integers
        case Nil => 0 // The sum of the empty list is 0.
        case Cons(x,xs) => x + sum(xs) // The sum of a list starting with `x` is `x` plus the sum of the rest of the list.
    }

    def product(ds: List[Double]): Double = ds match {
        case Nil => 1.0
        case Cons(0.0, _) => 0.0
        case Cons(x,xs) => x * product(xs)
    }
    def apply[A](as: A*): List[A] = // Variadic function syntax
        if (as.isEmpty) Nil
        else Cons(as.head, apply(as.tail: _*))

    def tail[A](list: List[A]): List[A] = list match {
        case Nil => sys.error("cannot get tail of an empty list")
        case Cons(_, xs) => xs
    }

    def setHead[A](nv: A, ls: List[A]): List[A] = ls match {
        case Nil => sys.error("cannt set head on an empty list")
        case Cons(_, xs) => Cons(nv,xs)
    }

    def drop[A](l: List[A], n: Int): List[A] = {
        if (n <= 0) l
        else l match {
            case Nil => Nil
            case Cons(_,xs) => drop(xs, n-1)

        }
    }

    def dropWhile[A](l: List[A])(f: A => Boolean): List[A] = l match {
        case Nil => Nil
        case Cons(h,t) if f(h) => dropWhile(t)(f)
        case _ => l
    }
    
    def init[A](l: List[A]): List[A] = l match {
        case Nil => sys.error("init of empty list")
        case Cons(_,Nil) => Nil
        case Cons(h,t) => Cons(h,init(t))
    }

    def foldRight[A,B](as: List[A], z: B)(f: (A, B) => B): B = as match {
        case Nil => z
        case Cons(x, xs) => f(x, foldRight(xs, z)(f))
    }

    def length[A] (as: List[A]): Int = {
        foldRight(as, 0)((_,acc) => acc + 1)
    }

    def foldLeft[A,B](as: List[A], z:B) (f: (B,A) => B): B = as match {
        case Nil => z
        case Cons(h,t) => foldLeft(t, f(z,h))(f)

    }

    def sum2(l: List[Int]) = foldLeft(l, 0)(_+_)

    def product2(l:List[Double]) = foldLeft(l, 1.0)(_*_)

    def reverse[A](l: List[A]) = foldLeft(l, List[A]()) ((acc,h) => Cons(h, acc))
}

object TestChap3{
	//import SolutionChap3._
    def main(args: Array[String]): Unit = {
        //3.1
        val x = List(1,2,3,4,5) match {
            case Cons(x, Cons(2, Cons(4,_))) => x
            case Nil => 42
            case Cons(x, Cons(y, Cons(3, Cons(4, _)))) => x + y
            case Cons(h, t) => h + List.sum(t)
            case _ => 101
        }
	println("Exercise 3.1")
	println("Expected: 3")
        println("Actual: %d".format(x))
        
        //3.2
        val l = List(1,2,3,4,5)
        val y = List.tail(l)

        println("Exercise 3.2")
        println("Expected: \nCons(2,Cons(3,Cons(4,Cons(5,Nil))))")
        println("Actual:")
        println(y)
        
        //3.3
        val z = List.setHead(6,l)
        println("Exercise 3.3")
        println("Expected: \nCons(6,Cons(2,Cons(3,Cons(4,Cons(5,Nil)))))")
        println("Actual:")
        println(z)
	
        //3.4
        val m = List.drop(l,3)
        println("Exercise 3.4")
        println("Expected: \nCons(4,Cons(5,Nil))")
        println("Actual:")
        println(m)

        //3.5

        val n = List.dropWhile(l)(a => (a <= 3))
        println("Exercise 3.5")
        println("Expected: \nCons(4,Cons(5,Nil))")
        println("Actual:")
        println(n)

        //3.6
        val p = List.init(l)
        println("Exercise 3.6")
        println("Expected: \nCons(1,Cons(2,Cons(3,Cons(4,Nil))))")
        println("Actual:")
        println(p)

        //3.9
        val len = List.length(l)
        println("Exercise 3.9")
        println("Expected: 5")
        println("Actual: %d"format(len))

        //3.12

        val r = List.reverse(l)
        println("Exercise 3.12")
        println("Expected: \nCons(5,Cons(4,Cons(3,Cons(2,Cons(1,Nil)))))")
        println("Actual:")
        println(r)


    }
}