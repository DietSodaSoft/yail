package com.dietsodasoftware.yailext.xmlrpc.client;

import com.dietsodasoftware.yail.xmlrpc.service.InfusionsoftXmlRpcServiceOperation;

/**
 * A batched operation is an InfusionsoftXmlRpcServiceOperation and hanlders for both success and failure.
 *
 * A batch will execute operations serially, and upon completion will invoke completion.onSuccess().  In the event the operation
 * throws an exception during the course of client.call(), completion.onError will be invoked and onSuccess will not.
 *
 * Created with IntelliJ IDEA.
 * User: wendel.schultz
 * Date: 5/23/13
 * Time: 5:27 PM
 */
public class BatchedOperation<RT, ST extends InfusionsoftXmlRpcServiceOperation<RT>> {

    public BatchedOperation(ST request, Completion<RT> completion) {
        this.completion = completion;
        this.request = request;
    }

    public interface Completion<ResponseType>{

        public void onSuccess(Batch batch, ResponseType response);

        public void onError(Batch batch, Exception e);

    }

    private final Completion<RT> completion;
    private final ST request;

    public ST getRpcOperation(){
        return request;
    }

    public Completion<RT> getCompletion(){
        return completion;
    }
}
