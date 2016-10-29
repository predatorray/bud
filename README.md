# Bud

[![Join the chat at https://gitter.im/bud-lisp/Lobby](https://badges.gitter.im/bud-lisp/Lobby.svg)](https://gitter.im/bud-lisp/Lobby?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge&utm_content=badge)

[![Build Status](https://travis-ci.org/predatorray/bud.svg?branch=master)](https://travis-ci.org/predatorray/bud)
[![Coverage Status](https://coveralls.io/repos/github/predatorray/bud/badge.svg?branch=master)](https://coveralls.io/github/predatorray/bud?branch=master)

Bud is yet another Scheme-like Lisp dialect implemented in Java. It is simple, lightweight and embeddable. Most of the special forms including `quote`, `lambda`, `if`, `cond`, `and`, `or` and built-in functions like `car`, `cdr`, `eq?`, type predicates and arithmetic functions specified in R5RS are implemented.

## Prerequisites

1. JDK 7+
2. Apache Maven 3.0.4+

## Features

Like Scheme,

1. Functions are first-class object.
2. Functions and other data variables share a single namespace.
3. Tail calls are properly optimized.

A list of data types that are currently supported are shown below.

1. boolean
2. character
3. number (decimal)
4. string
5. symbol
6. list
7. function

## How to use

### Running REPL

Use the Maven wrapper to compile and execute,

    ./mvnw clean compile exec:java -Dexec.mainClass='me.predatorray.bud.lisp.BudRepl'

If the docker cli is available on your computer, you can just run the lastest `zetaplusae/bud` image published on Docker Hub,

    docker run -it --rm zetaplusae/bud:latest

Or, you can try [the REPL online](http://bud.predatorray.me/).

Finally, type your Lisp expressions.

    bud> "Hello, Bud!"
    "Hello, Bud!"

### Embed in your program

Install the artifact to your local repository.

    mvn clean install

Add the dependency in you pom.xml.

```xml
<dependency>
    <groupId>me.predatorray</groupId>
    <artifactId>bud</artifactId>
    <version>1.0-SNAPSHOT</version>
</dependency>
```

And, here is an example of interpreting source code:

```java
String sourceCode = "(+ 1 1)"
BudInterpreter interpreter = new BudInterpreter();
BudObject result = interpreter.execute(sourceCode);
assert new BudNumber(new BigDecimal(2)).equals(result);
```
