package com.dietsodasoftware.yailext.xmlrpc.client;

import com.dietsodasoftware.yail.xmlrpc.client.YailClient;
import com.dietsodasoftware.yail.xmlrpc.service.InfusionsoftXmlRpcException;
import com.dietsodasoftware.yail.xmlrpc.service.InfusionsoftXmlRpcServiceOperation;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

/**
 * Created with IntelliJ IDEA.
 * User: wendel.schultz
 * Date: 5/23/13
 * Time: 6:23 PM
 */
public class BatchTest {

    @Mock
    private YailClient client;

    @Mock
    BatchedOperation.Completion completion1 = mock(BatchedOperation.Completion.class);
    @Mock
    InfusionsoftXmlRpcServiceOperation request1 = mock(InfusionsoftXmlRpcServiceOperation.class);

    @Mock
    BatchedOperation.Completion completion2 = mock(BatchedOperation.Completion.class);
    @Mock
    InfusionsoftXmlRpcServiceOperation request2 = mock(InfusionsoftXmlRpcServiceOperation.class);

    @Mock
    BatchedOperation.Completion completion3 = mock(BatchedOperation.Completion.class);
    @Mock
    InfusionsoftXmlRpcServiceOperation request3 = mock(InfusionsoftXmlRpcServiceOperation.class);

    Batch batch;

    @Before
    public void before() throws InfusionsoftXmlRpcException {

        batch = new Batch();

        MockitoAnnotations.initMocks(this);

        when(client.call(eq(request1))).thenReturn(11);
        when(client.call(eq(request2))).thenReturn(22);
        when(client.call(eq(request3))).thenReturn(33);

    }

    @Test
    public void testBatchWithNoOperationsAddedDuringExecution() throws InfusionsoftXmlRpcException {

        batch.addOperationToBatch(new BatchedOperation(request1, completion1));
        batch.addOperationToBatch(new BatchedOperation(request2, completion2));
        batch.addOperationToBatch(new BatchedOperation(request3, completion3));

        // now do work
        batch.execute(client);

        final InOrder inOrder = inOrder(completion1, completion2, completion3);
        inOrder.verify(completion1).onSuccess(eq(batch), eq(11));
        inOrder.verify(completion2).onSuccess(eq(batch), eq(22));
        inOrder.verify(completion3).onSuccess(eq(batch), eq(33));

        assertFalse("Batch should NOT have abended", batch.didAbend());
    }

    @Test
    public void testBatchWithRegularOperationsAddedDuringExecution() throws InfusionsoftXmlRpcException {

        final BatchedOperation.Completion completion4 = new BatchedOperation.Completion(){

            @Override
            public void onSuccess(Batch batch, Object response) {
                batch.addOperationToBatch(new BatchedOperation(request3, completion3));
                assertEquals(44, response);
            }

            @Override
            public void onError(Batch batch, Exception e) {
               throw new IllegalStateException("Should't fail");
            }
        };
        final InfusionsoftXmlRpcServiceOperation request4 = mock(InfusionsoftXmlRpcServiceOperation.class);
        when(client.call(eq(request4))).thenReturn(44);

        batch.addOperationToBatch(new BatchedOperation(request1, completion1));
        batch.addOperationToBatch(new BatchedOperation(request4, completion4));
        batch.addOperationToBatch(new BatchedOperation(request2, completion2));

        batch.execute(client);

        final InOrder inOrder = inOrder(completion1, completion2, completion3);
        inOrder.verify(completion1).onSuccess(eq(batch), eq(11));
        inOrder.verify(completion2).onSuccess(eq(batch), eq(22));
        inOrder.verify(completion3).onSuccess(eq(batch), eq(33));

        assertFalse("Batch should NOT have abended", batch.didAbend());
    }

