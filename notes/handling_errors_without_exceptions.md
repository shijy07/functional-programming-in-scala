### Handling errors without exceptions

We can represent failures and exceptions with ordinary values, and we can write higher-order functions that abstract out common patterns of error handling and recovery. The functional solution, of returning errors as values, is safer and retains referential transparency, and through the use of higher-order functions, we can preserve the primary venefit of exceptions - *consolidation of error-handling logic.*
 
##### Drawbacks of exceptions

* Exceptions break RT and introduce context dependence.

* Exceptions are not type safe.

##### Benefits of exceptions

* Allow us to consolidate and centralize error-handling logic

#### The `Option` data type

```scala
sealed trait Option[+A]
case class Some[+A](get A) extends Option[A]
case Object None extends Option[Nothing]

def mean(xs: Seq[Double]): Option[Double] ={
	if (xs.isEmpty) None
	else Some(xs.sum/xs.length)
}
```
In the above example, the return type now reflects the possibility that the result may not always be defined.  We still also always return a result of the declared type from our function. `mean` is a total funcion now. 

##### Basic functions in `Option` and their usage

The basic functions:
```scala
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

```

The `map` function can be used to transform the result inside an `Option`, if it exist. We can think of it as proceeding with a computation on the assumption that an error hasn't occured; it is also a way of deferring the error handling to later code.

`flatMap` is similar except that the function we provide to transform the result can itself fail. With `flatMap` we can construct a computation with multiple stages, any of which may fail, and the computation will abort as soon as the first failure is encountered, since `None.flatMap(f)` will immediately return `None` without running `f`.

We can use `filter` to convert successes into failures if the successful values don't match the given predicate. A common pattern is to transform an `Option` via calls to `map`, `flatMap`, and/or `filter`, and then use `getOrElse` to do error handling at the end.

`orElse` is similar to `getOrElse`, except that we return another `Option` if the first is undefined. This is often useful when we need to chain together possibly failing computations, trying the second if the first hasn't succeeded.

A common idiom is to do `o.getOrElse(throw new Exceptions("Fail"))` to convert the `None` case of an `Option` back to an exception. The general rule of thumb is that we use exceptions only if no resonable program would ever catch the exception; if for some callers the exception might be a recoverable error, we use `Option` to give them flexibility.

##### Option composition, lifting, and wrapping exception-oriented APIs

What `lift` trying to do is explicitly:

```scala
def lift[A,B](f: A => B): Option[A] => Option[B] = _ map f
```
Example of `lift` existing function to work with optional values:

```scala
val absO: Option[Double] => Option[Double] = lift(math.abs)
```
We do not need to rewrite the `math.abs` function to work with optional values, we just lifted it into `Option` context after the fact. We can do this for any function. 

#### The `Either` data type

One thing we notice with `Option` is that it doesn't tell us anything about what went wrong in the case of an exception condition. We can craft a data type that encodes whatever information we want about failures. 

```scala
sealed trait Either[+E, +A]
case class Left[+E](value: E) extends Either[E, Nothing]
case class Right[+A](value: A) extends Either[A, Nothing]
```

`Either` has only two cases and both cases carry a value. By convention, the `Right` constructor is reserved for the success case, and the `Left` is used for failure.g 

