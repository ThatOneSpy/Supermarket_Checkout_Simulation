#Supermarket Checkout Line Simulator

Purpose: Models full-service and self-checkout lanes to compare wait times, idle time, and customer satisfaction across different lane configurations and arrival/service-time assumptions.

Inputs: Minimum/maximum interarrival times, minimum/maximum service times, number of customers to serve, slowdown percentage for self-checkout, and the counts of full-service and self-service lanes.

Core flow: Randomly generate customers, route them to the shortest/fastest queue, serve them in order, and track per-lane and overall metrics (wait times, turnaround, idle time).

Outputs: Average wait per lane type, total idle time per lane type, and a satisfaction summary (customers under/over a 5-minute wait). The simulator also suggests adding or removing lanes based on idle time and queue performance.

Data structures: Custom Queue and LinkedList for customer ordering; Customer and CustomerCreator encapsulate timing attributes and random generation helpers; Simulator drives the per-tick event logic; JavaMarketDriver handles user input/validation and kicks off runs.

Database option (Checkpoint E): Optional MySQL/phpMyAdmin logging via SupermarketDB writes input parameters, generated customers, and simulation results (timestamped) to three tables. Requires importing supermarket.sql, creating DBUser with the specified credentials, and adding the MySQL JDBC driver to the classpath.

Typical use: Enter recommended ranges (e.g., interarrival 2–5, service 2–5, ~100 customers, ~20% self-checkout slowdown, lanes scaled to demand), run, then adjust lane counts per the simulator’s suggestions to minimize wait and idle time.
