
Sebastian Natalevich test NPAW
---------------------------------------------------------------------------------

Microservice project to answer to which host should go depending on Client, Plugin, Version
---------------------------------------------------------------------------------

It is a Spring-boot architecture using Maven.

The project is divided in different packages with different responsabilities.

plugin_clusters.controller
---------------------------------------------------------------------------------
PluginController --> Class where is defined the RestController. Point of access to the service

plugin_clusters.responseModel
---------------------------------------------------------------------------------
ToClusterResponse --> The response object of the service

plugin_clusters.model
---------------------------------------------------------------------------------
Client, Plugin, Version, Cluster --> POJO's, defined the model

plugin_clusters.exception
---------------------------------------------------------------------------------
NotFoundException --> Custom exception for when the parameters don't match

plugin_clusters.data
---------------------------------------------------------------------------------
DataManager --> Class created to load the data as a json. In charge to check if plucinClusters.json changed,
				allowing to make changes when the server is alive. Note: when a change it's done, the state 
				of the returned clusters are reseted. 
				Managing all data queries, and also the load balancer algorithm.
				The class was created as a singleton with the purpose of this excercise, in a real case
				using a DB a thread pool connection should be much appropiate aproach.

Assumptions
---------------------------------------------------------------------------------
** Instead to return empty when there is no ClientId with that clientId, or the plugin for that client
the service return an error with the message error
** When a there is no version defined as the requested for that Client and Plugin, the service returns 
an error
** For the load balancer algorithm, the service is only considering host with weight > 0 and weight <= 1,
if there is no host with weight between 0 and 1, and error will be returned by the service

Note
---------------------------------------------------------------------------------
To run the service, in the terminal java -jar plugin_clusters-0.0.1-SNAPSHOT.jar
The file pluginClusters.json must be at the same directory
An example of the call is:
localhost:8080/ToCluster?accountCode=clienteA&targetDevice=XBox&pluginVersion=3.3.1
The avaialble data is defined in pluginClusters.json