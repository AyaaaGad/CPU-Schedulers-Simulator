# CPU-Schedulers-Simulator
it's a java program to simulate a multi-level queue scheduler
it's a java program to simulate a multi-level queue scheduler, such that each queue should implement one of these algorithms:
1.First Come First Served (FCFS)
2.Non preemptive Shortest- Job  First (SJF) Scheduling
3.Round Robin (RR)
The processes of each queue will run based on the priority of the queue. Such that, the processes of the queue with highest priority will run first then the processes of the queue with low priority.
Program Input 
	No of queues
For each queue you need to receive the following parameters from the user:
	Priority (which may be 1, 2, or 3) 
•	E.g., 1 for min priority, 2 for mid priority, or 3 for max priority. Such that, the queues with the same priority can run sequential.
	Algorithm
•	E.g., FCFS, JSF, or RR. Such that, take from the user the required quantum in case of RR.
	Number of processes 
For Each Process you need to receive the following parameters from the user:
	Process Name
	Process Arrival Time 
	Process Burst Time
Program Output
	For each scheduler output the following:
	Processes execution order 
	Turnaround time for each process	
	Average Turnaround Time
-----------------------------------------------------------------------------------------------------------------
Sample Input:
Enter Number of Queues: 3
Q1, priority=2, alg=SJF, processes=4
Q2, priority=1, alg=RR, processes=5
Q3, priority=3, alg=FCFS, processes=3
Process of Q1	Arrival Time	Burst Time
Q1P1	0	8
Q1P2	2	2
Q1P3	3	5
Q1P4	5	1
Process of Q2	Arrival Time	Burst Time
Q2P1	3	5
Q2P2	4	2
Q2P3	4	4
Q2P4	5	2
Q2P5	6	3
Process of Q3	Arrival Time	Burst Time
Q3P1	0	5
Q3P2	2	2
Q3P3	3	4

Assume the quantum=2 and Context Switch overhead = 1
Note: the Context switch is computed with all algorithms not only RR
