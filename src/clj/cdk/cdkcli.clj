(ns cdk.cdkcli
  (:gen-class)
  (:require [cli-matic.core :refer [run-cmd]]
            [cdk.convertsmiles :as convertsmiles]
            [cdk.depict :as depict]))

(set! *warn-on-reflection* true)


(def CONFIGURATION
  {:command "cdk-cli"
   :description "Chemistry CLI utilities using CDK"
   :version "1.0"
   :subcommands
   [ depict/depict-command-opts 
    
    {:command "convertsmiles"
     :description "Converts SMILES to SVG using CDK"
     :opts [{:option "smiles"
             :short "s"
             :type :string
             :default :present
             :description "The SMILES string to convert"}
            {:option "output"
             :short "o"
             :type :string
             :default :present
             :description "The output SVG file"}]
     :runs convertsmiles/convert-smiles-to-svg}]})



(defn -main [& args]
  (run-cmd args CONFIGURATION))
