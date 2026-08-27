---
title: Simple Text Lexicon Version I
---

# Simple Text Lexicon Version I

The goal of the [SimpleTextV1Lexicon](http://api.directproject.info/direct-policy/1.0/apidocs/org/nhindirect/policy/impl/SimpleTextV1LexiconPolicyParser.html) is to let policy definitions be written in a simple syntax, much like an `if` statement. The lexicon defines a small set of reserved tokens — binary and unary operators, plus literals that represent attributes and extensions of an X.509 certificate. Definitions can be as simple as a single comparison or as involved as a deeply nested, grouped, ternary expression.

Expressions are evaluated left to right, applying each operator in the order it appears. The policy engine has no operator precedence, but this lexicon lets you group and prioritize sub-expressions by wrapping them in parentheses.

Some operators evaluate to integers, but a policy definition as a whole **must** evaluate to a boolean. For example, the following is syntactically correct but fails in the engine because it evaluates only to an integer:

```
  X509.TBS.EXTENSION.KeyUsage & 32
```

This is meant to check whether the keyEncipherment bit of the key usage extension is set, using a bitwise AND — but the result of that expression is an integer, not a boolean. A valid statement wraps the bitwise AND and compares its result:

```
  (X509.TBS.EXTENSION.KeyUsage & 32) > 0
```

Note the parentheses. Strictly speaking they are not required, because operators are applied left to right with no precedence — but grouping expressions this way makes them clearer and avoids surprises. Consider what happens if the operands are reversed:

```
  0 < X509.TBS.EXTENSION.KeyUsage & 32
```

This fails. Evaluated left to right, `0 < X509.TBS.EXTENSION.KeyUsage` produces a boolean, and the engine then tries to apply bitwise AND (`&`) to that boolean and the integer `32`. Adding parentheses forces the bitwise AND to run first:

```
0 < (X509.TBS.EXTENSION.KeyUsage & 32)
```

## Formal Syntax Definition

The following is an EBNF definition of the Simple Text Version 1 lexicon:

```
 letter = "A" | "B" | "C" | "D" | "E" | "F" | "G"
      | "H" | "I" | "J" | "K" | "L" | "M" | "N"
      | "O" | "P" | "Q" | "R" | "S" | "T" | "U"
      | "V" | "W" | "X" | "Y" | "Z" 
      | "a" | "b" | "c" | "d" | "e" | "f" | "g"
      | "h" | "i" | "j" | "k" | "l" | "m" | "n"
      | "o" | "p" | "q" | "r" | "s" | "t" | "u" 
      | "v" | "w" | "x" | "y" | "z" ;
      
 digit = "0" | "1" | "2" | "3" | "4" | "5" | "6" | "7" | "8" | "9" ;
 
 symbol = "[" | "]" |  "." | "#" | "%" | "+" ;
 
 unaryOperator = "^" | "{}" | "{}!" | "!" | "@@" ;
 
 binaryOperator = "=" | "!=" |  ">" | "<" | "$" | "{?}" | "{?}!" | "{}$" 
      | "{}&" |  "||" | "&&"  | "&" | "|" ;
      
 operator = unaryOperator | binaryOperator ;
      
 literalExpression = { letter | digit | symbol | white space} ;
 
 rdnAttributeName = "CN" | "C" | "O" | "OU" | "ST" | "L" | "E" | "DC"
      | "DNQUALIFIER" | "SERIALNUMBER" | "SN" | "TITLE" | "GIVENNAME"
      | "INITIALS" | "PSEUDONYM" | "GERNERAL_QUALIFIER" | "DN" ;
      
 x509Expression = "X509.Algorithm" | "X509.Signature" ;
 
 tbsExpression = "X509.TBS.Version" | "X509.TBS.SerialNumber" 
      | "X509.TBS.Signature" | "X509.TBS.Issuer.", rdnAttributeName
      | "X509.TBS.Validity.ValidFrom" | "X509.TBS.Validity.ValidTo"
      | "X509.TBS.Subject.", rdnAttributeName | "X509.TBS.IssuerUniqueID"
      | "X509.TBS.SubjectUniqueID" | "X509.TBS.SubjectPublicKeyInfo.Algorithm"
      | "X509.TBS.SubjectPublicKeyInfo.Size" ;
      
 extensionExpression = "X509.TBS.EXTENSION.KeyUsage" | "X509.TBS.EXTENSION.SubjectAltName"
      | "X509.TBS.EXTENSION.SubjectDirectoryAttributes" 
      | "X509.TBS.EXTENSION.SubjectKeyIdentifier" | "X509.TBS.EXTENSION.IssuerAltName"
      | "X509.TBS.EXTENSION.AuthorityKeyIdentifier.KeyId" 
      | "X509.TBS.EXTENSION.AuthorityKeyIdentifier.CertIssuers"
      | "X509.TBS.EXTENSION.AuthorityKeyIdentifier.SerialNumber"
      | "X509.TBS.EXTENSION.CertificatePolicies.PolicyOIDs"
      | "X509.TBS.EXTENSION.CertificatePolicies.CPSUrls"
      | "X509.TBS.EXTENSION.PolicyMappings" | "X509.TBS.EXTENSION.BasicConstraints.CA"
      | "X509.TBS.EXTENSION.BasicConstraints.MaxPathLength"
      | "X509.TBS.EXTENSION.NameConstraints" | "X509.TBS.EXTENSION.PolicyConstraints"
      | "X509.TBS.EXTENSION.ExtKeyUsageSyntax" | "X509.TBS.EXTENSION.InhibitAnyPolicy"
      | "X509.TBS.EXTENSION.CRLDistributionPoints.FullName" 
      | "X509.TBS.EXTENSION.CRLDistributionPoints.RelativeToIssuer" 
      | "X509.TBS.EXTENSION.CRLDistributionPoints.Reasons"
      | "X509.TBS.EXTENSION.CRLDistributionPoints.CRLIssuer"
      | "X509.TBS.EXTENSION.FreshestCRL.FullName"
      | "X509.TBS.EXTENSION.FreshestCRL.RelativeToIssuer"
      | "X509.TBS.EXTENSION.FreshestCRL.Reasons" 
      | "X509.TBS.EXTENSION.FreshestCRL.CRLIssuer"
      | "X509.TBS.EXTENSION.AuthorityInfoAccessSyntax.Url"
      | "X509.TBS.EXTENSION.AuthorityInfoAccessSyntax.OCSPLocation"
      | "X509.TBS.EXTENSION.AuthorityInfoAccessSyntax.AccessMethod"
      | "X509.TBS.EXTENSION.SubjectInfoAccessSyntax.Url"
      | "X509.TBS.EXTENSION.SubjectInfoAccessSyntax.AccessMethod"
      | "X509.TBS.EXTENSION.SubjectInfoAccessSyntax.OCSPLocation" ;
      
  requiredExpression =  tbsExpression, "+" | extensionExpression, "+" ;
  
  certificateReferenceExpression = x509Expression | tbsExpression 
      | extensionExpression | requiredExpression ;
      
  operatorExpression = [{(}] unaryOperator, [{white space}],  (literalExpression | certificateReferenceExpression | operatorExpression) , [{)}] |
      [{(}], (literalExpression | certificateReferenceExpression | operatorExpression) ,  [{white space}] , binaryOperator , [{white space}]
      (literalExpression | certificateReferenceExpression | operatorExpression) , [{)}] ;
      
  policyExpression = {operatorExpression} 
```

## Literals

Literals are simply strings (white space included) and numbers used as operands. There is no special syntax to distinguish strings from numbers — the engine converts between them as needed. The elements of a collection literal are separated by commas (`,`). Boolean literals are written as the strings `true` and `false`.

## Operators

The lexicon provides a small, focused set of operators for evaluating X.509 certificate attribute and extension values. Operators are either unary or binary, but they can immediately follow one another to form ternary expressions. The full list is enumerated in the [PolicyOperator](http://api.directproject.info/direct-policy/1.0/apidocs/org/nhindirect/policy/PolicyOperator.html) class.

### Unary Operators

A unary operator precedes its single operand; white space may appear between the operator and the operand.

**SIZE: `^`**

Takes a collection as its single operand and returns an integer: the number of elements in the collection.

**EMPTY: `{}`**

Takes a collection as its single operand and returns a boolean indicating whether the collection is empty (contains zero elements).

**NOT EMPTY: `{}!`**

Takes a collection as its single operand and returns a boolean indicating whether the collection is not empty (contains one or more elements).

**LOGICAL NOT: `!`**

Takes a boolean as its single operand and returns the opposite value: `true` becomes `false`, and `false` becomes `true`.

**URI VALIDATE: `@@`**

Takes a string representing a fully qualified URI as its single operand. It returns a boolean indicating whether the URI is syntactically correct and whether the URI resource exists and is reachable from the calling application.

### Binary Operators

A binary operator takes two operands, one on each side, and is evaluated left to right.

**EQUALS: `=`**

Either operand may be of any type. Returns a boolean indicating whether the two operands are equal. Equality semantics depend on the operand type: if an operand is not a string or a number, the engine uses that object's `equals` method to determine equality.

**NOT EQUALS: `!=`**

Either operand may be of any type. Returns a boolean indicating whether the two operands are *not* equal. As with EQUALS, equality semantics depend on the operand type, and non-string, non-number operands are compared with their `equals` method.

**GREATER THAN: `>`**

Both operands must be numbers. Returns a boolean indicating whether the first operand is greater than the second.

**LESS THAN: `<`**

Both operands must be numbers. Returns a boolean indicating whether the first operand is less than the second.

**REGEX: `$`**

Both operands must be strings (numbers are treated as strings). Returns a boolean indicating whether the first operand, used as a regular expression, is found in the second operand. In other words, the first operand is the pattern to search for and the second operand is the string being searched.

**CONTAINS: `{?}`**

The first operand is an object of any type; the second is a collection of objects of any type. Returns a boolean indicating whether the first operand is present in the collection. Membership is tested with the Java collection's `contains()` method.

**NOT CONTAINS: `{?}!`**

The first operand is an object of any type; the second is a collection of objects of any type. Returns a boolean indicating whether the first operand is *not* present in the collection. As with CONTAINS, membership is tested with the Java collection's `contains()` method.

**CONTAINS REGEX: `{}$`**

The first operand is a string; the second is a collection of objects of any type. Returns a boolean indicating whether the first operand, used as a regular expression, matches any element of the collection. Non-string elements are converted to strings with their `toString()` method before matching.

**INTERSECTION: `{}&`**

Both operands are either a comma-delimited set of strings or a collection of objects of any type. Returns the set intersection of the two collections, computed with the Java collection's `retainAll` method. If the intersection is empty, an empty collection is returned. Comma-delimited strings are automatically converted to a collection of strings.

**LOGICAL OR: `||`**

Both operands must be booleans (each may itself be the result of another boolean expression). Returns a boolean indicating whether at least one operand is `true`.

**LOGICAL AND: `&&`**

Both operands must be booleans (each may itself be the result of another boolean expression). Returns a boolean indicating whether both operands are `true`.

**BITWISE OR: `|`**

Both operands must be numbers. Returns a number produced by a bitwise OR of the two values.

**BITWISE AND: `&`**

Both operands must be numbers. Returns a number produced by a bitwise AND of the two values.

## Certificate Reference Expressions

Certificate reference expressions are tokens that extract an attribute or extension value from an X.509 certificate. They fall into three categories, matching the structural sections of a certificate as defined by RFC 5280:

* Certificate attributes
* To-be-signed (TBS) attributes
* v3 extensions

The lexicon names many attributes and extensions (though not exhaustively), but the engine does not yet support all of them. The attributes most likely to be useful for policy checking are supported now; later versions of the engine will add more.

The following sections describe the level of support for each attribute and extension.

### Certificate Attributes

These are the top-level attributes of every X.509 certificate.

**X509.Algorithm**

Extracts the algorithm used to sign the certificate. Returns the signature algorithm OID as a string. Commonly used OIDs are enumerated in the `SignatureAlgorithmIdentifier` class.

**X509.Signature**

Not yet supported.

### To Be Signed Attributes

These attributes make up the to-be-signed (TBSCertificate) structure of every X.509 certificate. An attribute can be marked as *required* by appending the `+` token to the attribute token.

**X509.TBS.Version**

Not yet supported. Most current X.509 certificates use a value of `2`, indicating version 3.

**X509.TBS.SerialNumber**

Extracts the certificate's serial number. Returns the serial number as a string in hexadecimal form, with no white space between digits. Hexadecimal digits represented by letters are lowercase, and leading zeros are removed.

**X509.TBS.Signature**

Not yet supported. This is always the same value as `X509.Algorithm`, as defined by section 4.1.2.3 of [RFC 5280](http://www.ietf.org/rfc/rfc5280.txt).

**X509.TBS.Issuer.**

Extracts a specific relative distinguished name (RDN) attribute from the certificate's Issuer field. Returns the value of the requested attribute as a string, or an empty string if the attribute is not found. Append the RDN attribute name to this token. Commonly used RDN names are enumerated in the [RDNAttributeIdentifier](http://api.directproject.info/direct-policy/1.0/apidocs/org/nhindirect/policy/x509/RDNAttributeIdentifier.html) class.

For example, the following token extracts the common name attribute from the Issuer field:

```
X509.TBS.Issuer.CN
```

If the CN had the value `Test CN`, then only `Test CN` would be returned — not `CN=Test CN`. The exception is the `DN` (distinguished name) attribute, which returns the entire distinguished name in [RFC 2253](http://www.ietf.org/rfc/rfc2253.txt) format. For example:

```
X509.TBS.Issuer.DN
```

might return something like:

```
O=Cerner,L=Kansas City,ST=MO,C=US,CN=test.email.com
```

**X509.TBS.Subject.**

Identical to `X509.TBS.Issuer.`, except that attributes are extracted from the Subject field rather than the Issuer field.

**X509.TBS.Validity.ValidTo**

Not yet supported.

**X509.TBS.Validity.ValidFrom**

Not yet supported.

**X509.TBS.IssuerUniqueID**

Not yet supported.

**X509.TBS.SubjectPublicKeyInfo.Algorithm**

Extracts the algorithm of the subject's public key. Returns the public key algorithm OID as a string. Commonly used OIDs are enumerated in the `SignatureAlgorithmIdentifier` class.

**X509.TBS.SubjectPublicKeyInfo.Size**

Extracts the size of the subject's public key. Returns the key size in bits as a number.

### V3 Extensions

These are the v3 certificate extensions that make up the Extensions sequence of the X.509 certificate TBS structure. Any extension value can be marked as *required* by appending the `+` token to the extension token.

**X509.TBS.EXTENSION.KeyUsage**

Extracts the certificate's key usage extension. Returns a number that is the bitwise OR of the certificate's allowed key usages, or `0` if the extension is not present. All key usage bits are enumerated in the [KeyUsageBit](http://api.directproject.info/direct-policy/1.0/apidocs/org/nhindirect/policy/x509/KeyUsageBit.html) class.

**X509.TBS.EXTENSION.SubjectAltName**

Extracts the certificate's subject alternative name extension. Returns a collection of strings, where each string is the name type and the name itself joined by a colon (`:`). Returns an empty collection if the extension is not present. All name types are enumerated in the [GeneralNameType](http://api.directproject.info/direct-policy/1.0/apidocs/org/nhindirect/policy/x509/GeneralNameType.html) class.

Example entry for a domain- or organization-bound certificate:

```
dns:direct.securehealthemail.com
```

Example entry for an address-bound certificate:

```
rfc822:gm2552@direct.securehealthemail.com
```

**X509.TBS.EXTENSION.IssuerAltName**

Identical to `X509.TBS.EXTENSION.SubjectAltName`, except that values are extracted from the issuer alternative name extension rather than the subject alternative name extension.

**X509.TBS.EXTENSION.SubjectDirectoryAttributes**

Not yet supported.

**X509.TBS.EXTENSION.SubjectKeyIdentifier**

Extracts the certificate's subject key identifier extension. Returns the key identifier as a string, or an empty string if the extension is not present.

**X509.TBS.EXTENSION.AuthorityKeyIdentifier.KeyId**

Extracts the keyId field of the certificate's authority key identifier extension. Returns the key identifier as a string, or an empty string if the extension is not present or the field is not populated.

**X509.TBS.EXTENSION.AuthorityKeyIdentifier.CertIssuers**

Not yet supported.

**X509.TBS.EXTENSION.AuthorityKeyIdentifier.SerialNumber**

Not yet supported.

**X509.TBS.EXTENSION.CertificatePolicies.PolicyOIDs**

Extracts the certificate's certificate policies extension. Returns a collection of strings containing the policy OIDs in the extension, or an empty collection if the extension is not present.

**X509.TBS.EXTENSION.CertificatePolicies.CPSUrls**

Extracts the CPS (Certification Practice Statement) URL field of the certificate's certificate policies extension. Returns a collection of strings containing the CPS URL for each policy OID that publishes one. Returns an empty collection if the extension is not present or if no policy OID populates the CPS URL field.

**X509.TBS.EXTENSION.PolicyMappings**

Not yet supported.

**X509.TBS.EXTENSION.BasicConstraints.CA**

Extracts the CA indicator field of the certificate's basic constraints extension. Returns a boolean: `true` if the certificate is a CA certificate, `false` if it is an end-entity certificate. Returns `false` if the extension is not present or the field is not populated.

**X509.TBS.EXTENSION.BasicConstraints.MaxPathLength**

Not yet supported.

**X509.TBS.EXTENSION.NameConstraints**

Not yet supported.

**X509.TBS.EXTENSION.PolicyConstraints**

Not yet supported.

**X509.TBS.EXTENSION.ExtKeyUsageSyntax**

Extracts the certificate's extended key usage extension. Returns a collection of strings containing the OIDs of the supported extended usages, or an empty collection if the extension is not present. Commonly used extended key usage OIDs are enumerated in the `ExtendedKeyUsageIdentifier` class.

**X509.TBS.EXTENSION.InhibitAnyPolicy**

Not yet supported.

**X509.TBS.EXTENSION.CRLDistributionPoints.FullName**

Extracts the full name field of the certificate's CRL distribution points extension. Returns a collection of strings containing the locations of the CRLs supported by the certificate, or an empty collection if the extension is not present or the FullName field is not populated.

These are generally URIs — for example, HTTP or HTTPS URLs — of the CRL distribution points.

**X509.TBS.EXTENSION.CRLDistributionPoints.RelativeToIssuer**

Not yet supported.

**X509.TBS.EXTENSION.CRLDistributionPoints.Reasons**

Not yet supported.

**X509.TBS.EXTENSION.CRLDistributionPoints.CRLIssuer**

Not yet supported.

**X509.TBS.EXTENSION.FreshestCRL.FullName**

Not yet supported.

**X509.TBS.EXTENSION.FreshestCRL.RelativeToIssuer**

Not yet supported.

**X509.TBS.EXTENSION.FreshestCRL.Reasons**

Not yet supported.

**X509.TBS.EXTENSION.FreshestCRL.CRLIssuer**

Not yet supported.

**X509.TBS.EXTENSION.AuthorityInfoAccessSyntax.Url**

Extracts the certificate's authority information access extension. Returns a collection of strings, where each string is the access method type and the access URL joined by a colon (`:`). Returns an empty collection if the extension is not present. All access method types are enumerated in the `AuthorityInfoAccessMethodIdentifier` class.

Example entry for a CA issuers URL:

```
caIssuers:http://ca.cerner.com/public/root.der
```

Example entry for an OCSP URL:

```
OCSP:http://ca.cerner.com/OCSP
```

**X509.TBS.EXTENSION.SubjectInfoAccessSyntax.AccessMethod**

Not yet supported. Use `X509.TBS.EXTENSION.AuthorityInfoAccessSyntax.Url` instead.

**X509.TBS.EXTENSION.SubjectInfoAccessSyntax.OCSPLocation**

Extracts the certificate's subject information access extension. Returns a collection of strings containing the URLs of entries that use the OCSP access method type. Returns an empty collection if the extension is not present or if no entries use the OCSP access method type.
