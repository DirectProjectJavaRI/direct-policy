# Policy Engine Architecture

The policy engine is more or less a boolean logic engine. It processes a set of rules called a [PolicyExpression](http://api.directproject.info/direct-policy/1.0/apidocs/org/nhindirect/policy/PolicyExpression.html) against a provided X509 certificate and determines if the certificate is in compliance with the policy. The engine itself is structurally similar to a compiled programming language and runtime environment. Polices start as definition files written in a specific [PolicyLexicon](http://api.directproject.info/direct-policy/1.0/apidocs/org/nhindirect/policy/PolicyLexicon.html), are [parsed](http://api.directproject.info/direct-policy/1.0/apidocs/org/nhindirect/policy/PolicyLexiconParser.html) to an intermediate state, [compiled](http://api.directproject.info/direct-policy/1.0/apidocs/org/nhindirect/policy/Compiler.html) into an [ExecutionEngine](http://api.directproject.info/direct-policy/1.0/apidocs/org/nhindirect/policy/ExecutionEngine.html) specific set of [Opcodes](http://api.directproject.info/direct-policy/1.0/apidocs/org/nhindirect/policy/Opcode.html), and finally processed by the ExecutionEngine.

The following diagram is a modular view of the engine:

![policyEnting](assets/policyEngine.PNG)

## Policy Definition

Policies are expressed in a boolean type syntax where attributes and extension values of an X509 certificate are evaluated against provided [literals](http://api.directproject.info/direct-policy/1.0/apidocs/org/nhindirect/policy/LiteralPolicyExpression.html). The engine provides several operators and supports compound and ternary expression creating a powerful and flexible boolean assertion module. In addition, certain certificate extensions can be flagged as required meaning that the absence of the extension will automatically cause the certificate to be in non compliant with the policy. All definitions ultimately evaluate to a single boolean value indicating if a certificate is in compliance with the policy. A true value indicates compliance while a false value indicates non-compliance.

All policies are written using a formal definition lexicon/syntax, and supported lexicons are enumerated in the PolicyLexicon class:

* [Serialized XML](http://api.directproject.info/direct-policy/1.0/apidocs/org/nhindirect/policy/impl/XMLLexiconPolicyParser.html): An XML serialized representation of the intermediate state.
* [Serialized Java Object](http://api.directproject.info/direct-policy/1.0/apidocs/org/nhindirect/policy/impl/JavaSerializedObjectLexiconPolicyParser.html): An Java object serialized representation of the intermediate state.
* [Simple Text Version 1](http://api.directproject.info/direct-policy/1.0/apidocs/org/nhindirect/policy/impl/SimpleTextV1LexiconPolicyParser.html): A simple syntax similar to writing an "if" statement.

## Policy Lexicon Parser and Compiler

The engine translates policy definitions into executable operations or "opcodes" using a two pass process.

### Parsing

A policy definition written in a supported lexicon/syntax is translated into common intermediate state by a [PolicyLexiconParser](http://api.directproject.info/direct-policy/1.0/apidocs/org/nhindirect/policy/PolicyLexiconParser.html). The parser validates the syntax for correctness and throws a [PolicyParseException](http://api.directproject.info/direct-policy/1.0/apidocs/org/nhindirect/policy/PolicyParseException.html) is any errors were found. Parsing errors are currently limited in information (ex. they do not yet indicated line or character number where the error occurred), but they do attempt to provide enough information to isolate the cause of the syntax error.

After parsing is complete (and assuming successful), the parsed definition is stored as a [PolicyExpression](http://api.directproject.info/direct-policy/1.0/apidocs/org/nhindirect/policy/PolicyExpression.html) object which is the intermediate state. Parsed PolicyExpressions are generally comprised of a tree structure where the leaf nodes are either literals or other policy operator [expressions](http://api.directproject.info/direct-policy/1.0/apidocs/org/nhindirect/policy/OperationPolicyExpression.html). The top of a valid tree always consists of operator that evaluates to a boolean value.

Intermediate state/PolicyExpression objects can be serialized to an external medium using the [PolicyExpressionSerializer](http://api.directproject.info/direct-policy/1.0/apidocs/org/nhindirect/policy/PolicyExpressionSerializer.html) which is supported by almost all PolicyLexiconParser implementations.

Parsers should be created using the [PolicyLexiconParserFactory](http://api.directproject.info/direct-policy/1.0/apidocs/org/nhindirect/policy/PolicyLexiconParserFactory.html). The following is a code snippet example that creates a parser and parses a definition into an intermediate state object:

```  
final InputStream stream = IOUtils.toInputStream("X509.TBS.EXTENSION.SubjectKeyIdentifier+ = 1.3.2.3");

  final LexiconParser parser = PolicyLexiconParserFactory.getInstance(PolicyLexicon.SIMPLE_TEXT_V1);
  
  PolicyExpression expression = null;
  
  try
  {
     expression = parser.parse(stream);
  }
  catch (PolicyParseException e)
  {
    // handle the exception
  }
```

### Compiling

The [compiler](http://api.directproject.info/direct-policy/1.0/apidocs/org/nhindirect/policy/Compiler.html) is the second step of the execution process after a definition has been parse into a [PolicyExpression](http://api.directproject.info/direct-policy/1.0/apidocs/org/nhindirect/policy/PolicyExpression.html) tree. The compiler consumes the parsed expression and a certificate and generates an ordered vector of [Opcode](http://api.directproject.info/direct-policy/1.0/apidocs/org/nhindirect/policy/Opcode.html) objects that are specific to a particular [ExecutionEngine](http://api.directproject.info/direct-policy/1.0/apidocs/org/nhindirect/policy/ExecutionEngine.html). If an error is encountered in the compilation process, a [PolicyProcessException](http://api.directproject.info/direct-policy/1.0/apidocs/org/nhindirect/policy/PolicyProcessException.html) exception is thrown.

The compiler extracts attribute values from the provided certificate for policy expression tokens that evaluate to [ReferencePolicyExpressions](http://api.directproject.info/direct-policy/1.0/apidocs/org/nhindirect/policy/ReferencePolicyExpression.html) (lexicons specify what syntactical tokens represent certificate attributes). By default, the compile operation throws a [PolicyRequiredException](http://api.directproject.info/direct-policy/1.0/apidocs/org/nhindirect/policy/PolicyRequiredException.html) exception if a required attribute is missing in the provided certificate. This halts the evaluation process immediately at the point the first missing required field is encountered. However, there are use cases, such as a policy validation tool, where generating a complete list of missing fields may be useful. In addition, it may desirable to process the certificate through the execution engine even though it is known that required fields are missing. Obviously this is not the most efficient operating mode when executing inside of an environment where the only result of interest is the simple binary decision of policy compliance.

To enable the ability to retrieve a list of all known missing fields (or other compilation errors), the compiler supports a report mode that can be enabled by calling the [setReportModeEnabled](http://api.directproject.info/direct-policy/1.0/apidocs/org/nhindirect/policy/Compiler.html#setReportModeEnabledboolean) method. When reporting mode is turned on, the compiler will no longer throw an exception when a required attribute is missing. After compilation is complete, a collection of all compilation issues can be retrieved by calling the [getCompilationReport](http://api.directproject.info/direct-policy/1.0/apidocs/org/nhindirect/policy/Compiler.html#getCompilationReport) method.

Compilers are created by directly instantiating a concrete implementation. The following is a code snippet example expands on the previous example to compile a parsed policy definition and associate it with a certificate. It uses a [StackMachineCompiler](http://api.directproject.info/direct-policy/1.0/apidocs/org/nhindirect/policy/impl/machine/StackMachineCompiler.html) that is executed by the [StackMachine](http://api.directproject.info/direct-policy/1.0/apidocs/org/nhindirect/policy/impl/machine/StackMachine.html) execution engine.

```
  final X509Certificate certToEvaluate = getCertificateToEvaluate();
  
  final InputStream stream = IOUtils.toInputStream("X509.TBS.EXTENSION.SubjectKeyIdentifier+ = 1.3.2.3");

  final LexiconParser parser = PolicyLexiconParserFactory.getInstance(PolicyLexicon.SIMPLE_TEXT_V1);
  
  final Compiler compiler = new StackMachineCompiler();
  
  final Vector<Opcode> opcodes = null;
  
  PolicyExpression expression = null;
  
  try
  {
     expression = parser.parse(stream);
     
     opcodes = compiler.compile(certToEvaluate, expression);
  }
  catch (PolicyProcessException e)
  {
    // handle the exception
  }
```

### Execution Engine

The [ExecutionEngine](http://api.directproject.info/direct-policy/1.0/apidocs/org/nhindirect/policy/ExecutionEngine.html) is the final stage of the policy evaluation process. It executes an ordered vector of opcodes and produces a boolean result indicating if the previously provided certificate is compliant with the provided policy definition. You may think of the execution engine as the runtime library of the policy engine.

Remember that opcodes produced by a compiler are targeted to be executed by a specific execution engine implementation. The reference implementation provides a default engine based on a stack machine. The [StackMachine](http://api.directproject.info/direct-policy/1.0/apidocs/org/nhindirect/policy/impl/machine/StackMachine.html) class implements the engine, and as illustrated in the previous section, opcodes for the machine are generated by the [StackMachineCompiler](http://api.directproject.info/direct-policy/1.0/apidocs/org/nhindirect/policy/impl/machine/StackMachineCompiler.html) class.

The execution engine implements one simple method: [evaluate](http://api.directproject.info/direct-policy/1.0/apidocs/org/nhindirect/policy/ExecutionEngine.html#evaluatejava.util.Vector). This method takes the the ordered vector of opcodes from the compiler and returns a simple boolean result. If the return value is true, then the certificate provided to the compiler is considered in compliance with the policy definition. A return value of false indicates the opposite. Like the parser and compiler, the execution engine throws a [PolicyProcessException](http://api.directproject.info/direct-policy/1.0/apidocs/org/nhindirect/policy/PolicyProcessException.html) if an error occurs during the execution process.

```
  final X509Certificate certToEvaluate = getCertificateToEvaluate();
  
  final InputStream stream = IOUtils.toInputStream("X509.TBS.EXTENSION.SubjectKeyIdentifier+ = 1.3.2.3");

  final LexiconParser parser = PolicyLexiconParserFactory.getInstance(PolicyLexicon.SIMPLE_TEXT_V1);
  
  final Compiler compiler = new StackMachineCompiler();
  
  final ExecutionEngine engine = new StackMachine();
  
  final Vector<Opcode> opcodes = null;
  
  PolicyExpression expression = null;
  
  try
  {
     expression = parser.parse(stream);
     
     opcodes = compiler.compile(certToEvaluate, expression);
     
     return engine.evaluate(opcodes);
  }
  catch (PolicyProcessException e)
  {
    // handle the exception
  }
```

### Policy Filter

In the previous section the policy engine was broken down into its individual parts and code provided to illustrate how the pieces work together to ultimately discern if a certificate is compliant with a given policy. Utilizing the components modularly is extremely useful for uses such as writing a policy editor or an external evaluation tool. However, the three phase process is almost always the same when starting with a definition and a certificate and evaluating for compliance. It would be desirable to aggregate the process is a simple construct. Thus the introduction of the [PolicyFilter](http://api.directproject.info/direct-policy/1.0/apidocs/org/nhindirect/policy/PolicyFilter.html).

Policy filters are the core constructs of the policy engine as they determine if an X509 certificate is compliant with a given policy. Internally they encapsulate the modular pieces of the engine (parser, compiler, and execution engine), and orchestrate the flow of a certificate and policy through the engine's components. Each functional component of the policy engine can be used independently to perform specific tasks such as those stated in the previous paragraph, however filters combine the components together to achieve the primary value proposition of the engine: evaluating X509 certificate compliance to a policy.

The term filter is derived from the primary use case of the PolicyFilter: to filter non compliant certificates within the security and trust [agent](http://api.directproject.info/agent/2.0.13/apidocs/org/nhindirect/stagent/NHINDAgent.html) process. In the agent, filters are applied at strategic intercept points to remove discovered and other encountered certificates that do not meet policy requirements. These intercept points are covered in detail in the [STA](http://api.directproject.info/direct-policy/1.0/users-guide/dev-sta.html) section of this book.

The filter provides one method with two variations:

```
  boolean isCompliant(X509Certificate cert, InputStream policyStream, PolicyLexicon lexicon) throws PolicyProcessException;
	
  boolean isCompliant(X509Certificate cert, PolicyExpression expression) throws PolicyProcessException;
```

The first version accepts the certificate to be evaluated, the policy definition as an InputStream, and the lexicon/syntax that the definition is written in.

The second version is a slight that exists for performance reasons. In some use cases (specifically in the security and trust agent), the same PolicyExpression will be utilized multiple times, so it is not necessary to parse the policy definition each time a certificate needs to be evaluated. This version allows a definition to be pre-parsed and reused against multiple certificates.

Parsers should be created using the [PolicyFilterFactory](http://api.directproject.info/direct-policy/1.0/apidocs/org/nhindirect/policy/PolicyFilterFactory.html). By default, the factory creates a filter using stack machine compiler and execution engine. However, the compiler and engine can be overridden by simply providing instances of the desired compiler and engine. The following is a code snippet example that creates a default policy filter an:
Execution engines are created by directly instantiating a concrete implementation. The following is a code snippet example expands on the previous example to compile a parsed policy definition, associate it with a certificate via a compiler, and determine if the certificate is compliant with the policy via the execution engine.

```
  final X509Certificate certToEvaluate = getCertificateToEvaluate();
  
  final InputStream stream = IOUtils.toInputStream("X509.TBS.EXTENSION.SubjectKeyIdentifier+ = 1.3.2.3");
  
  try
  {  
     final PolicyFilter filter = PolicyFilterFactory.getInstance();
     
     return filter.isCompliant(certToEvaluate, stream, PolicyLexicon.SIMPLE_TEXT_V1);
  }
  catch (PolicyProcessException e)
  {
    // handle the exception
  }
```
