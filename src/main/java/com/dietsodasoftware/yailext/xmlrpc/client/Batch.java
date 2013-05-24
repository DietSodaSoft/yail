package com.dietsodasoftware.yailext.xmlrpc.client;

import com.dietsodasoftware.yail.xmlrpc.client.YailClient;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 *A batch is a serial list of operations to perform in the order added.
 *
 *
 *
 * Created with IntelliJ IDEA.
 * User: wendel.schultz
 * Date: 5/23/13
 * Time: 5:26 PM
 */
public class Batch {

    public enum Status{
        NOT_STARTED, RUNNING, FINISHED, ABEND;
    }

    private final List<BatchedOperation> operations;
    private final List<BatchedOperation> completedOperations;

    private BatchedOperation preemptiveOperation = null;
    private Status status = Status.NOT_STARTED;
    private final AtomicBoolean halt = new AtomicBoolean(false);
    private final AtomicBoolean isExecuting = new AtomicBoolean(false);

    private Exception haltException = null;


    public Batch() {
        operations = new CopyOnWriteArrayList<BatchedOperation>();
        completedOperations = new CopyOnWriteArrayList<BatchedOperation>();
    }

    public Batch addOperationToBatch(BatchedOperation operation){
        operations.add(operation);
        return this;
    }

    public Batch injectImmediateOperationIntoBatch(BatchedOperation operation){
        if(preemptiveOperation != null){
            throw new IllegalStateException("Only one operation can preempt");
        }

        preemptiveOperation = operation;
        return this;
    }

    public Batch halt(){
        halt.set(true);
        return this;
    }

    public Batch haltWithException(Exception e){
        halt();
        haltException = e;

        return this;
    }

    public boolean didAbend(){
        return status == Status.ABEND;
    }

    public Status getStatus(){
        return status;
    }

    public Exception getHaltException(){
        return haltException;
    }

    public List<BatchedOperation> getCompletedOperations(){
        return Collections.unmodifiableList(completedOperations);
    }


    public void execute(YailClient client){
        if(isExecuting.getAndSet(true)){
            throw new RuntimeException("Cannot re-execute a batch while it is processing the batch");
        }

        this.status = Status.RUNNING;

        // re-read the iterator to see if new operations came in during execution
        while(operations.iterator().hasNext() || preemptiveOperation != null){

            while(preemptiveOperation != null){
                if(halt.get()){
                    this.status = Status.ABEND;
                    return;
                }

                final BatchedOperation preemptiveOperation = this.preemptiveOperation;
                this.preemptiveOperation = null;

                performOperation(client, preemptiveOperation);
            }

            final Iterator<BatchedOperation> iterator = operations.iterator();
            operations.clear();

            while(iterator.hasNext()) {

                while(preemptiveOperation != null){
                    if(halt.get()){
                        this.status = Status.ABEND;
                        return;
                    }

                    final BatchedOperation preemptiveOperation = this.preemptiveOperation;
                    this.preemptiveOperation = null;

                    performOperation(client, preemptiveOperation);
                }

                if(halt.get()){
                    this.status = Status.ABEND;
                    return;
                }

                final BatchedOperation operation = iterator.next();
                performOperation(client, operation);

            }

        }

        this.status = Status.FINISHED;
    }

    private void performOperation(YailClient client, BatchedOperation operation){
        boolean success = true;
        Object result = null;
        try {
            result = client.call(operation.getRpcOperation());
        } catch (Exception e) {
            success = false;
            operation.getCompletion().onError(this, e);
        } finally {
            completedOperations.add(operation);
        }

        if(success){
            try {
                operation.getCompletion().onSuccess(this, result);
            } catch (Exception e){
                // WTD? operation completed successfully but the callback failed ... should the batch fail?
                // right now, I say no
            }
        }

    }

}