    @Test
    public void testBatchWithPreemptiveOperationsAddedDuringExecution() throws InfusionsoftXmlRpcException {

        final BatchedOperation.Completion completion4 = new BatchedOperation.Completion(){

            @Override
            public void onSuccess(Batch batch, Object response) {
                batch.injectImmediateOperationIntoBatch(new BatchedOperation(request3, completion3));
                assertEquals(44, response);
            }

            @Override
            public void onError(Batch batch, Exception e) {
                throw new IllegalStateException("Should't fail");
            }
        };
        final InfusionsoftXmlRpcServiceOperation request4 = mock(InfusionsoftXmlRpcServiceOperation.class);
        when(client.call(eq(request4))).thenReturn(44);

        batch.addOperationToBatch(new BatchedOperation(request1, completion1));
        batch.addOperationToBatch(new BatchedOperation(request4, completion4));
        batch.addOperationToBatch(new BatchedOperation(request2, completion2));

        batch.execute(client);

        final InOrder inOrder = inOrder(completion1, completion2, completion3);
        inOrder.verify(completion1).onSuccess(eq(batch), eq(11));
        inOrder.verify(completion3).onSuccess(eq(batch), eq(33));
        inOrder.verify(completion2).onSuccess(eq(batch), eq(22));

        assertFalse("Batch should NOT have abended", batch.didAbend());
    }

    @Test
    public void testBatchWitPreemptiveOperationsAddedAtEndOfExecution() throws InfusionsoftXmlRpcException {

        final BatchedOperation.Completion completion4 = new BatchedOperation.Completion(){

            @Override
            public void onSuccess(Batch batch, Object response) {
                batch.injectImmediateOperationIntoBatch(new BatchedOperation(request3, completion3));
                assertEquals(44, response);
            }

            @Override
            public void onError(Batch batch, Exception e) {
                throw new IllegalStateException("Should't fail");
            }
        };
        final InfusionsoftXmlRpcServiceOperation request4 = mock(InfusionsoftXmlRpcServiceOperation.class);
        when(client.call(eq(request4))).thenReturn(44);

        batch.addOperationToBatch(new BatchedOperation(request1, completion1));
        batch.addOperationToBatch(new BatchedOperation(request2, completion2));
        batch.addOperationToBatch(new BatchedOperation(request4, completion4));

        batch.execute(client);

        final InOrder inOrder = inOrder(completion1, completion2, completion3);
        inOrder.verify(completion1).onSuccess(eq(batch), eq(11));
        inOrder.verify(completion2).onSuccess(eq(batch), eq(22));
        inOrder.verify(completion3).onSuccess(eq(batch), eq(33));

        assertFalse("Batch should NOT have abended", batch.didAbend());
    }

    @Test
    public void testBatchWithRegularOperationsHaltedPrematurelyDuringExecution() throws InfusionsoftXmlRpcException {

        final BatchedOperation.Completion completion4 = new BatchedOperation.Completion(){

            @Override
            public void onSuccess(Batch batch, Object response) {
                batch.halt();
                assertEquals(44, response);
            }

            @Override
            public void onError(Batch batch, Exception e) {
                throw new IllegalStateException("Should't fail");
            }
        };
        final InfusionsoftXmlRpcServiceOperation request4 = mock(InfusionsoftXmlRpcServiceOperation.class);
        when(client.call(eq(request4))).thenReturn(44);

        final BatchedOperation first = new BatchedOperation(request1, completion1);
        final BatchedOperation second = new BatchedOperation(request2, completion2);
        final BatchedOperation third = new BatchedOperation(request3, completion3);
        final BatchedOperation halt = new BatchedOperation(request4, completion4);
        batch.addOperationToBatch(first);
        batch.addOperationToBatch(second);
        batch.addOperationToBatch(halt);
        batch.addOperationToBatch(third);

        batch.execute(client);

        final InOrder inOrder = inOrder(completion1, completion2);
        inOrder.verify(completion1).onSuccess(eq(batch), eq(11));
        inOrder.verify(completion2).onSuccess(eq(batch), eq(22));
        verifyZeroInteractions(completion3);

                assertTrue(batch.getCompletedOperations().contains(first));
        assertTrue(batch.getCompletedOperations().contains(second));
        assertTrue(batch.getCompletedOperations().contains(halt));
        assertFalse(batch.getCompletedOperations().contains(third));
        assertEquals(3, batch.getCompletedOperations().size());
        assertTrue("Batch should have abended", batch.didAbend());
    }

