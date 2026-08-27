---
title: Example Policies
---

# Example Policies

The policy language and its operators support a wide range of expressions. The examples below show policies for some commonly needed checks, all written in the [Simple Text lexicon](stext-lexicon). Each example evaluates to a single boolean value: `true` means the certificate complies with the policy, and `false` means it does not.

Remember that the engine and its lexicons allow compound expressions, so these examples can be combined in different permutations using the boolean operators (`&&`, `||`, `!`) and grouped with parentheses.

**A specific policy OID is present**

Checks that the certificate's certificate-policies extension contains the given policy OID.

```
X509.TBS.EXTENSION.CertificatePolicies.PolicyOIDs {?} 1.3.6.1.4.1.41179.0.1.2
```

**At least one of several policy OIDs is present**

Takes the intersection of the certificate's policy OIDs with the supplied list and checks that the result is not empty.

```
(^(X509.TBS.EXTENSION.CertificatePolicies.PolicyOIDs {}& 1.3.6.1.4.1.41179.0.1.2,3.2.22.1)) > 0
```

**The secure email extended key usage is present**

Checks that the certificate's extended key usage extension asserts the id-kp-emailProtection OID.

```
X509.TBS.EXTENSION.ExtKeyUsageSyntax {?} 1.3.6.1.5.5.7.3.4
```

**The certificate is an end-entity certificate**

```
X509.TBS.EXTENSION.BasicConstraints.CA = false
```

**The certificate is a CA certificate**

```
X509.TBS.EXTENSION.BasicConstraints.CA = true
```

**The key encipherment key usage bit is set**

```
(X509.TBS.EXTENSION.KeyUsage & 32) > 0
```

**The digital signature key usage bit is set**

```
(X509.TBS.EXTENSION.KeyUsage & 128) > 0
```

**Key encipherment is set and digital signature is not**

```
((X509.TBS.EXTENSION.KeyUsage & 32) > 0) && ((X509.TBS.EXTENSION.KeyUsage & 128) = 0)
```

**Digital signature is set and key encipherment is not**

```
((X509.TBS.EXTENSION.KeyUsage & 128) > 0) && ((X509.TBS.EXTENSION.KeyUsage & 32) = 0)
```
