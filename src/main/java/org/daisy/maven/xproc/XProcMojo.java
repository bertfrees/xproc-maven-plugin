package org.daisy.maven.xproc;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.apache.maven.plugin.MojoExecutionException;

import static org.daisy.maven.xproc.utils.asURI;

/**
 * Run an XProc pipeline.
 *
 * @goal xproc
 */
public class XProcMojo extends AbstractMojo {
	
	/**
	 * Path to the pipeline.
	 *
	 * @parameter
	 * @required
	 */
	private File pipeline;
	
	/**
	 * Input paths.
	 *
	 * @parameter
	 */
	private Map<String,String> inputs;
	
	/**
	 * Output paths.
	 *
	 * @parameter
	 */
	private Map<String,String> outputs;
	
	/**
	 * Options.
	 *
	 * @parameter
	 */
	private Map<String,String> options;
	
	public void execute() throws MojoExecutionException {
		String pipelineAsURI = asURI(pipeline);
		Map<String,String> inputsAsURIs = null;
		Map<String,String> outputsAsURIs = null;
		if (inputs != null) {
			inputsAsURIs = new HashMap<String,String>();
			for (String port : inputs.keySet())
				inputsAsURIs.put(port, asURI(new File(inputs.get(port)))); }
		if (outputs != null) {
			outputsAsURIs = new HashMap<String,String>();
			for (String port : outputs.keySet())
				outputsAsURIs.put(port, asURI(new File(outputs.get(port)))); }
		try {
			System.out.println("Running XProc ...");
			engine.run(pipelineAsURI,
			           inputsAsURIs,
			           outputsAsURIs,
			           options,
			           null); }
		catch (Exception e) {
			throw new MojoExecutionException("Error running XProc", e); }
	}
}
