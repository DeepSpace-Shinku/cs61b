# **Scheme Interpreter**
This program is a scheme interpreter made with Python based on a [cs61a course project](https://inst.eecs.berkeley.edu/~cs61a/fa20/proj/scheme/) at UC Berkeley.

## **Files:**
Here are all the files that were included in the repository:
- `scheme.py`: implements the REPL and an evaluator for Scheme expressions. Run this file to start the interrupter.
- `scheme_reader.py`: implements the reader for Scheme input. The `Pair` class and `nil` definition can be found here.
- `scheme_builtins.py`: implements built-in Scheme procedures in Python.
- `buffer.py`: implements the `Buffer` class, used in `scheme_reader.py`
- `ucb.py`: utility functions for use in 61A projects of UC Berkeley
- `questions.scm`: some test questions of scheme programs
- `ok`: the autograder of UC Berkeley
- `tests.scm`: a collection of test cases written in Scheme to test the correctness of the interpreter
- `tests`: a directory of tests used by autograder of UCB
- `mytests.rst`: a file where you can add your own test

## **Features:**
This interpreter can interprete a subset of scheme languages, including the following features:
### **Read-Eval-Print**
The interpreter reads Scheme expressions, evaluates them, and displays the results.
```
scm> 2
2
scm> (+ 2 3)
5
scm> ((lambda (x) (* x x)) 5)
25
```

### **Load**
You can load a file by passing in a symbol for the file name. For example, to load `tests.scm`, evaluate the following call expression.
```
scm> (load ‘tests)
```

### **Symbols**
Various dialects of Scheme are permissive about identifiers (which serve as symbols and variable names).

Our rule is that:
> An identifier is a sequence of letters (a-z and A-Z), digits, and characters in `!$%&*/:<=>?@^_~-+.` that do not form a valid integer or floating-point numeral and are not existing special form short hands.
Our version of Scheme is case-insensitive: two identifiers are considered identical if they match except possibly in the capitalization of letters. They are internally represented and printed in lower case:
```
scm> ‘Hello
hello
```

## **Implementation overview**
Here is a brief overview of each of the Read-Eval-Print Loop components in our interpreter. Refer to this section as you work through the project as a reminder of how all the small pieces fit together!

### **Read**
This step parses user input (a string of Scheme code) into our interpreter`s internal Python representation of Scheme expressions (e.g. Pairs).

- Lexical analysis are implemented in the `tokenize_lines` function in `scheme_tokens.py`. This function returns a `Buffer` (from `buffer.py`) of tokens.
- Syntactic analysis happens in `scheme_reader.py`, in the `scheme_read` and `read_tail` functions. Together, these mutually recursive functions parse Scheme tokens into our interpreter's internal Python representation of Scheme expressions.

### **Eval**
This step evaluates Scheme expressions (represented in Python) to obtain values. Code for this step is in the main `scheme.py` file.

- Eval happens in the `scheme_eval` function. If the expression is a call expression, it gets evaluated according to the rules for evaluating call expressions. If the expression being evaluated is a special form, the corresponding `do_?_form` function is called. You will complete several of the `do_?_form` functions.
- Apply happens in the `scheme_apply` function. If the function is a built-in procedure, `scheme_apply` calls the `apply` method of that `BuiltInProcedure` instance. If the procedure is a user-defined procedure, `scheme_apply` creates a new call frame and calls `eval_all` on the body of the procedure, resulting in a mutually recursive eval-apply loop.
### **Print**
This step prints the `__str__` representation of the obtained value.
### **Loop**
The logic for the loop is handled by the `read_eval_print_loop` function in `scheme.py`.

### **Exceptions**
If something wrong happened in the user's code, the interpreter will handle it by raising a `SchemeError`. All `SchemeError` exceptions are handled and printed as error messages by the `read_eval_print_loop` function in `scheme.py`.

## **Running the interpreter**
To start an interactive Scheme interpreter session, type:
```
python3 scheme.py
```
You can use your Scheme interpreter to evaluate the expressions in an input file by passing the file name as a command-line argument to `scheme.py`:
```
python3 scheme.py tests.scm
```
Currently, your Scheme interpreter can handle a few simple expressions, such as:
```
scm> 1
1
scm> 42
42
scm> true
#t
```
To exit the Scheme interpreter, press `Ctrl-d` or evaluate the `exit` procedure.
```
scm> (exit)
```
