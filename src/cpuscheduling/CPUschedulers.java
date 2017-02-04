package cpuscheduling;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Formatter;

public class CPUschedulers {

    void readFile(String Path, ArrayList<Process> readyQueue) throws FileNotFoundException, IOException {
        FileReader fr = new FileReader(Path);
        BufferedReader br = new BufferedReader(fr);
        int numOfQueue;
        numOfQueue = Integer.parseInt(br.readLine());
        ArrayList<Queue> Q = new ArrayList<Queue>();
        for (int i = 0; i < numOfQueue; i++) {
            String data = br.readLine();
            String[] arr = data.split(" ");
            Queue cur = new Queue(Integer.parseInt(arr[0]), arr[1], Integer.parseInt(arr[2]));
            Q.add(cur);
        }

        for (int j = 0; j < numOfQueue; j++) {

            for (int k = 0; k < Q.get(j).getNumOfProcess(); k++) {

                String data = br.readLine();
                String[] arr = data.split(" ");
                Process p = new Process(arr[0], Integer.parseInt(arr[1]), Integer.parseInt(arr[2]), Q.get(j).getAlgo(), Q.get(j).getPriority());
                p.setRemaningTime(p.getBurstTime());
                p.setNewarrivalTime(p.getArrivalTime());
                readyQueue.add(p);

            }
        }

    }

    void get_arrived_processes( ArrayList<Process> readyQueue, ArrayList<Process> arrived_processes, int time) {
        for (int i = 0; i < readyQueue.size(); i++) {
            if (readyQueue.get(i).getArrivalTime() <= time) {
                arrived_processes.add(readyQueue.get(i));
            }
        }
    }

    void get_max_priority(ArrayList<Process> arrived_processes, ArrayList<Process> max) {
       
        int max_priority = arrived_processes.get(0).getPriority();

        for (int i = 0; i < arrived_processes.size(); i++) // to get max priority 
        {

            for (int j = i + 1; j < arrived_processes.size(); j++) {
                if (arrived_processes.get(j).getPriority() > max_priority) {
                    max_priority = arrived_processes.get(j).getPriority();
                }
            }
        }
        
        for (int i = 0; i < arrived_processes.size(); i++) // to add max priority 
        {
            if (arrived_processes.get(i).getPriority()==max_priority) 
            {
                max.add(arrived_processes.get(i));
            }
        }
        
    }

    Process get_first_arrives(ArrayList<Process> Max_priority )
    {
       
        Process cur_process = new Process() ;
        int min_arrival_time = Max_priority.get(0).getNewarrivalTime();
        cur_process = Max_priority.get(0);

        for (int i=0 ; i<Max_priority.size() ; i++)
        {
            for (int j=0 ; j<Max_priority.size() ; j++)
            {
                if (Max_priority.get(j).getNewarrivalTime() < min_arrival_time)
                {   
                    min_arrival_time = Max_priority.get(j).getNewarrivalTime();
                    cur_process = Max_priority.get(j) ;
                 
                }
            }
        }
    
        return cur_process ;
         
    }
    
    Process get_min_burst_time (ArrayList<Process> Max_priority)
    {
        
        Process cur_process = new Process() ;
        int min_burst_time = Max_priority.get(0).getBurstTime();
        
        cur_process = Max_priority.get(0);
        
        for (int i=0 ; i<Max_priority.size() ; i++)
        {
            for (int j=0 ; j<Max_priority.size() ; j++)
            {
                if (Max_priority.get(j).getBurstTime()< min_burst_time)
                {   
                    min_burst_time = Max_priority.get(j).getBurstTime();
                    cur_process = Max_priority.get(j) ;
                }
            }
        }
               
       return cur_process ;
        
    }
    
    int search_by_name (Process a , ArrayList<Process> readyQueue)
    {
        int indx = -1 ;
        
        for (int i=0 ; i<readyQueue.size() ; i++)
        {
            if (a.getProcessName().equals(readyQueue.get(i).getProcessName()))
            {
                indx = i ;
                break;
            }
        }
        return indx ;
    }
    
