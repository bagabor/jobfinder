# jobfinder


This application is trying to satisfy the requirements for a homework.

It uses H2 inmermory database so not required to install and connect any other db.
Lombok was used. 
Hateos has been added as a dependency but later it became unused. (responseEntity response type has been added to achieve the response consistency).



available endpoints:

http://localhost:8080/clients/ -> POST for client creation

http://localhost:8080/positions -> POST for position creation

http://localhost:8080/positions/ -> GET for fetching positions

http://localhost:8080/positions/{id} -> GET for fetching only one position


problems that are arising and still showing:
- The generic exception format hasn't been achieved totally...
- The apiKey is not populated for every positions when we start a bulk fetching


possible improvements:

-should check the position that we want to save... to prevent the duplications...
-dto valid format should be moved to a global validator like a controller advise
-remove the stack trace from the response.... if something went wrong
-useing hateos instead of responseEntity
-use basic auth instead of an apikey
-add monitoring imporements (Spring actuator)
-add swagger