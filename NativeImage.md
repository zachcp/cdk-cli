
## Native Image Attempts

```sh

rm -rf target/

clojure -T:build ci

native-image \
  --features=clj_easy.graal_build_time.InitClojureClasses \
  -H:ReflectionConfigurationFiles=reflect-config.json \
  --initialize-at-build-time=org.openscience.cdk \
  --initialize-at-build-time=javax.vecmath.Vector2d \
  --initialize-at-build-time=javax.vecmath.Point2d \
  --initialize-at-build-time=uk.ac.ebi.beam \
  --initialize-at-build-time=java.awt.Color \
  -jar target/cdk/cdkcli-0.0.5.jar


#$JAVA_HOME/bin/java  -agentlib:native-image-agent=config-output-dir=/agentlib-profile/ \
#    -jar target/cdk/cdkcli-0.0.5.jar

./cdkcli-0.0.5

```



```txt
1) If it is intended that objects of type 'org.openscience.cdk.interfaces.IReaction$Direction' are persisted in the image heap, add

    '--initialize-at-build-time=org.openscience.cdk.interfaces.IReaction$Direction'

to the native-image arguments. Note that initializing new types can store additional objects to the heap. It is advised to check the static fields of 'org.openscience.cdk.interfaces.IReaction$Direction' to see if they are safe for build-time initialization,  and that they do not contain any sensitive data that should not become part of the image.

2) If these objects should not be stored in the image heap, you can use

    '--trace-object-instantiation=org.openscience.cdk.interfaces.IReaction$Direction'

to find classes that instantiate these objects. Once you found such a class, you can mark it explicitly for run time initialization with

    '--initialize-at-run-time=<culprit>'

to prevent the instantiation of the object.

If you are seeing this message after upgrading to a new GraalVM release, this means that some objects ended up in the image heap without their type being marked with --initialize-at-build-time.
To fix this, include '--initialize-at-build-time=org.openscience.cdk.interfaces.IReaction$Direction' in your configuration. If the classes do not originate from your code, it is advised to update all library or framework dependencies to the latest version before addressing this error.

The following detailed trace displays from which field in the code the object was reached.
Object was reached by
  reading field cdk.cdkcli.DepictController$Param.defaultValue of constant
    cdk.cdkcli.DepictController$Param@265346be: ARROW
  scanning root cdk.cdkcli.DepictController$Param@265346be: ARROW embedded in
    cdk.cdkcli.DepictController.depict(DepictController.java:344)
  parsing method cdk.cdkcli.DepictController.depict(DepictController.java:269) reachable via the parsing context
    at cdk.depict$run_depict.invokeStatic(depict.clj:124)
    at cdk.depict$run_depict.invoke(depict.clj:118)
    at clojure.core$isa_QMARK_.invokeStatic(core.clj:5629)
    at clojure.core$isa_QMARK_.invoke(core.clj:5617)
    at clojure.lang.AFn.applyToHelper(AFn.java:160)
    at clojure.lang.AFn.applyTo(AFn.java:144)
    at cdk.cdkcli.main(Unknown Source)
    at com.oracle.svm.core.JavaMainWrapper.invokeMain(JavaMainWrapper.java:181)
    at com.oracle.svm.core.JavaMainWrapper.runCore0(JavaMainWrapper.java:232)
    at com.oracle.svm.core.JavaMainWrapper.doRun(JavaMainWrapper.java:299)
    at com.oracle.svm.core.code.IsolateEnterStub.JavaMainWrapper_run_XNhh1mz2Ib2aPR1wdv014D(generated:0)
    at static root method.(Unknown Source)
```
