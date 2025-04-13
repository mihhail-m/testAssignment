# Repository contains solution for test assignment for Test Automation Engineer role

## Prerequisites

- Java 17+
- Maven
- Docker
- **p.s** If on Windows install GNUmake or use GitBash/WSL to make use of `Makefile`

## Setup

### 1. Clone it

```shell
git clone https://github.com/mihhail-m/testAssignment.git
```

### 2. Install dependencies

```shell
mvn install
```

### 3. Build image 

Use `make` to build, run and reload mappings.

```shell
make build
```

### 4. Run container

This will run container instance on port `8080`

```shell
make run
```

### 5. Run tests

To run all tests: 

```shell
mvn test
```

To run specific test suit:

```shell
mvn test -Dtest=TestSpecFile
```

### 6. Reload mappings

If mappings where updated you can hot reload them without rebuilding container.

```shell
make reload
```
