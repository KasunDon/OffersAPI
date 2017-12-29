## Offers API
Allows merchants to create offers, display offers and cancel offers using RESTful API calls.

### Requirements
This project requires following software versions or higher in order to compile, package and execute the JAR.

```
JDK8 or later version
maven2 or later version
```

### Running JAR
Running JAR file can be done by executing following command. please make sure to set appropiate `spring.profiles.active` property depend on the environment you trying to run this JAR.

```
java -D"spring.profiles.active=staging" -jar target/offersapi.jar com.worldpay.offersapi.App
```

### REST endpoints
The REST endpoints are declared as follows to interact with this API.

##### Create offers

Syntax to create an offer

**Endpoint :** `http://localhost:8080/<merchantId>/create-offer`

***Required Parameters***
```
description=offer (string)
currency=GBP (string)
price=1.00 (decimal)
expiryTime=60000000 (epoch timestamp)
```

```
curl -X PUT \
	-d 'description=offer&currency=GBP&price=1.00&expiryTime=60000000' \
 	http://localhost:8080/1001/create-offer
```

##### Display offers

Syntax to display an offer

**Endpoint :** `http://localhost:8080/<merchantId>/display-offer/<offerId>`

```
curl -X GET http://localhost:8080/1001/display-offer/1
```

##### Cancel offers

Syntax to cancel an offer

**Endpoint :** `http://localhost:8080/<merchantId>/cancel-offer/<offerId>`

```
curl -X POST http://localhost:8080/1001/cancel-offer/2
```

##### Please Note: `merchantId` and `offerId` should be numeric values. This application allows to create/display/cancel offers for any `merchantId` (Assumes merchantId already registered).


### Application
This application based on `sparkjava` HTTP Framework to support endpoints and run embedded jetty web server. Currently application uses default port as `8080`, if needs to change port it can be easily configured by changing following config.
```
http.server.port=8080
```

Application runs background thread to monitor expiry time of offers. There's a refresh interval set for this purpose, if needed to tweak this value - please change following config.
```
app.expiryCheckRefreshInterval.ms=200
```

### Tests
Running `UnitTests` and `IntegrationTests` can be easily achieve by executing following maven commands.

##### Unit Test
```
mvn clean test
```

##### Integration Test
```
mvn clean verify
```

#### Packaging
Fat JAR can be easily build by running following `maven` command.

```
mvn clean verify package
```