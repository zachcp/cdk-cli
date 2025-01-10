(ns build
  (:refer-clojure :exclude [test])
  (:require [clojure.tools.deps :as t]
            [clojure.tools.build.api :as b]))

(def lib 'cdk/cdkcli)

;; note - this will end up in the tagged releade
(def version "0.0.5")
(def main 'cdk.cdkcli)
(def class-dir "target/classes")

(def basis (delay (b/create-basis {:project "deps.edn"})))

(defn compile_java [_]
  (b/javac {:src-dirs ["src/java"]
            :class-dir class-dir
            :basis @basis
            ;; :javac-opts ["--release" "11"]
            :javac-opts ["-source" "8" "-target" "8"]
            }))


(defn test "Run all the tests." [opts]
  (println "\nRunning tests...")
  (let [
        _ (compile_java nil)
        basis    (b/create-basis {:aliases [:test]})
        combined (t/combine-aliases basis [:test])
        cmds     (b/java-command
                  {:basis basis
                   :java-opts (:jvm-opts combined)
                   :main      'clojure.main
                   :main-args ["-m" "cognitect.test-runner"]})
        {:keys [exit]} (b/process cmds)]
    (when-not (zero? exit) (throw (ex-info "Tests failed" {}))))
  opts)

(defn- uber-opts [opts]
  (assoc opts
         :lib lib :main main
         :uber-file (format "target/%s-%s.jar" lib version)
         :basis (b/create-basis {})
         :class-dir class-dir
         :src-dirs ["src/java"]
         :ns-compile [main]))

(defn ci "Run the CI pipeline of tests (and build the uberjar)." [opts]
  (test opts)
  (b/delete {:path "target"})
  (let [opts (uber-opts opts)]
    (compile_java nil)
    (println "\nCopying source...")
    (b/copy-dir {:src-dirs ["resources" "src"] :target-dir class-dir})
    (println (str "\nCompiling " main "..."))
    (b/compile-clj opts)
    (println "\nBuilding JAR...")
    (b/uber opts))
  opts)
