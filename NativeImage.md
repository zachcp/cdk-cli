
## Native Image Attempts

```
clojure -T:build

native-image \
  --features=clj_easy.graal_build_time.InitClojureClasses \
  --initialize-at-run-time=org.openscience.cdk.interfaces.IReaction$Direction \
  -jar target/cdk/cdkcli-0.0.5.jar


./cdkcli-0.0.5

```
