# Shopping Cart

### Prerequisites
* Download and install [MongoDB](https://www.mongodb.com/)

* Download and install [Gradle](https://gradle.org/)

* Download and install [Yarn](https://yarnpkg.com/en/)

## Build the Backend
```bash
$ ./gradlew clean build
```

## Build the Frontend
Go to the frontend directory
```bash
$ cd webapp
```
```bash
$ yarn install
```

## Run
Start the backend
```bash
$ ./gradlew bootRun
```

Initialize mongodb with products data
```bash
$ run bin/initProduct.js within mongodb console
```

Go to the frontend directory
```bash
$ cd webapp
```

Start the frontend
```bash
$ yarn start
```

Navigate to http://localhost:3000