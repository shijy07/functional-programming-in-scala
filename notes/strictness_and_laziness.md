### Strict and non-strict functions
In scala `&&` and `||` are non-strict functions. `&&` only evaluate the second expression when the first expression is `true`. `||` only evaluate the second expression when the first expression is `false`.
In Scala, we can write non-strict functions by accepting some of our arguments unevaluated.

Addinng `lazy` keyword to a `val`declaration will cause Scala to delay evaluation of the right-hand side of the `lazy val` declaration until it's first referenced. It will also cache the result so that the subsequent reference don't trigger repeated evaluation.

If the evaluation of an expression runs forever or throws an error instead of returning a definite value, we say that the expression doesn't terminate, or that it evaluate to bottom. A function `f` is strict if the expression `f(x)` evaluates to bottom for all `x` that evaluate to bottom. A non-strict function in Scala takes its argument *by name* rather than *by value*. 