    @Test
    public void testBatchWithPreemptiveOperationsHaltedPrematurelyDuringExecution() throws InfusionsoftXmlRpcException {

        final BatchedOperation first = new BatchedOperation(request1, completion1);
        final BatchedOperation second = new BatchedOperation(request2, completion2);
        final BatchedOperation third = new BatchedOperation(request3, completion3);

        final InfusionsoftXmlRpcServiceOperation request4 = mock(InfusionsoftXmlRpcServiceOperation.class);
        final BatchedOperation.Completion completion4 = new BatchedOperation.Completion(){

            @Override
            public void onSuccess(Batch batch, Object response) {
                batch.injectImmediateOperationIntoBatch(third);
                batch.halt();
                assertEquals(44, response);
            }

            @Override
            public void onError(Batch batch, Exception e) {
                throw new IllegalStateException("Should't fail");
            }
        };
        when(client.call(eq(request4))).thenReturn(44);

        final BatchedOperation halt = new BatchedOperation(request4, completion4);

        batch.addOperationToBatch(first);
        batch.addOperationToBatch(halt);
        batch.addOperationToBatch(second);

        batch.execute(client);

        final InOrder inOrder = inOrder(completion1);
        inOrder.verify(completion1).onSuccess(eq(batch), eq(11));
        verifyZeroInteractions(completion2);
        verifyZeroInteractions(completion3);


        assertTrue(batch.getCompletedOperations().contains(first));
        assertFalse(batch.getCompletedOperations().contains(second));
        assertTrue(batch.getCompletedOperations().contains(halt));
        assertFalse(batch.getCompletedOperations().contains(third));
        assertEquals(2, batch.getCompletedOperations().size());

        assertTrue("Batch should have abended", batch.didAbend());
    }

    @Test
    public void testBatchWithRegularOperationsAddedDuringExecutionHaltedAfterLastOperationCompleted() throws InfusionsoftXmlRpcException {

        final BatchedOperation second = new BatchedOperation(request2, completion2);
        final BatchedOperation third = new BatchedOperation(request3, completion3);

        final BatchedOperation.Completion completion1 = spy(new BatchedOperation.Completion() {
            @Override
            public void onSuccess(Batch batch, Object response) {
                batch.injectImmediateOperationIntoBatch(second);
            }

            @Override
            public void onError(Batch batch, Exception e) {
                throw new IllegalArgumentException("Shouldn't error out");
            }
        });

        final InfusionsoftXmlRpcServiceOperation request4 = mock(InfusionsoftXmlRpcServiceOperation.class);
        final BatchedOperation.Completion completion4 = new BatchedOperation.Completion(){

            @Override
            public void onSuccess(Batch batch, Object response) {
                batch.halt();
                assertEquals(44, response);
            }

            @Override
            public void onError(Batch batch, Exception e) {
                throw new IllegalStateException("Should't fail");
            }
        };
        when(client.call(eq(request4))).thenReturn(44);

        final BatchedOperation first = new BatchedOperation(request1, completion1);
        final BatchedOperation halt = new BatchedOperation(request4, completion4);

        batch.addOperationToBatch(first);
        batch.addOperationToBatch(third);
        batch.addOperationToBatch(halt);

        batch.execute(client);

        final InOrder inOrder = inOrder(completion1, completion2, completion3);
        inOrder.verify(completion1).onSuccess(eq(batch), eq(11));
        inOrder.verify(completion2).onSuccess(eq(batch), eq(22));
        inOrder.verify(completion3).onSuccess(eq(batch), eq(33));

        assertTrue(batch.getCompletedOperations().contains(first));
        assertTrue(batch.getCompletedOperations().contains(second));
        assertTrue(batch.getCompletedOperations().contains(third));
        assertTrue(batch.getCompletedOperations().contains(halt));
        assertEquals(4, batch.getCompletedOperations().size());

        assertFalse("Batch should NOT have abended", batch.didAbend());
    }

