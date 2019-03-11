## Introduction

The DirectProject policy engine is an optional runtime module that enables enforcement of X509 certificate policy. The primary use case of the engine is within the security and trust agent to "filter" certificates at specific points in the agent's process. Because of the module and independent design of the engine, it can be utilized outside the agent where use cases exist to consume the engine in such a manner.

## Guides

This document describes the policy engine framework and how to write and configure policy definitions.

* [Architecture Guide](ArchGuide) - This guide describes the architectural makeup of the policy engine and how the security and trust agent interacts with it.
* [Tools](Tools) - This guide describes the process of creating a policy definition. It includes an in-depth description of the Simple Text V1 lexicon and tools for creating and testing policy definitions.