# calculator-lib
Java library for multi-type calculators

This library was originally created in by [Calculator project](https://github.com/endreman0/Calculator). However, I have decided to move the core processing part of the project out into a separate library, so I can use it in other projects.

All of the difficult code is in this repository now, so theoretically the only change that has to be made to Calculator is to change the dependency's version number.

## Issue Reporting
Please report to [Calculator](https://github.com/endreman0/Calculator/issues) with `client` label.


## `expression-tree` ![complete](https://img.shields.io/badge/status-complete-brightgreen.svg)
The `expression-tree` method converts input into a tree of expressions, each containing other expression to represent the full input. Expressions can be evaluated recursively to get an answer. For example, the input `"3 + 2 * 5"` would result in `OperatorExpression(Integer(3), "+", OperatorExpression(Integer(2), "*", Integer(5)))`. To evaluate this, you would simply call `evaluate()` on the addition expression. It would evaluate the integer (which evaluates to itself), then the multiplication expression. The multiplication would evaluate the 2 and 5, multiply them, and return 10. The addition expression would then add 3 and 10, and return 13 for its evaluation.

The benefit of this over the previous approach is that expressions can be stored and evaluated on demand. For example, if the equation `f(x) = 3 * x + 2` was inputted, only when the user then called `f(3)` would it be evaluated.
