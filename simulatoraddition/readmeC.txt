Java Market Checkpoint C (Aardvarks)

- To start off there are now 6 classes in Check point C

- Customer Class: The class has several private variables such as arrival time, service time, etc..
The class has three constructors (default, one that has everything but the wait time, and the third takes any customers with a wait time in the driver)
The class also has several setter and getters

- Customer Creator Class: This class contains instances and methods that generate new customers such as the callFirstCustomer (generates a new customer with a random arrival time 
and service time) and includes many other methods that contribute to that such as (callNextCustomer, genArrivalTime, genServiceTimeWithPercent, etc...)
This class also has getters and setters 

- Linked List: Has a private nested class named Node with the first and last nodes
Includes constructor, is Empty method, size method, add method, remove method, and to string method.
Also has an arraylist method that returns customers in the linked list

- Queue Class: Queue that stores objects from Customer 
Includes methods such as enqueue, dequeue, is empty, need to dequeue (removes all customers if finish time is less then arrival time), size method, 
peek methods, and to ArrayList which returns an arraylist that has all the customers in the queue. 

- Self Check Out Class: Main method includes the parameters and has a queue called check out
Then the queue called checkout generates the first customer, enqueues them, and adds their wait time to a list.
Wait Average calculates the average wait time for all the customers 
get TurnAround calculates the total time the customer is in the system
Calculate Percent Slower changes percent value to the decimal value

- Java Market Driver: Main program
Has a 2 Nodes with value and next nodes
Main method asks user to enter minimum and maximum inter-arrival time, minimum and maximum service time, number of customers to serve, and a percentage value for the checkout process to be slower for self checkout.
Then makes sure that the customer input is valid
Then intilizes four queues, 3 for full checkout, and 1 for self checkout
Adds each created customer to either A, B, C queues in full or the self check out based on if its faster to do so because of the wait times
Has a calculate Percent Slower method which takes an integer and converts it into a double
Has a gen Arrival Time/Service Time which generate random times for the customers
Has a serve Customer method which serves the first customer in queue by using the remove method
and Satisfaction Calc which calculates the number of satisfied and dissatisfied customers based on their wait times.
If a customer's wait time is less then 5 minutes they are satisfied if they are higher then they are dissatisfied. 
