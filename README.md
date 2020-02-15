This is a Spring based app, I did for a code screening. The exercise was given to me by a recruiter.

The tools iv'e chosen to develop it are:
Spring(boot, web), modelmapper, java 8, lombok, assertJ, mockito

Post development comments:
I've chosen Spring web+boot an lobock as my main tools. The reason I selected those is just because they significantly reduce the ammount of boilerplate.

I did record a demo of the application, it can be found here: http://javing.blogspot.com/2020/02/restful-app-demo-and-explanation.html

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

I spotted bug after I did the the demo, basically I didn't train my mock to have an expiration time in the retrieval scenario
and I totally forgot to test that when we are reading an offer, it needs to check if it is expired. So I added a bug fix for that
I just want to mention that cancelling the the offer at retrival if it has expired does work but in a real life scenario probably
an async service should review the data in the db in the case is expired and mark it as expired.
    
To boot the app:
```
./gradlew bootRun
```

Sample Requests:
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
