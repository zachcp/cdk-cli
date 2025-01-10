
## Native Image Attempts

```sh

rm -rf target/

clojure -T:build ci

  -Djava.awt.headless=false \

native-image \
  --features=clj_easy.graal_build_time.InitClojureClasses \
  -H:ReflectionConfigurationFiles=reflect-config.json \
  -H:+JNI \
  -H:+UnlockExperimentalVMOptions \
  --initialize-at-build-time=org.openscience.cdk.interfaces \
  --initialize-at-build-time=javax.vecmath.Vector2d \
  --initialize-at-build-time=javax.vecmath.Point2d \
  --initialize-at-build-time=uk.ac.ebi.beam \
  --initialize-at-build-time=java.awt \
  --initialize-at-build-time=sun.awt \
  --initialize-at-build-time=sun.java2d.HeadlessGraphicsEnvironment \
  -jar target/cdk/cdkcli-0.0.5.jar


./cdkcli-0.0.5 depict --smiles "O[C@@H]1CC[C@H](C[C@H]1OC)C[C@@H](C)[C@@H]4CC(=O)[C@H](C)/C=C(\C)[C@@H](O)[C@@H](OC)C(=O)[C@H](C)C[C@H](C)\C=C\C=C\C=C(/C)[C@@H](OC)C[C@@H]2CC[C@@H](C)[C@@](O)(O2)C(=O)C(=O)N3CCCC[C@H]3C(=O)O4"  --fmt png --style cow --output out.png

```
