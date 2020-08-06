* Build and Run Floodlight:

  * Download this code

    Command line:
    > ant
 
    > java -jar target/floodlight.jar


    Using Eclipse:
    https://floodlight.atlassian.net/wiki/spaces/floodlightcontroller/pages/1343544/Installation+Guide


* Accessing the RESTful API:
  * Obs: You can access the implemented code in: /floodlight/src/main/java/net/floodlightcontroller/engine
  * Pre-condition: Floodlight is running.

http://{ip-address}:8080/engine/communication-descriptor/  ---> where ip-address identifies an interface of the machine (e.g. VM) that the controller is running.

   


* Functionalities:
  * Only the POST METHOD is implemented so far. 

An example of JSON data to be sent to the Controller:
```json
{
  "srcIPAddress": "10.0.0.1",
  "dstIPAddress": "10.0.0.2",
  "TransportProtocol": "QUIC",
  "srcPort": 2000,
  "dstPort": 80,
  "streams": [
    {
      "id": 1,
      "requiredBandwidth": 5,
      "bandwidthUnit": "Mbps"
    },
    {
      "id": 2,
      "requiredBandwidth": 3,
      "bandwidthUnit": "Mbps"
    }
  ],
  "key": 11111111111111222222222222222222222222233333333333333333333334444444444444444444444444
}
```
The message returned from the Controller must be the following: **Worked!** 




