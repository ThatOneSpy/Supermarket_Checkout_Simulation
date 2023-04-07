Java Market Checkpoint D (Aardvarks)

++ Updated Driver and Simulator Class to accept input for the initial number of full-service lines and the number of self-service lines is input as parameters to the program.
++ The program now end by suggesting additions or deletions of lines based on the results of the simulation to minimize wait time and time the lines were not in use.  
++ Program functions the same as the last checkpoint.

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

- Java Market Driver & Simulator Class
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

*****
EX Input and Results:

Input:
Welcome to the Java Market Checkout Simulator.
To get started, please enter some endpoints for ranges that will calculate interarrival time, service time for each customer, and number of customers to serve.
WARNING: Please enter only positive integers in order for the program to run correctly.

The following are recommended intervals and number to yield the best results.
As the range of both interarrival time and service time increases, the likelihood of increased minutes of no checkouts being used goes up.
This applies to expanding numbers of customers, where the more customers you serve, the likelihood of customer dissatisfication increases.
Recommended interval for interarrival and service times: 2-5
Recommended amount of customers to serve: 100
Recommended slow percentage for self-checkout customers: 20
Recommended amount of lanes for full service checkout (per 20 customers): 2
Recommended amount of lanes for self service checkout (per 20 customers): 2

Enter minimum interarrival time between customers (Must be at least zero):2
Enter maximum interarrival time between customers (Must be greater than zero):5
Enter minimum service time (Must be greater than zero):2
Enter maximum service time (Must be greater than zero):5
Enter number of customers to serve (Must be greater than zero):10
Percentage(%) slower for SELF (Must be greater than zero): 20
Enter number of full service checkout lanes (Must be greater than zero):
2
Enter number of self service checkout lanes (Must be greater than zero):
2

*****

Simulation Results:
Average full checkout wait: 0.6
Average self checkout wait: 0
Total time full checkouts were not in use: 5
Total time self checkouts were not in use: 0
Customer satisfaction: 10 satisfied (<5 minutes)  0 dissatisfied (>=5 minutes)

For full service checkouts,
Consider deleting one or more lanes in your next simulation to minimize the time of the checkout(s) not being used.
For self service checkouts,
Optimal amount of lanes present. No changes necessary.

Simulation complete.
*****

