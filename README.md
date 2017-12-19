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

Add jasypt:
java -cp ~/.gradle/caches/modules-2/files-2.1/org.jasypt/jasypt/1.9.2/91eee489a389faba9fc57bfee75c87c615c19cd7/jasypt-1.9.2.jar  org.jasypt.intf.cli.JasyptPBEStringEncryptionCLI input=XY7kmzoNzl100 password=password algorithm=PBEWithMD5AndDES

Start with --jasypt.encryptor.password=password