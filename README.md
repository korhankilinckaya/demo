I created the project using spring initializr with the dependencies of
starter web to use restful apis/
openai for api creation/
h2 in-memory database/
junit 5 and mockito for service layer unit tests/
and lombok

I don't put extra parameters on Booking class such as customer ssn, hotel name, hotel room number etc.
I only focused on the business logic

You can test using the link below after running the application

http://localhost:8080/swagger-ui/index.html

guestName is what I discriminate Booking and Block. I guestName is "block" then it is block type Booking.

You can create Block by writing "block" in guest name and those Bookings can be overlapped, but 
when guestName is not "block" Bookings cannot be overlapped

also please check test classes