    void Write_file (String path , ArrayList <Process> done ,  ArrayList <Process> unique)
    {
        Formatter out = new Formatter () ;
        
        try {
            out = new Formatter(path);
        } catch (Exception e) {
            System.out.println("Can not find File ");
        }
        
        out.format("Processes execution order : ");
        out.format("%n");
        for (int i=0 ; i<done.size() ; i++)
        {
            out.format("%s", done.get(i).getProcessName() ); 
            out.format("%n");
        }
       
        out.format("----------------------");
        out.format("%n");
        out.format("%n");
        out.format("Trun around time : ");
        out.format("%n");
        double total = 0 ;
        for (int i=0 ; i<unique.size() ; i++)
        {
            out.format("%s", unique.get(i).getProcessName() + "  " + unique.get(i).getTurnAroundTime() ); 
            total +=unique.get(i).getTurnAroundTime();
            out.format("%n");
        }
        
        out.format("----------------------");
        out.format("%n");
        
        out.format("Avg turnaround time = " + total/unique.size());
       
        out.flush(); // 34an yktb 3al file
        out.close();
    }
    
    
    void Excute(ArrayList<Process> readyQueue) {
       
        int cur_time = 0;
        int Quantum = 2 ; // 34an el RR
        ArrayList<Process> arrived_processes = new ArrayList<Process>(); // kol eli already weslo 3nd el time dah
        ArrayList<Process> done = new ArrayList<Process>(); // kol eli et3mlohom excution 
        ArrayList<Process> unique = new ArrayList<Process>();
        int c =0 ;
                  
        while ( !readyQueue.isEmpty())
        {
            
            get_arrived_processes(readyQueue, arrived_processes, cur_time);
            ArrayList<Process> Max_priority = new ArrayList<>();
            get_max_priority(arrived_processes, Max_priority);
            Process cur_process = new Process ();
            
            
            if (Max_priority.size() == 1) // y3ny mfe4 etnen aw aktr lehom nfs el arrival time we nfs el probability
            {  
              cur_process = Max_priority.get(0); 
               
            } 
            
            else // feeh kza 7ad 3ndo el max priority " y3ny more than one mn nfs el algo/queue gom -> han48l anhy el awel ?? " ... hanro7 ngeb eli geh bdry           
            {   
                if ( Max_priority.get(0).getAlg().equals("SJF"))
                { 
                    cur_process = get_min_burst_time(Max_priority);
                }
                
                else 
                {
                    cur_process = get_first_arrives(Max_priority);
                }
                
            }
                 
                System.out.println("cur process = " + cur_process.getProcessName());
                
                if (cur_process.getAlg().equals("FCFS"))
                {
                    cur_time += cur_process.getBurstTime();
                    cur_process.setRemaningTime(0);
                } 
                
                else if (cur_process.getAlg().equals("SJF"))
                {

                    cur_time += cur_process.getBurstTime(); // +1 34an el context switch 
                    cur_process.setRemaningTime(0);
                } 
              
                else // RR
                { 
                    if (cur_process.getRemaningTime() > Quantum)
                    {
                        cur_time += Quantum ;
                        cur_process.setRemaningTime(cur_process.getRemaningTime()- Quantum);
                        cur_process.setNewarrivalTime(cur_time);
                        done.add(cur_process); // just added to the excuted orders 

                       
                    }
                    
                    else 
                    {
                        cur_time += cur_process.getRemaningTime();
                        cur_process.setRemaningTime(0);
                        
                    }
                    
                }

               if (cur_process.getRemaningTime()==0) 
                {   
                    cur_process.setEndTime(cur_time);
                    cur_process.setTurnAroundTime(cur_time - cur_process.getArrivalTime());
                    done.add(cur_process);
                    unique.add(cur_process);
                    int indx = search_by_name( cur_process , readyQueue);
                    readyQueue.remove(indx);
                    
                }
                
                cur_time++;
               
                arrived_processes.clear();
                

        }
        
        Write_file ("output.txt" , done , unique );
     
    }

   
    public static void main(String[] args) throws IOException 
    {

        ArrayList<Process> readyQueue = new ArrayList<Process>();
        CPUschedulers c = new CPUschedulers();
        c.readFile("input.txt", readyQueue);
        c.Excute(readyQueue);
        
    }

}
