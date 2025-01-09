(ns cdk.convertsmiles
 (:import [org.openscience.cdk.depict DepictionGenerator]
          [org.openscience.cdk.silent SilentChemObjectBuilder]
          [org.openscience.cdk.smiles SmilesParser]))


(defn convert-smiles-to-svg
  "Converts a SMILES string to an SVG file"
  [{:keys [smiles output]}]
  (try
    (let [smiles-parser (SmilesParser. (SilentChemObjectBuilder/getInstance))
          molecule (.parseSmiles smiles-parser smiles)
          depict (-> (DepictionGenerator.)
                     (.withSize 300 300)
                     (.withMolTitle))]
      (-> depict
          (.depict molecule)
          (.writeTo output))
      (println "SVG file created:" output)
      0)
    (catch Exception e
      (println "Error:" (.getMessage e))
      (.printStackTrace e)
      1)))