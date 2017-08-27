### Functional data structure

It is only operated on using only pure functions. *Functional data structures are not immutable*. 

* In object-oriented, an <br>immutable</br> object is an object whose state cannot be modified after it is created. 

For example, if we concatenate two lists `a` and `b` using `a ++ b`, we create a new list and leaves the two inputs unmodified. 

#### Defining functional data structures

In scala, we introduce a data type with `trait` keyword. A `trait` is an abstract interface that may optionally contain implementations of some methods.

`sealed` is added in front to indicate that all implementations of the `trait` must be declared in this file.

##### Side notes on variances in Scala:

```scala
class Foo[+A] // A covariant class 
class Bar[-A] // A contravariant class 
class Baz[A] // An invariant class
```
A type parameter `A` of a generic class can be made covariant by using the annotation `+A`. If `X` is a subtype of `Y`, and we have `List[+A]` , then, `List[X]` is a subtype of `List[Y]`.

A type parameter `A` of a generic class can be made controvariant by using the annotation `-A`. This create a subtyping relationship between the calss and its type parameter that is similar. 

`Nothing` is a subtype of all types.

#### Pattern matching

Pattern matching works similar as `switch` statement. It's introduced with an expression, followed by keyword `match` and a `{}`-wrapped sequence of `cases`. Each case in the match consist of a pattern to the left of `=>` and a result to the right of `=>`. If the target matches the pattern in a case, the result of that case becomes the result of the entire match expression. If multiple patterns match the target, Scala choose the first matching case.

* We often define a *companion object* in addition to our data type and its data constructors. It is just an `object` with the same name as the datatype where we put various convenience functions for creating or working with values of the datatype.

`_` matches any expression.

`List(1,2,3) match { case _ => 42}` results in 42.

`List(1,2,3) match { case Cons(h,_) => h}` results in 1.

`List(1,2,3) match { case Cons(_,h) => h}` results in `List(2,3)`.

##### Variadic functions

For data types, it's a common idiom to have a variadic `apply` methd in the companion object to conveniently construct instances of the data type.

For example:

```scala
def apply[A](as: A*): List[A] = // Variadic function syntax
    if (as.isEmpty) Nil
    else Cons(as.head, apply(as.tail: _*))
```
By having such variadic functions, we can invoke it with syntax like `List(1,2,3,4)` or `List("Hi", "Bye")`.

#### Data Sharing in functional data structures

Sharing of immutable data often lets us implement functions more efficiently. We can always return immutable dta structures without having to worry about subsequent code modifying our data. There is no need to pressimitically make copies to avoid modification or corruption.

### Trees

*Algebric data type* is a data type that is defined by one or more data constructors. Each data constructor may contain zero or more arguments. We say that the data type is the `sum` or `union` of its data constructors and each data constructor is the `product` of its arguments.


