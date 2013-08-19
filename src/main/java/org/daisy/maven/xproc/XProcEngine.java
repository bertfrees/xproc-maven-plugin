package org.daisy.maven.xproc;

import java.util.List;
import java.util.Map;

public interface XProcEngine {
	public void run(String pipeline,
	                Map<String,List<String>> inputs,
	                Map<String,String> outputs,
	                Map<String,String> options,
	                Map<String,Map<String,String>> parameters)
			throws XProcExecutionException;
}
