This is a Spring based app, I did for a code screening. The exercise was given to me by a recruiter.

The tools iv'e chosen to develop it are:
Spring(boot, web), modelmapper, java 8, lombok, assertJ, mockito

Comments/Assumptions

I created Request objects/DTO's(Data Transfer Objects) that I use them in Controllers. The domain objects are not
needed until we reach the service. This allows me to have more lean Controllers that only know about what is 
relevant to them.
    
In the controllers I created a validator instead using annotations for validation because I think I have more
flexibility when it comes to testing and also is a more of the natural outcome as result of test driving the code.
It's possible to refactor to validation annotations for some of the fields but I don't really see an advantage over
the current implementation with the validator class.
    
Exception handling was implemented in the way Spring suggests. The ExceptionTranslator class is a ExceptionHandler
any exception declared in the handler that the application throws, will be caught and translated. It's a comfortable
way of implementing exception handling. The translator translates to a value object called ClientError which allows
to customise the error message. This implementation does not require of the custom exceptions, ordinary RuntimeExceptions
could be used. But having them allows for more informative feedback to the end client. Because those exceptions relate
to the domain, I left them in the domain package.
    
To boot the app:
```
./gradlew bootRun
```

Sample Request:
```
curl --location --request POST 'http://localhost:8080/offers/create' \
--header 'Content-Type: application/json' \
--data-raw '{
	"description": "Offer Details",
	"price": "2.50",
	"currency": "GBP",
	"expiration": "2020-02-15"
}'


curl --location --request GET 'http://localhost:8080/offers/d8a73bfc-7aaa-4097-896c-ecb0ee25c441'



curl --location --request GET 'http://localhost:8080/offers/d8a73bfc-7aaa-4097-896c-ecb0ee25c441/cancel'
```
