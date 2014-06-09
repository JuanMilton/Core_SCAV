/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package firstone.core.negocio;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 *
 * @author Milton
 */
public class SchedulerNegocio extends Thread{
    
    int NRO_QUEUE = 3;
    private Queue<Task>[] queues;
    
    SchedulerEvent se;
    
    public SchedulerNegocio(SchedulerEvent se)
    {
        queues = new ConcurrentLinkedQueue[NRO_QUEUE];
        queues[0] = new ConcurrentLinkedQueue<>();
        queues[1] = new ConcurrentLinkedQueue<>();
        queues[2] = new ConcurrentLinkedQueue<>();
        this.se = se;
    }
    
    
    @Override
    public void run()
    {
        int cont = 0;
        int nroQueu = queues.length -1;
        while ( true )
        {
            Task cad = queues[nroQueu].poll();
            if (cad != null)
            {
                cont++;
                se.doTask(cad.getContrato(),cad.getKey());
                if (cont > (nroQueu))
                {
                    cont = 0;
                    nroQueu--;
                    if (nroQueu == -1)
                        nroQueu = queues.length - 1;
                }
            }else
            {
                cont = 0;
                nroQueu--;
                if (nroQueu == -1)
                    nroQueu = queues.length - 1;
            }
        }
    }

    public void addHighPriorityTask(Task task)
    {
        queues[2].add(task);
    }
    
    public void addMediumPriorityTask(Task task)
    {
        queues[1].add(task);
    }
    
    public void addLowPriorityTask(Task task)
    {
        queues[0].add(task);
    }
    
}


