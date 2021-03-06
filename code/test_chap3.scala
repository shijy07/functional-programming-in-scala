
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

    def foldRightViaFoldLeft[A,B](as: List[A], z:B)(f: (A,B) => B): B = 
        foldLeft(reverse(as), z)((b,a) => f(a,b))
    
    def foldLeftViaFoldRight[A,B] (as: List[A], z:B)(f: (B,A) => B): B = 
        foldRight(as, (b:B) => b)((a,g) => b => g(f(b,a)))(z)
    
    def append[A](a1: List[A], a2: List[A]): List[A] = a1 match {
        case Nil => a2
        case Cons(h,t) => Cons(h, append(t, a2))
    }

    def appendViaFoldRight[A](l: List[A], r: List[A]): List[A] =
        foldRight(l, r)(Cons(_,_)) 
    
    def concat[A](l: List[List[A]]): List[A] =
        foldRight(l, Nil:List[A])(append)

    def add1(l: List[Int]): List[Int] =
        foldRight(l, Nil:List[Int])((h,t) => Cons(h+1,t))

    def doubleToString(l: List[Double]): List[String] =
        foldRight(l, Nil:List[String])((h,t) => Cons(h.toString,t))

    def map[A,B](as: List[A])(f: A => B): List[B] =
        foldRight(as, Nil:List[B])((h,t) => Cons(f(h), t))
 
    def filter[A](as: List[A])(f: A => Boolean): List[A] =
        foldRight(as, Nil:List[A])((h,t) => if (f(h)) Cons(h,t) else t)

    def flatMap[A,B](as: List[A])(f: A => List[B]): List[B] = 
        concat(map(as)(f))

    def filterViaFlatMap[A](as:List[A])(f: A => Boolean): List[A] =
        flatMap(as)(a => if(f(a)) List(a) else Nil)
    
    def parewiseAdding(a: List[Int], b: List[Int]): List[Int] = (a,b) match{
        case (Nil,_) => Nil
        case (_, Nil) => Nil
        case (Cons(h1,t1), Cons(h2,t2)) => Cons(h1+h2, parewiseAdding(t1,t2))
    }
 
    def zipWith[A](a: List[A], b: List[A])(f:(A,A) => A): List[A] = (a,b) match{
        case (Nil,_) => Nil
        case (_, Nil) => Nil
        case (Cons(h1,t1), Cons(h2,t2)) => Cons(f(h1,h2), zipWith(t1,t2)(f)) 
    }  

    @annotation.tailrec
    def startsWith[A](l: List[A], prefix: List[A]): Boolean = (l,prefix) match {
        case (_,Nil) => true
        case (Cons(h,t),Cons(h2,t2)) if h == h2 => startsWith(t, t2)
        case _ => false
    }
    
    @annotation.tailrec
    def hasSubsequence[A](sup: List[A], sub: List[A]): Boolean = sup match {
        case Nil => sub == Nil
        case _ if startsWith(sup, sub) => true
        case Cons(h,t) => hasSubsequence(t, sub)
    }  
}

sealed trait Tree[+A]

case class Leaf[A](value: A) extends Tree[A]
case class Branch[A](left: Tree[A], right: Tree[A]) extends Tree[A]

object Tree{

    //3.25
    def size[A](t: Tree[A]): Int = t match{
        case Leaf(_) => 1
        case Branch(l, r) => 1 + size(l) + size(r)
    }

    //3.26
    def maximum(t: Tree(Int)): Int = t match{
        case Leaf(n) => n
        case Branch(l, r) => maximum(l) max maximum(r)
    }

    //3.27
    def depth[A](t: Tree[A]): Int = t match{
        case Leaf(_) => 1
        case Branch(l, r) => 1 + (depth(l) max depth(r))
    }

    //3.28
    def map[A](t: Tree[A])(f: A => B): Tree[B] = t match{
        case Leaf(a) => Leaf(f(a))
        case Branch(l, r) => Branch(map(l)(f), map(r)(f))
    } 


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

        //3.16

        val a1 = List.add1(l)
        println("Exercise 3.16")
        println("Expected: \nCons(2,Cons(3,Cons(4,Cons(5,Cons(6,Nil)))))")
        println("Actual:")
        println(a1)

        //3.22
        val pa = List.parewiseAdding(l, r)
        println("Exercise 3.16")
        println("Expected: \nCons(6,Cons(6,Cons(6,Cons(6,Cons(6,Nil)))))")
        println("Actual:")
        println(pa)


    }
}