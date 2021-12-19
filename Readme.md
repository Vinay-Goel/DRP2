# Distributed Resource Planner

## Problem Statement
Generate day wise distribution plan to distribute material from Source to destination
depending on the Customer Demand at the lowest possible Total Freight Cost

## Definitions
* DRP - Distribution resource planning – How much of stock has to be moved from any source
to destination considering the Lead time and Freight Cost
* Lead Time – Transportation time in days on any route (Source to Destination)
* Freight Cost – Cost in Rupees to transport material from source to destination
e.g It takes 2 days to ship material from Factory1 to Warehouse1 with Rs. 2000 as
transportation cost
* Total Freight Cost – Summation of freight cost from first point of movement (Source) to the
last point of movement (Destination)
e.g Factory1 to Warehouse1 – Rs.1000
 Warehouse1 to Depot1 – Rs.500
 Depot1 to Customer1 – Rs.300
So the total freight cost in this example is (1000+500+300) Rs.1800
* Bill of distribution (BOD) – A list of all possible routes where material can be transported
within the network
* On Hand Inventory – This is the quantity of each part which is already available. It’s not
necessary that there will be some stock of each part. Whatever inventory is available for any
part will be called as On Hand Inventory
* Inventory Net off – On hand inventory at source needs to be net off with the required quantity
form the downstream location to identify the qty to be dispatched
* Customer Demand – Quantity of finished product required by each customer

## Implementation Details
Implemented in Java (Maven), with Dagger2 dependency injection. Using Univocity-parser for parsing CSV input files.
[Check pom.xml for additional open-source dependencies used] 

## How to Run
Keep the input files, bill_of_distribution.csv, on_hand_inventory.csv, customer_demand.csv, in the same working directory.

* customer_demand.csv can have any number of day headers. Each day header must start with prefix "DAY".
* For simplicity we will remove the prefix and model day as a numeric field.

### DRPScript
This script will invoke the DRP Collect Input Executor. It will collect input from same directory & execute the planner.

### DRPGenerateInputScript
This script will invoke the DRP Generate Input Executor. This will generate random data and execute the planner.

### Planner
DRPExecutor uses the Resource Planner implementation (currently only Single Node Shortest Path/Dijkstra's algo) to get shortest path for each customer day wise demand.
* After the shortest path is identified, inventory & shortest path stores are updated.
* When fetching inventory from a source for a day already used, we do a look-ahead in inventory store to subtract the inventory already used by other demand.
* In the end planner outputs two files one for shortest path of each demand & one for inventory movement for each day.

### Using JAR
Additionally one can create a JAR and execute it with files alongside JAR i.e. in same folder, command:
*  java -jar DRP2.jar

## Output
Output directory & Logs are generated appended with timestamp in the execution folder. Output will have two files:

### shortest_path.txt
e.g. - "Demand: [Demand(location=Customer2, day=1, demand=3000)], ShortestPath: [NextInPath(source=Warehouse1, freightCost=9500, leadTime=8, inventoryCollected=3000, fromEdge=NextInPath(source=Depot2, freightCost=2500, leadTime=1, inventoryCollected=1500, fromEdge=null))]"

Here are two attributes,
1. Demand: [Demand(location=Customer2, day=1, demand=3000)]
   * It represents the demand value directly from customer_demand.csv file. For Customer Day wise demand we create the above object.

2. ShortestPath: [NextInPath(source=Warehouse1, freightCost=9500, leadTime=8, inventoryCollected=3000, fromEdge=NextInPath(...))]
   * It represents the source of inventory for the corresponding day wise demand for customer.
   * It is a linked list from Factory to Customer. "fromEdge" being link to next edge. 
   * Each "NextInPath" provides the source, total freight cost, total lead time, total inventory collected TILL THAT "NextInPath".

### dispatched_inventory.txt
e.g. - "From: [Warehouse1], to: [Depot2], day: [-8] - inventory dispatched: [3500]"
* It represents the total inventory dispatched from "Warehouse1" to "Deposit2" on "8+1=9" days prior to "Day1" to meet any demand which uses this in its shortest path.
* Hence, this provides the overall plan one needs to execute to plan resources for whole of the demand in "customer_demand.txt" input.

