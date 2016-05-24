**orderedcp-maven-plugin**
A Mojo plugin to workaround the problem of multi-modules maven projects that need to be deployed in a specific order.
----------

**Usage**
----------
1) Clone it;
2) Use Maven to build and install the plugin;
3) Add	the dependency:
<dependency>
		<groupId>order.plugin</groupId>
		<artifactId>openiot-maven-plugin</artifactId>
		<version>0.0.1-SNAPSHOT</version>
		<scope>provided</scope>
</dependency>
4) Add the plugin:
<plugin>
				<groupId>order.plugin</groupId>
				<artifactId>openiot-maven-plugin</artifactId>
				<version>0.0.1-SNAPSHOT</version>
				<configuration>
					<orderedSourceFiles>
						<param>C:\Users\JoaoBoscoJares\workspace\openiot-develop\modules\lsm-light\lsm-light.server\target\lsm-light.server.war</param>
						<param>C:\Users\JoaoBoscoJares\workspace\openiot-develop\modules\security\security-server\target\openiot-cas.war</param>
						<param>C:\Users\JoaoBoscoJares\workspace\openiot-develop\modules\security\security-management\target\security.management.war</param>
						<param>C:\Users\JoaoBoscoJares\workspace\openiot-develop\modules\scheduler\scheduler.core\target\scheduler.core.war</param>
						<param>C:\Users\JoaoBoscoJares\workspace\openiot-develop\modules\sdum\sdum.core\target\sdum.core.war</param>
						<param>C:\Users\JoaoBoscoJares\workspace\openiot-develop\ui\ui.requestDefinition\target\ui.requestDefinition.war</param>
						<param>C:\Users\JoaoBoscoJares\workspace\openiot-develop\ui\ui.requestPresentation\target\ui.requestPresentation.war</param>
						<param>C:\Users\JoaoBoscoJares\workspace\openiot-develop\ui\ui.schemaeditor\target\ui.schemaeditor.war</param>
						<param>C:\Users\JoaoBoscoJares\workspace\openiot-develop\ui\ide\ide.core\target\ide.core.war</param>
					</orderedSourceFiles>
					<targetDir>C:\ProgramFilesDevel\jboss-as-7.1.1.Final\standalone\deployments</targetDir>
				</configuration>
				<executions>
					<execution>
						<id>order.plugin</id>
						<goals>
							<goal>cp-ordered</goal>
						</goals>
					</execution>
				</executions>
			</plugin>
			
	5) Run it: mvn openiot:cp-ordered
	
	--
	
	Thanks!
