## Scala Language
### object

The `object` keyword creates a new singleton type, which is similar to a `class` in
Java that only has a sinle name instance.

There is no `static` keyword in scala.

###  Define a method

```scala
def method_name(arg_name: Arg_Type): Output_Type = {
    /** process arg_name */
    output // return output of type Output_Type, no need to have "return"	
}

```

The `private` keyword means that it cannot be called from any code outside the object where the function is defined.

If the reurn type is not defined, Scala is usually able to infre the return types of the methods. However, it's generally considered good style to explicitly declare the return types of methods that you expect others to use.

### Main method

```scala
def main(args: Array[String]): Unit ={
	/** Call other functions*/
}
```

The `main` method is an outer shell that calls into our purely functional core.
Scala will look for a method named main with a specific signiture `main`. It has to take an `Array` of `String`s as its argument, and its return type must be `Unit` (i.e. `void` in Java).

In Scala, every method has to return some value as long as it does not crash or hang. 

### Run Scala

```shell
> scalac SOURCE_CODE.scala
```
This will generate `.class` file that contains complied code that can be run with JVM.

To excute the code, we can use `scala` command-line tool:

```shell
> scala SOURCE_CODE
```

It is not strictly necessary to complile first and then run the code. We can simply run 

```shell
> scala SOURCE_CODE.scala
```
In this case, the interpreter will look for any object within the file `SOURCE_CODE.scala` with a `main`method with the appropriate signature and will then call it.

Last, we can load the source file into REPL and try things out.

```shell
> scala
...

scala> :load SOURCE_CODE.scala
```

### Modules, objects and namespaces

For example:

```scala
MyModule.func()
```

`MyModule` is `func`'s *namespace*. `func` is defined in the `MyModule` *object*.

To import all members of a module:

```scala
import MyModule._
```

### Passing functions to functions

#### Writing loops functionally

Take `factorial` as an example:

```scala
def factorial(n: Int): Int = {
	def go(n: Int, acc: Int): Int ={
		// n: remaining value
		// acc: accumulated factorial
		if (n <= 0) acc
		else go(n-1, n*acc)
	}

	go(n, 1)
}
```

The loops functionally is implemented with a recursive function instead of mutating a loop variable. We defined a helper function inside the body which is typically called `go` or `loop` by convension.

In scala, we can define function inside of any block. Since it's local, the `go` function can only be referred to from within the body of `factorial` function. 

##### A side note on tail calls
A call is said to be in tail position if the caller does nothing other than return the value of the recursive call (i.e. `go(n-1, n*acc)`). On other hand, if we had `1 + go(n-1, n*acc)` then `go` would no longer be in tail position. 

If all recursive calls made by a function are in tail position, Scala automatically compilies the recursion to iterative loops that don't consume call stack frames for each iteration. By default, Sacala won't tell if tail call elimination was successful, but if we are expecting this to occur for a recursive function we write, we can tell scala compliler by adding `@annotation.tailrec` before the `go` function.

### Polymorphic functions

We we write HOFs (higher order functions), we want to write code that works for any type it's given. These are called *polymorphic* functions.

```scala
def findFirst[A](as: Array[A], p: A => Boolean): Int = {
	@annotation.tailrec
	def loop(n: Int): Int =
	    if (n >= as.length) -1
	    else if (p(as(n))) n
        else loop(n + 1)
    loop(0)
}
```
If a function  is polymorphic in some type `A`, the on;y operations that can be performed on that `A` are those passed into the function as arguments. 

* Currying, uncurrying and compositions of functoins is similar to the definition in mathematics. Just take `=>` as mappings.
