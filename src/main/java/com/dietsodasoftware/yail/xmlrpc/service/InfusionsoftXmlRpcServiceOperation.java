package com.dietsodasoftware.yail.xmlrpc.service;

import com.dietsodasoftware.yail.xmlrpc.client.YailClient;
import com.dietsodasoftware.yail.xmlrpc.client.annotations.InfusionsoftRpc;
import org.springframework.core.annotation.AnnotationUtils;

import java.util.LinkedList;
import java.util.List;

/**
 * To subclass this, you must either proivde the @InfusionsoftRpc annotation or provide service/method values.  Or the ctor will throw.
 *
 * @param <T>
 */
public abstract class InfusionsoftXmlRpcServiceOperation<T> {

    private final String rpcName;
    protected InfusionsoftXmlRpcServiceOperation(){

        final InfusionsoftRpc rpcAnnotation = AnnotationUtils.findAnnotation(this.getClass(), InfusionsoftRpc.class);
        if(rpcAnnotation == null){
            throw new IllegalArgumentException("Must provide the return XML RPC service and method using annotation @InfusionsoftRpc on class " + this.getClass().getName());
        }
        rpcName = buildRpcName(rpcAnnotation.service(), rpcAnnotation.method());

    }

    protected InfusionsoftXmlRpcServiceOperation(String service, String method){
        rpcName = buildRpcName(service, method);
    }

    private String buildRpcName(String service, String method){
        if(service == null || service.isEmpty()){
            throw new IllegalArgumentException("null argument: service");
        }
        if(method == null || method.isEmpty()){
            throw new IllegalArgumentException("null argument: method");
        }

        return new StringBuilder()
                      .append(service.trim())
                      .append(".")
                      .append(method.trim())
                      .toString();
    }

	@SuppressWarnings("unchecked")
	public final List<?> getXmlRpcParameters(YailClient isft) throws InfusionsoftXmlRpcException {
        @SuppressWarnings("rawtypes")
		final List parameters = new LinkedList();
        if(includeApiKey()){
            parameters.add(isft.getKey());
        }
		parameters.addAll(getOperationParameters());
		
		return parameters;
	}
	
	abstract protected List<?> getOperationParameters();

    /**
     * The only reason you wouldn't do this is if you are the vendor key auth operation.
     * @return to include API key in the parameter list
     */
    protected boolean includeApiKey(){
        return true;
    }

	public final String getRpcName(){
        return rpcName;
    }

	public abstract T parseResult(Object rawResponse) throws InfusionsoftResponseParsingException, InfusionsoftAuthorizationFailureException;
}
