# Repository contains solution for test assignment for Test Automation Engineer role

## Prerequisites

- Java 17+
- Maven
- Docker

## Setup

### 1. Clone it

```shell
git clone https://github.com/mihhail-m/testAssignment.git
```

### 2. Install dependencies

```shell
mvn install
```

### 3. Run standalone Wiremock

```shell
docker build -t wiremock-srv Dockerfile .
docker docker run -p 8080:8080 wiremock-srv
```

### 4. Run tests

```shell
mvn test
```

Alternatively you can navigate to your localhost with 8080 port