#!/bin/bash
#curl script
#https://github.com/ArberBajraktari/WebService
echo ""

echo "- Trying first what should not work"
echo "------------------------------------------------"
echo "- curl -X VIEW http://localhost:8000"
curl -X VIEW http://localhost:8000
echo "- curl -X GET http://localhost:8000"
curl -X GET http://localhost:8000
echo "- curl -X GET http://localhost:8000/msg"
curl -X GET http://localhost:8000/msg
echo "- curl -X GET http://localhost:8000/messages"
curl -X GET http://localhost:8000/messages
echo "- curl -X GET http://localhost:8000/messages/a"
curl -X GET http://localhost:8000/messages/a
echo "- curl -X GET http://localhost:8000/messages/2"
curl -X GET http://localhost:8000/messages/2
echo "- curl -X POST http://localhost:8000/messages/1"
curl -X POST http://localhost:8000/messages/1
echo "- curl -X PUT http://localhost:8000/messages"
curl -X PUT http://localhost:8000/messages
echo "- curl -X PUT http://localhost:8000/messages/2"
curl -X PUT http://localhost:8000/messages/2
echo "- curl -X DELETE http://localhost:8000/messages"
curl -X DELETE http://localhost:8000/messages
echo "------------------------------------------------"
echo ""
echo "- Trying what should work"
echo "------------------------------------------------"
echo "- curl -X POST http://localhost:8000/messages -d 'First message'"
curl -X POST http://localhost:8000/messages -d 'First message'
echo "- curl -X GET http://localhost:8000/messages"
curl -X GET http://localhost:8000/messages
echo "- curl -X POST http://localhost:8000/messages -d 'Second message'"
curl -X POST http://localhost:8000/messages -d 'Second message'
echo "- curl -X GET http://localhost:8000/messages"
curl -X GET http://localhost:8000/messages
echo "- curl -X GET http://localhost:8000/messages/1"
curl -X GET http://localhost:8000/messages/1
echo "- curl -X GET http://localhost:8000/messages/2"
curl -X GET http://localhost:8000/messages/2
echo "- curl -X POST http://localhost:8000/messages"
curl -X POST http://localhost:8000/messages
echo "- curl -X GET http://localhost:8000/messages/3"
curl -X GET http://localhost:8000/messages/3
echo "- curl -X PUT http://localhost:8000/messages/3 -d 'Changing third message'"
curl -X PUT http://localhost:8000/messages/3 -d 'Changing third message'
echo "- curl -X GET http://localhost:8000/messages/3"
curl -X GET http://localhost:8000/messages/3
echo "- curl -X DELETE http://localhost:8000/messages/2"
curl -X DELETE http://localhost:8000/messages/2
echo "- curl -X GET http://localhost:8000/messages"
curl -X GET http://localhost:8000/messages
echo "- curl -X GET http://localhost:8000/messages/2"
curl -X GET http://localhost:8000/messages/2
echo "------------------------------------------------"
echo ""
