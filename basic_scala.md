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