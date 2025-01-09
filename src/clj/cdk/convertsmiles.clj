(ns cdk.convertsmiles
 (:import [org.openscience.cdk.depict DepictionGenerator]
          [org.openscience.cdk.silent SilentChemObjectBuilder]
          [org.openscience.cdk.smiles SmilesParser]))


(def convertsmiles-opts      
  [{:option "smiles"
    :short "s"
    :type :string
    :default :present
    :description "The SMILES string to convert"}
   {:option "output"
    :short "o"
    :type :string
    :default :present
    :description "The output SVG file"}])

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


 (def convertsmiles-command-opts
   {:command "convertsmiles"
    :description "Converts SMILES to SVG using CDK"
    :opts convertsmiles-opts      
    :runs convert-smiles-to-svg})