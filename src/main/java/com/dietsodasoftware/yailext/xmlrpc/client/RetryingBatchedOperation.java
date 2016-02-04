package com.dietsodasoftware.yailext.xmlrpc.client;

import com.dietsodasoftware.yail.xmlrpc.service.InfusionsoftXmlRpcServiceOperation;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created with IntelliJ IDEA.
 * User: wendel.schultz
 * Date: 5/23/13
 * Time: 10:40 PM
 */
public class RetryingBatchedOperation<RT, ST extends InfusionsoftXmlRpcServiceOperation<RT>> extends BatchedOperation<RT, ST> {
    private final int maxAttempts;
    private final AtomicInteger attempted = new AtomicInteger(0);
    public RetryingBatchedOperation(ST request, Completion<RT> completion, int maxAttempts) {
        super(request, completion);

        this.maxAttempts = maxAttempts;
    }

    @Override
    public Completion<RT> getCompletion() {
        final Completion<RT> delegate = super.getCompletion();

        return new Completion<RT>() {
            @Override
            public void onSuccess(Batch batch, RT response) {
                delegate.onSuccess(batch, response);
            }

            @Override
            public void onError(Batch batch, Exception e) {
                if(attempted.incrementAndGet() > maxAttempts){
                    delegate.onError(batch, e);
                } else {
                    batch.injectImmediateOperationIntoBatch(RetryingBatchedOperation.this);
                }
            }
        };
    }
}
