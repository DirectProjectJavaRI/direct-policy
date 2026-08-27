---
title: Overview
---

## Introduction

The Direct Project defines a simple, secure, and scalable way for participants to exchange health information over the internet. Its security model relies on S/MIME and X.509 certificates: messages are signed and encrypted with certificates, and trust between participants is established by validating those certificates against a set of trust anchors.

The Direct Project policy engine is an optional runtime module that adds a further layer of control on top of that model. It lets an implementation define and enforce its own X.509 certificate policy — rules that a certificate must satisfy before it is used, above and beyond basic path validation. For example, an organization can require that a certificate assert a particular key usage bit, contain a specific certificate policy OID, or chain to a specific issuer.

The primary use case for the engine is inside the Security and Trust Agent (STA), the component of a Direct implementation responsible for signing, encrypting, decrypting, and trust-validating messages. There, the engine acts as a filter: at defined points in the agent's processing, it removes any certificate that does not comply with the configured policy. Because the engine is modular and has no hard dependency on the agent, it can also be used on its own wherever certificate-policy evaluation is useful, such as in a standalone validation tool.

## Guides

This documentation describes the policy engine framework and explains how to write, test, and configure policy definitions.

* [Architecture Guide](arch-guide) — Describes the architecture of the policy engine and how the Security and Trust Agent interacts with it.
* [Tools](tools) — Explains how to author a policy definition. Includes an in-depth description of the Simple Text V1 lexicon and the tools for creating and testing policy definitions.
