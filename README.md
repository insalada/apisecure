<h1>Api Secure</h1>

An API REST example secured with Basic-Auth

<h2>Description</h2>

A convenient API REST example that shows how to secure your Spring boot Rest API in a quick and easy way. It makes use of:

- [Spring Boot, Spring Security](https://github.com/spring-projects/spring-framework)
- [Embedded MongoDB Flapdoodle](https://github.com/flapdoodle-oss/de.flapdoodle.embed.mongo)


<h2>How does it work?</h2>

* **SecurityConfig.java** file provides spring security configuration to read from mongodb in memory database.
* **MainController.java** provides controllers with different access levels. It is based on JSR250 specification to use proper annotations (easier, isn't it!).
* **CredentialRepository.java** implements Spring MongoRepository.
* **Credential.java** maps mongo document for users/password.
* **ApisecureApplication.java** starts spring boot and saves users in memory.

<h2>Try it!</h2>

You can either run the unit tests on your favourite IDE or manually with Maven or start the service and try yourself. It's up to you!
