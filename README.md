# hostfully-interview
Hostfully Technical Interview

## Introduction 

Hello, my name is Leonardo A. Alcantara, this is my Hostfully Technical Interview submission. 

## Swagger 

The REST API is documented using Swagger / Open API. To see the documentation, please access it [here](http://localhost:8080/swagger-ui/index.html).

## Postman Collection

I've also added a Postman Collection with all endpoints available to be used. It is located at the root of the project, in [this ](hostfully.postman_collection.json) file.

## Building and Running tests

In order to build and run tests, please use:

```
./gradlew clean build
```

At the end of the execution, you'll see a link to the Coverage Report as so: 

```bash
See report file:///Users/leonardo.alcantara/work/estudos/techint/hostfully-interview/build/reports/jacoco/test/html/index.html
```

The coverage statistics will also be displayed directly in the terminal output as so:

```bash
> Task :jacocoLogTestCoverage
Test Coverage:
    - Class Coverage: 100%
    - Method Coverage: 94.3%
    - Branch Coverage: 73.9%
    - Line Coverage: 95%
    - Instruction Coverage: 93.9%
    - Complexity Coverage: 84.9% 
```

### Spotless

I've configured an automatic code styling plugin called `Spotless`, in order to run it, just run the following command:

```bash
./gradlew spotlessApply 
```