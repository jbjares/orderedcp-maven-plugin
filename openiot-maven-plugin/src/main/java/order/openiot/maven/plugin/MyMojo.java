package order.openiot.maven.plugin;


import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.codehaus.plexus.util.IOUtil;

@Mojo( name = "cp-ordered")
public class MyMojo extends AbstractMojo {


    @Parameter(required=true)
    private File targetDir;
    
    @Parameter(required=true)
    private List<File> orderedSourceFiles;
    

	public void execute() throws MojoExecutionException {
		getLog().info("%%% Mojo cp-ordered Started! %%%");
		try {

				try{
					URL u = new URL("http://127.0.0.1:8080"); 
				    HttpURLConnection huc =  (HttpURLConnection)  u.openConnection(); 
				    huc.setRequestMethod("GET"); 
				    huc.connect(); 
				    int code = huc.getResponseCode();
				    //TODO add code to do the parameterization.
				    getLog().info("===> Code: "+code);// The code must be 200
				}catch(java.net.ConnectException jne){
					throw new RuntimeException("AS is not running on 127.0.0.1:8080",jne);
				}
		    
			getLog().info("%%% "+targetDir+" %%%");
			getLog().info("%%% "+Arrays.asList(orderedSourceFiles).toString()+" %%%");
			int count = 1;
			for(File file: orderedSourceFiles){
				getLog().info(count+") ====> copying file from: "+file.getAbsolutePath()+" to: "+targetDir.getAbsolutePath());
				FileUtils.copyFileToDirectory(file, targetDir);
				getLog().info(count+") ====> The file: "+file.getAbsolutePath()+" was copied to: "+targetDir.getAbsolutePath());
				count++;
			}
			
			getLog().info("%%% Mojo cp-ordered Finished! %%%");
		    
		} catch (RuntimeException | IOException e) {
			getLog().error(e);
			throw new RuntimeException("ERROR: ", e);
		}
	}

	public File getTargetDir() {
		return targetDir;
	}

	public void setTargetDir(File targetDir) {
		this.targetDir = targetDir;
	}

	public List<File> getOrderedSourceFiles() {
		return orderedSourceFiles;
	}

	public void setOrderedSourceFiles(List<File> orderedSourceFiles) {
		this.orderedSourceFiles = orderedSourceFiles;
	}




	
}
