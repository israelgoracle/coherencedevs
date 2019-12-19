package com.telefonica.coco.api.monitorvar;

import com.telefonica.coco.api.flowvars.service.FlowVarsApi;
import com.telefonica.coco.api.flowvars.service.impl.FlowVarsApiImpl;

public class SentenciasVAR {
	
	
	
	public static int count()
	{
		FlowVarsApi api = new FlowVarsApiImpl();
		return api.countFlowVar();
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println(SentenciasVAR.count());
	}

}
