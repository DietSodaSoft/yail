package com.dietsodasoftware.yailext.xmlrpc.client;

import com.dietsodasoftware.yail.xmlrpc.service.InfusionsoftXmlRpcServiceOperation;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;

/**
 * Created with IntelliJ IDEA.
 * User: wendel.schultz
 * Date: 5/23/13
 * Time: 11:01 PM
 */
public class RetryingBatchedOperationTest {

    @Mock
    private InfusionsoftXmlRpcServiceOperation request;

    @Mock
    private BatchedOperation.Completion completion;

    @Mock
    private Batch batch;

    private RetryingBatchedOperation operation;
    private final int maxAttempts = 5;

    @Before
    public void before(){

        MockitoAnnotations.initMocks(this);

        operation = new RetryingBatchedOperation(request, completion, maxAttempts);

    }

    @Test
    public void testSuccessDelegatesToRealHandler(){

        operation.getCompletion().onSuccess(batch, 11);

        verify(completion).onSuccess(eq(batch), eq(11));

    }

    @Test
    public void testFailureInsertsPreemptiveBatchedOperation(){
        final Exception dud = new RuntimeException("6");

        operation.getCompletion().onError(batch, new RuntimeException("1"));
        operation.getCompletion().onError(batch, new RuntimeException("2"));
        operation.getCompletion().onError(batch, new RuntimeException("3"));
        operation.getCompletion().onError(batch, new RuntimeException("4"));
        operation.getCompletion().onError(batch, new RuntimeException("5"));

        verifyZeroInteractions(completion);

        operation.getCompletion().onError(batch, dud);

        verify(batch, times(5)).injectImmediateOperationIntoBatch(eq(operation));
        verify(completion).onError(eq(batch), eq(dud));

    }

}
