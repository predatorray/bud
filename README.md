# Bud

[![Build Status](https://travis-ci.org/predatorray/bud.svg?branch=master)](https://travis-ci.org/predatorray/bud)
[![Coverage Status](https://coveralls.io/repos/github/predatorray/bud/badge.svg?branch=master)](https://coveralls.io/github/predatorray/bud?branch=master)

Bud is yet another scheme-like lisp dialect implemented in Java. It is simple, lightweight and embeddable. Most of the special forms including `quote`, `lambda`, `if`, `cond`, `and`, `or` and built-in functions like `car`, `cdr`, `eq?`, type predicates and arithmetic functions specified in R5RS are implemented.

## Prerequisites
1. JDK 7+
2. Apache Maven 3.0.4+

## Running REPL
First, use Maven to build the jar.

    mvn clean package

Then, run it as a Java program.

    java -cp target target/bud-1.0-SNAPSHOT.jar me.predatorray.bud.lisp.BudRepl

Finally, type your Lisp expressions.

    bud> "Hello, Bud!"
    "Hello, Bud!"

## Embed in your program
Install the artifact to your local repository

    mvn clean install

Add the dependency in you pom.xml.

    <dependency>
        <groupId>me.predatorray</groupId>
        <artifactId>bud</artifactId>
        <version>1.0-SNAPSHOT</version>
    </dependency>

And, here is an example of evaluating source code:

    String sourceCode = "(+ 1 1)"
    Lexer lexer = new Lexer(sourceCode);
    Parser parser = new Parser();
    List<Expression> expressions = parser.parse(lexer); // In our case, only one expression is returned.
    Evaluator evaluator = new NaiveEvaluator();
    // Finally, evaluates the first expression under the default environment
    BudObject result = evaluator.evaluate(expressions.get(0), BuiltinsEnvironment.INSTANCE);
    assert new BudNumber(new BigDecimal(2)).equals(result);
