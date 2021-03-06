package org.daisy.maven.xproc.pipeline;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.util.List;
import java.util.Map;

import javax.xml.namespace.QName;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamResult;

import org.daisy.common.base.Provider;
import org.daisy.common.transform.LazySaxResultProvider;
import org.daisy.common.transform.LazySaxSourceProvider;
import org.daisy.common.xproc.XProcEngine;
import org.daisy.common.xproc.XProcInput;
import org.daisy.common.xproc.XProcOutput;
import org.daisy.common.xproc.XProcPipeline;
import org.daisy.common.xproc.XProcPortInfo;
import org.daisy.common.xproc.XProcResult;

import org.daisy.maven.xproc.api.XProcExecutionException;

public class DaisyPipeline2 implements org.daisy.maven.xproc.api.XProcEngine {
	
	private XProcEngine engine;
	
	protected void setXProcEngine(XProcEngine engine) {
		this.engine = engine;
	}
	
	protected void activate() {}
	
	public void run(String pipeline,
	                Map<String,List<String>> inputs,
	                Map<String,String> outputs,
	                Map<String,String> options,
	                Map<String,Map<String,String>> parameters)
			throws XProcExecutionException {
		try {
			XProcPipeline xprocPipeline = engine.load(new URI(pipeline));
			XProcInput.Builder inputBuilder = new XProcInput.Builder();
			if (inputs != null)
				for (String port : inputs.keySet())
					for (String document : inputs.get(port))
						inputBuilder.withInput(port, new LazySaxSourceProvider(document));
			if (options != null)
				for (String name : options.keySet())
					inputBuilder.withOption(new QName("", name), options.get(name));
			if (parameters != null)
				for (String port : parameters.keySet())
					for (String name : parameters.get(port).keySet())
						inputBuilder.withParameter(port, new QName("", name), parameters.get(port).get(name));
			XProcResult results = xprocPipeline.run(inputBuilder.build());
			XProcOutput.Builder outputBuilder = new XProcOutput.Builder();
			for (XProcPortInfo info : xprocPipeline.getInfo().getOutputPorts()) {
				String port = info.getName();
				outputBuilder.withOutput(port, (outputs != null) && outputs.containsKey(port) ?
				                         new LazySaxResultProvider(outputs.get(port)) :
				                         new DevNullStreamResultProvider()); }
			results.writeTo(outputBuilder.build()); }
		catch (Exception e) {
			throw new XProcExecutionException("DAISY Pipeline failed to execute XProc", e); }
	}
	
	private static class DevNullStreamResultProvider implements Provider<Result> {
		private static final Result result = new StreamResult(
			new OutputStream() {
				@Override public void write(byte[] b, int off, int len) throws IOException {}
				@Override public void write(byte[] b) throws IOException {}
				@Override public void write(int b) throws IOException {}});
		public Result provide() {
			return result;
		}
	}
}
