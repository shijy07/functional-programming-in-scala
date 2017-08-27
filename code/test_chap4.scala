//hide std library `Option` and `Either`, since we are writing our own in this chapter
import scala.{Option => _, Either => _, _}

sealed trait Option[+A]
case class Some[+A](get A) extends Option[A]
case Object None extends Option[Nothing]

object Option {
    
    //4.1
    def map[B](f:A => B): Option[B] = this match  {
        case None => None
        case Some(a) => Some(f(a))
    }
    
    def flatMap[B](f: A => Option[B]): Option[B] = this match{
        case None => None
        case Some(a) => f(a)
    }
    
    def getOrElse[B >: A](default: => B):B = this match{
    	case None => None
    	case Some(a) => a
    }
    
    def orElse[B >: A](ob: => Option[B]): Option[B] = this match{
    	case None => None
    	case _ => this
    }
    
    def filter(f: A => Boolean): Option[A] = this match{
    	case Some(a) if f(a) => this
    	case _ => None
    }

    //4.2
    def mean(xs: Seq[Double]): Option[Double] =
        if (xs.isEmpty) None
        else Some(xs.sum / xs.length)
    
    def variance(xs: Seq[Double]): Option[Double] = {
        mean(xs).flatMap(m => mean(xs.map(x => math.pow(x-m, 2))))

   }

    //4.3
    def map2[A,B,C](a: Option[A], b:Option[B])(f: (A,B)=>C): Option[C] = {
       a flatMap (aa=> b map (bb => f(aa, bb)))
    }
    
    //4.4
    def sequence[A](a: List[Option[A]]): Option[List[A]] = a match{
        case Nil => Some(Nil)
        case h :: t => h flatMap(hh => sequence(t) map(hh :: _))
    }
    def traverse[A, B](a: List[A])(f: A => Option[B]): Option[List[B]] =
        a match {
            case Nil => Some(Nil)
            case h::t => map2(f(h), traverse(t)(f))(_ :: _)
    }
    //4.5
    def sequenceViaTraverse[A](a: List[Option[A]]): Option[List[A]] =
        traverse(a)(x => x)
    }

}

sealed trait Either[+E,+A] 
case class Left[+E](get: E) extends Either[E,Nothing]
case class Right[+A](get: A) extends Either[Nothing,A]


object Either{
    //4.6
    def map[B](f: A => B): Either[E, B] = this match {
        case Right(a) => Right(f(a))
        case Left(e) => Left(e)
    }
   
    def flatMap[EE >: E, B](f: A => Either[EE, B]): Either[EE, B] = this match {
        case Left(e) => Left(e)
        case Right(a) => f(a)
    }
   
    def orElse[EE >: E, AA >: A](b: => Either[EE, AA]): Either[EE, AA] = this match {
        case Left(_) => b
        case Right(a) => Right(a)
    }
   
    def map2[EE >: E, B, C](b: Either[EE, B])(f: (A, B) => C): 
        Either[EE, C] = for { a <- this; b1 <- b } yield f(a,b1)

   
    def mean(xs: IndexedSeq[Double]): Either[String, Double] = 
        if (xs.isEmpty) 
            Left("mean of empty list!")
        else 
            Right(xs.sum / xs.length)

    def safeDiv(x: Int, y: Int): Either[Exception, Int] = 
        try Right(x / y)
        catch { case e: Exception => Left(e) }

    def Try[A](a: => A): Either[Exception, A] =
        try Right(a)
        catch { case e: Exception => Left(e) }
    //4.6
    def traverse[E,A,B](es: List[A])(f: A => Either[E, B]): Either[E, List[B]] = es match {
        case Nil => Right(Nil)
        case h::t => (f(h) map2 traverse(t)(f))(_ :: _)
    }

    def sequence[E,A](es: List[Either[E, A]]): Either[E, List[A]] =
        traverse(es)(x=>x)
}