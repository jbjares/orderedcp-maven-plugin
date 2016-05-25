package order.openiot.maven.plugin;


import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

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
				copyFilesToJBossDeploymentFolder(null);
		    
		} catch (RuntimeException | IOException e) {
			getLog().error(e);
			throw new RuntimeException("ERROR: ", e);
		}
	}

	private void copyFilesToJBossDeploymentFolder(File file) throws IOException {

		File actualFile = file;
		if(file==null && orderedSourceFiles.size()==1){
			//cleanDeploymentFolder();
			actualFile = orderedSourceFiles.get(0);
			FileUtils.copyFileToDirectory(actualFile, targetDir);
			return;
		}

		for(File nextFile:orderedSourceFiles){
			getLog().info("nextFile: "+nextFile);
				FileUtils.copyFileToDirectory(nextFile, targetDir);
				String fileNameWithoutSuffix = nextFile.getName().substring(0,nextFile.getName().indexOf(".war"));
				File deployedFile = new File(targetDir+"\\"+fileNameWithoutSuffix+".war.deployed");
				while(!deployedFile.exists()){
					getLog().info("=== "+deployedFile.getCanonicalPath()+deployedFile.getName());
					getLog().info("=== into isdeployed loop:value: "+deployedFile.exists()+" FOR: "+fileNameWithoutSuffix+".war.deployed");
					//TODO improve it. create a timeout, avaliate other file types.
					continue;
				}
		}
			
		getLog().info("%%% Mojo cp-ordered Finished! %%%");
		
	}

	private void cleanDeploymentFolder() {
	
		File target = targetDir;
		if(!target.isDirectory()){
			return;
		}
		for(File fileToDelete:target.listFiles()){
			fileToDelete.delete();
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


	public static void main(String[] args) {
		File file = new File("C:\\ProgramFilesDevel\\jboss-as-7.1.1.Final\\standalone\\deployments\\test-0.0.1-SNAPSHOT.war");
		String fileNameWithoutSuffix = file.getName().substring(0,file.getName().indexOf(".war"));
		System.out.println(fileNameWithoutSuffix);
		File parentFile = file.getParentFile();
		System.out.println(parentFile.getAbsolutePath());
		//for(File subNamedFiles:parentFile.listFiles()){
			File expectedFile = new File(fileNameWithoutSuffix+".war.deployed");
			while(!expectedFile.exists()){
				//TODO improve it. create a timeout, avaliate other file types.
				continue;
			}
		//}
	}

	
}
