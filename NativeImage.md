
## Native Image Attempts

Current status. Will create an Image but fail on AWT.

```sh
rm -rf target/

clojure -T:build ci

java -agentlib:native-image-agent=config-output-dir=config \
     -jar target/cdk/cdkcli-0.0.5.jar depict --smiles "O[C@@H]1CC[C@H](C[C@H]1OC)C[C@@H](C)[C@@H]4CC(=O)[C@H](C)/C=C(\C)[C@@H](O)[C@@H](OC)C(=O)[C@H](C)C[C@H](C)\C=C\C=C\C=C(/C)[C@@H](OC)C[C@@H]2CC[C@@H](C)[C@@](O)(O2)C(=O)C(=O)N3CCCC[C@H]3C(=O)O4"  --fmt png --style cow --output out.png

native-image \
  -H:Name=cdk-cli \
  -Djava.awt.headless=false \
  -H:ConfigurationFileDirectories=config \
  -H:JNIConfigurationFiles=config/jni-config.json \
  -H:+ReportExceptionStackTraces \
  --features=clj_easy.graal_build_time.InitClojureClasses \
  --initialize-at-build-time=org.openscience.cdk.interfaces \
  --initialize-at-build-time=javax.vecmath.Vector2d \
  --initialize-at-build-time=javax.vecmath.Point2d \
  --initialize-at-build-time=uk.ac.ebi.beam \
  --verbose \
  -jar target/cdk/cdkcli-0.0.5.jar


./cdk-cli depict --smiles "O[C@@H]1CC[C@H](C[C@H]1OC)C[C@@H](C)[C@@H]4CC(=O)[C@H](C)/C=C(\C)[C@@H](O)[C@@H](OC)C(=O)[C@H](C)C[C@H](C)\C=C\C=C\C=C(/C)[C@@H](OC)C[C@@H]2CC[C@@H](C)[C@@](O)(O2)C(=O)C(=O)N3CCCC[C@H]3C(=O)O4"  --fmt png --style cow --output out.png

```

## Current Error

```
(base) zcpowers@Zacharys-MacBook-Air cdkcli % ./cdkcli-0.0.5 depict --smiles "O[C@@H]1CC[C@H](C[C@H]1OC)C[C@@H](C)[C@@H]4CC(=O)[C@H](C)/C=C(\C)[C@@H](O)[C@@H](OC)C(=O)[C@H](C)C[C@H](C)\C=C\C=C\C=C(/C)[C@@H](OC)C[C@@H]2CC[C@@H](C)[C@@](O)(O2)C(=O)C(=O)N3CCCC[C@H]3C(=O)O4"  --fmt png --style cow --output out.png

** ERROR: **
Exception: #error {
 :cause Can't load library: awt | java.library.path = [.]
 :via
 [{:type java.lang.UnsatisfiedLinkError
   :message Can't load library: awt | java.library.path = [.]
   :at [com.oracle.svm.core.jdk.NativeLibraries loadLibraryRelative NativeLibraries.java 141]}]
 :trace
 [[com.oracle.svm.core.jdk.NativeLibraries loadLibraryRelative NativeLibraries.java 141]
  [java.lang.ClassLoader loadLibrary ClassLoader.java 108]
  [java.lang.Runtime loadLibrary0 Runtime.java 916]
  [java.lang.System loadLibrary System.java 2066]
  [java.awt.Toolkit$2 run Toolkit.java 1384]
  [java.awt.Toolkit$2 run Toolkit.java 1382]
  [java.security.AccessController executePrivileged AccessController.java 132]
  [java.security.AccessController doPrivileged AccessController.java 319]
  [java.awt.Toolkit loadLibraries Toolkit.java 1381]
  [java.awt.Toolkit initStatic Toolkit.java 1419]
  [java.awt.Toolkit <clinit> Toolkit.java 1393]
  [java.awt.Font <clinit> Font.java 288]
  [org.openscience.cdk.depict.DepictionGenerator <init> DepictionGenerator.java 227]
```
