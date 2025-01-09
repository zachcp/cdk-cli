(ns cdk.depict 
  (:import [org.openscience.cdk.depict Depiction] 
           [cdk.cdkcli DepictController]
           [javax.imageio ImageIO]
           [java.io File]))


(def depict-opts [{:option "smiles"
                   :type :string
                   :default :present
                   :description "Smiles Input String"}
                  {:option "fmt"
                   :type #{"jpg", "png", "svg", "pdf"}
                   :default :present
                   :description "Image Format"}
                  {:option "style"
                   :type #{"bow" "cow" "cob"} 
                   :default :present
                   :description "Image Style"}
                  {:option "output"
                   :type :string
                   :default :present
                   :description "Output File"}
                  {:option "smalim"
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
                   :description "SVG units"}])


(defn write-depiction [depiction fmt output-path]
  (case fmt
    "svg" (spit output-path (.toSvgStr depiction))
    "pdf" (spit output-path (.toPdfStr depiction))
    ("png" "jpg" "gif")
    (let [img (.toImg depiction)]
      (ImageIO/write img fmt (File. output-path)))
    (throw (IllegalArgumentException.
            (str "Unsupported format: " fmt)))))



(defn run-depict [{:keys [smiles fmt style output] :as params}]
  (let [depict-controller (DepictController.)
        param-map (-> params
                      (dissoc :smiles)
                      (dissoc :imgtype)
                      (into (java.util.HashMap.)))
       ^Depiction depiction (.depict depict-controller smiles fmt style param-map)
        output-path (if (.contains output ".")
                            output
                            (str output "." fmt))]

  (write-depiction depiction fmt output-path)
  (println "Wrote depiction to:" output-path)
   0
     ))



(def depict-command-opts
  {:command "depict"
   :description "Depicts chemical structures"
   :opts depict-opts
   :runs run-depict})
