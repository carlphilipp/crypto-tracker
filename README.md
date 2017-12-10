### Development Environment
#### Build

To start the backend:
```
./gradlew clean build
java -jar back-end/build/libs/back-end-0.0.1-SNAPSHOT.jar
java -jar gateway/build/libs/gateway-0.0.1-SNAPSHOT.jar
```

It will start the back-end app on `http://localhost:8280` and the gateway on `http://localhost:8180`

To start the frontend:

```
cd front-end && npm install && npm start
```

The browser should be started on `http://localhost:3000`

### Production Environment
#### Build

```
./gradlew clean build
cd front-end
npm run build

```

#### Deploy

```
java -jar -Dspring.profiles.active=prod back-end-XXX.jar
java -jar gateway-XXX.jar


```