(ns cdk.cdkcli
  (:gen-class)
  (:require [cli-matic.core :refer [run-cmd]]
            [cdk.convertsmiles :as convertsmiles]
            [cdk.depict :as depict])
  )

(set! *warn-on-reflection* true)


(def CONFIGURATION
  {:command "cdk-cli"
   :description "Chemistry CLI utilities using CDK"
   :version "1.0"
   :subcommands
   [{:command "depict"
     :description "Depicts chemical structures"
     :opts [{:option "smalim"
             :type :int
             :default 100
             :description "SMARTS hit limit"}
            {:option "sma"
             :type :string
             :default ""
             :description "SMARTS query"}
            {:option "hdisp"
             :type :flag
             :default false
             :description "Display hydrogens"}
            {:option "alignrxnmap"
             :type :flag
             :default true
             :description "Align reaction map"}
            {:option "anon"
             :type :flag
             :default false
             :description "Anonymize"}
            {:option "suppressh"
             :type :flag
             :default true
             :description "Suppress hydrogens"}
            {:option "annotate"
             :type :string
             :default "none"
             :description "Annotation type"}
            {:option "abbr"
             :type :string
             :default "reagents"
             :description "Abbreviation type"}
            {:option "bgcolor"
             :type :string
             :default "default"
             :description "Background color"}
            {:option "fgcolor"
             :type :string
             :default "default"
             :description "Foreground color"}
            {:option "showtitle"
             :type :flag
             :default false
             :description "Show title"}
            {:option "arw"
             :type :keyword
             :default :forward
             :description "Arrow direction"}
            {:option "dat"
             :type :keyword
             :default :metals
             :description "Dative bond type"}
            {:option "zoom"
             :type :float
             :default 1.3
             :description "Zoom level"}
            {:option "ratio"
             :type :float
             :default 1.1
             :description "Aspect ratio"}
            {:option "r"
             :type :int
             :default 0
             :description "Rotation angle"}
            {:option "f"
             :type :flag
             :default false
             :description "Flip"}
            {:option "w"
             :type :int
             :default -1
             :description "Width"}
            {:option "h"
             :type :int
             :default -1
             :description "Height"}
            {:option "svgunits"
             :type :string
             :default "mm"
             :description "SVG units"}]
     :runs depict/depict}
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

