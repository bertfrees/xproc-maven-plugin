[xproc-maven-plugin][]
======================
Maven plugin for running [XProc][] pipelines and [XProcSpec][] tests.

Goals
-----

### xproc:xproc
Run an XProc pipeline.

- `pipeline` Path to the pipeline.

### xproc:xprocspec
Run a series of XProcSpec tests.

- `xprocspecDirectory` Directory containing the XProcSpec tests. Default `${basedir}/src/test/xprocspec`
- `reportsDirectory` Directory that will contain the generated reports. Default `${project.build.dir}/xprocspec-reports`
- `surefireReportsDirectory` Directory that will contain standard Surefire reports. Default `${project.build.dir}/surefire-reports`

Usage
-----
```xml
<build>
  <plugins>
    ...
    <plugin>
      <groupId>org.daisy.maven</groupId>
      <artifactId>xproc-maven-plugin</artifactId>
      <version>1.0-SNAPSHOT</version>
      <executions>
        <execution>
          <phase>test</phase>
          <goals>
            <goal>xprocspec</goal>
          </goals>
        </execution>
      </executions>
      <dependencies>
        <dependency>
          <groupId>org.daisy.libs</groupId>
          <artifactId>com.xmlcalabash</artifactId>
          <version>1.0.10-SNAPSHOT</version>
        </dependency>
        <dependency>
          <groupId>com.thaiopensource</groupId>
          <artifactId>jing</artifactId>
          <version>20091111</version>
          <exclusions>
            <exclusion>
              <groupId>net.sf.saxon</groupId>
              <artifactId>saxon</artifactId>
            </exclusion>
            <exclusion>
              <groupId>xml-apis</groupId>
              <artifactId>xml-apis</artifactId>
            </exclusion>
          </exclusions>
        </dependency>
      </dependencies>
    </plugin>
    ...
  <plugins>
</build>
```

For rendering the XProcSpec reports in HTML:

```xml
<reporting>
  <plugins>
    <plugin>
      <groupId>org.apache.maven.plugins</groupId>
      <artifactId>maven-surefire-report-plugin</artifactId>
      <version>2.14.1</version>
    </plugin>
  </plugins>
</reporting>
```

License
-------
Copyright © 2013 [Bert Frees][bert]

Licensed under the [Apache License, Version 2.0][apache2] (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

[http://www.apache.org/licenses/LICENSE-2.0][apache2]

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.


[xproc-maven-plugin]: http://github.com/bertfrees/xproc-maven-plugin
[xproc]: http://xproc.org/
[xprocspec]: https://github.com/josteinaj/xprocspec
[bert]: http://github.com/bertfrees
[apache2]: http://www.apache.org/licenses/LICENSE-2.0