    @Test
    public void testBatchWithLastOperationInsertinhMultiplePreemptiveOperations() throws InfusionsoftXmlRpcException {

        final BatchedOperation third = new BatchedOperation(request3, completion3);

        final BatchedOperation.Completion completion2 = spy(new BatchedOperation.Completion() {
            @Override
            public void onSuccess(Batch batch, Object response) {
                batch.injectImmediateOperationIntoBatch(third);
            }

            @Override
            public void onError(Batch batch, Exception e) {
                throw new IllegalArgumentException("Shouldn't error out");
            }
        });


        final BatchedOperation second = new BatchedOperation(request2, completion2);
        final BatchedOperation.Completion completion1 = spy(new BatchedOperation.Completion() {
            @Override
            public void onSuccess(Batch batch, Object response) {
                batch.injectImmediateOperationIntoBatch(second);
            }

            @Override
            public void onError(Batch batch, Exception e) {
                throw new IllegalArgumentException("Shouldn't error out");
            }
        });

        final BatchedOperation first = new BatchedOperation(request1, completion1);

        batch.addOperationToBatch(first);

        batch.execute(client);

        final InOrder inOrder = inOrder(completion1, completion2, completion3);
        inOrder.verify(completion1).onSuccess(eq(batch), eq(11));
        inOrder.verify(completion2).onSuccess(eq(batch), eq(22));
        inOrder.verify(completion3).onSuccess(eq(batch), eq(33));

        assertTrue(batch.getCompletedOperations().contains(first));
        assertTrue(batch.getCompletedOperations().contains(second));
        assertTrue(batch.getCompletedOperations().contains(third));
        assertEquals(3, batch.getCompletedOperations().size());

        assertFalse("Batch should NOT have abended", batch.didAbend());
    }

    @Test
    public void testBatchWithOnlyMultiplePreemptiveOperations(){


        final BatchedOperation third = new BatchedOperation(request3, completion3);

        final BatchedOperation.Completion completion2 = spy(new BatchedOperation.Completion() {
            @Override
            public void onSuccess(Batch batch, Object response) {
                batch.injectImmediateOperationIntoBatch(third);
            }

            @Override
            public void onError(Batch batch, Exception e) {
                throw new IllegalArgumentException("Shouldn't error out");
            }
        });


        final BatchedOperation second = new BatchedOperation(request2, completion2);
        final BatchedOperation.Completion completion1 = spy(new BatchedOperation.Completion() {
            @Override
            public void onSuccess(Batch batch, Object response) {
                batch.injectImmediateOperationIntoBatch(second);
            }

            @Override
            public void onError(Batch batch, Exception e) {
                throw new IllegalArgumentException("Shouldn't error out");
            }
        });

        final BatchedOperation first = new BatchedOperation(request1, completion1);

        batch.injectImmediateOperationIntoBatch(first);

        batch.execute(client);

        final InOrder inOrder = inOrder(completion1, completion2, completion3);
        inOrder.verify(completion1).onSuccess(eq(batch), eq(11));
        inOrder.verify(completion2).onSuccess(eq(batch), eq(22));
        inOrder.verify(completion3).onSuccess(eq(batch), eq(33));

        assertTrue(batch.getCompletedOperations().contains(first));
        assertTrue(batch.getCompletedOperations().contains(second));
        assertTrue(batch.getCompletedOperations().contains(third));
        assertEquals(3, batch.getCompletedOperations().size());

        assertFalse("Batch should NOT have abended", batch.didAbend());

    }
}
