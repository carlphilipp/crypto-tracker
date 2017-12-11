### Prerequisites

MongoDB (>=3.4.10) installed and running on default port

### Development Environment
#### Build

```
./gradlew clean build
cd front-end && npm install
```

#### Deploy & start

```
java -jar -Dspring.profiles.active=dev back-end/build/libs/back-end-0.0.1-SNAPSHOT.jar
java -jar gateway/build/libs/gateway-0.0.1-SNAPSHOT.jar
cd front-end && npm install && npm start
```

It will start the back-end app on `http://localhost:8280` and the gateway on `http://localhost:8180`
The browser should be started on `http://localhost:3000`

### Production Environment
#### Build

```
./gradlew clean build
cd front-end
npm run build

```

#### Deploy & start

```
java -jar back-end-XXX.jar
java -jar gateway-XXX.jar
cd front-end
serve -s build
```