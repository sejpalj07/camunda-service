# Camunda - Service

###### A spring boot starter that helps to get started with an embedded camunda engine to deploy models and decisions.

## Building the application.

Use maven to build the application locally.

~~~
 mvn clean install -U
 ~~~

## Running the application.

Add ```.bpmn``` and ```.dmn``` files to the ```src/main/resource``` directory and the spring boot application would be
able to deploy those business processes and decisions into the embedded application.

## Using the application

Open any web browser and head over to ```localhost:8080``` and the camunda dashboard should come up.
