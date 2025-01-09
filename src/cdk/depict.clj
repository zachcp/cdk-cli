(ns cdk.depict
  (:import [org.openscience.cdk.depict DepictionGenerator]
           [org.openscience.cdk.silent SilentChemObjectBuilder]
           [org.openscience.cdk.smiles SmilesParser]))


;; https://github.com/cdk/depict/blob/master/cdkdepict-lib/src/main/java/org/openscience/cdk/app/DepictController.java
;; inputs are:
(defn depict [params])