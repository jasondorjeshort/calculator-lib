# calculator-lib
Java library for multi-type calculators

This library was originally created in by [Calculator project](https://github.com/endreman0/Calculator). However, I have decided to move the core processing part of the project out into a separate library, so I can use it in other projects.

All of the difficult code will be in this repository now, so theoretically the only changes that will be made to Calculator is to change the dependency's version number.

## `expression-tree` ![in development](https://img.shields.io/badge/status-development-yellowgreen.svg)
The `expression-tree` branch is for testing out a new way of parsing input. The idea is to have an `Expression<? extends Type>` class that stores something like a `Promise` for a calculation: it will always evaluate to either an instance of `Type` or an exception, and it isn't necessarily evaluated right after being parsed. For example, functions are the natural child of this behavior; store the expression `3 * x - 2_1/2` in function f and evaluate it whenever f is called.
