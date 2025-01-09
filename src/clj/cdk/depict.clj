(ns cdk.depict
  (:require [clojure.java.io :as io])
  (:import [org.openscience.cdk.depict DepictionGenerator]
           [org.openscience.cdk.silent SilentChemObjectBuilder]
           [org.openscience.cdk.smiles SmilesParser]
           [com.simolecule.centres BaseMol CdkLabeller Descriptor]
           [org.openscience.cdk CDKConstants]
           [org.openscience.cdk.depict Abbreviations Depiction DepictionGenerator]
           [org.openscience.cdk.exception CDKException InvalidSmilesException]
           [org.openscience.cdk.geometry GeometryUtil]
           [org.openscience.cdk.graph Cycles]
           [org.openscience.cdk.interfaces IAtom IAtomContainer IBond IChemObject IChemObjectBuilder IReaction IReactionSet IStereoElement]
           [org.openscience.cdk.io MDLV2000Reader MDLV3000Reader]
           [org.openscience.cdk.layout StructureDiagramGenerator]
           [org.openscience.cdk.renderer RendererModel SymbolVisibility]
           [org.openscience.cdk.renderer.color CDK2DAtomColors IAtomColorer UniColor]
           [org.openscience.cdk.renderer.generators.standard StandardGenerator StandardGenerator$Visibility]
           [org.openscience.cdk.sgroup Sgroup SgroupKey SgroupType]
           [org.openscience.cdk.silent SilentChemObjectBuilder]
           [org.openscience.cdk.smarts SmartsPattern]
           [org.openscience.cdk.smiles SmilesParser]
           [org.openscience.cdk.stereo ExtendedTetrahedral Octahedral SquarePlanar Stereocenters TetrahedralChirality TrigonalBipyramidal]
           [org.openscience.cdk.tools.manipulator AtomContainerManipulator ReactionManipulator ReactionSetManipulator]
           [cdk.cdkcli DepictController]
           [javax.imageio ImageIO]
           [java.io File ByteArrayOutputStream]))



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



    ;; // pre-render the depiction
    ;; final Depiction depiction = isRxn ? myGenerator.depict(rxns)
    ;;         : isRgp ? myGenerator.depict(mols, mols.size(), 1)
    ;;         : myGenerator.depict(mol);

    ;; switch (fmtlc) {
    ;;   case Depiction.SVG_FMT:
    ;;     return makeResponse(depiction.toSvgStr(getString(Param.SVGUNITS, extra))
    ;;                                  .getBytes(), "image/svg+xml");
    ;;   case Depiction.PDF_FMT:
    ;;     return makeResponse(depiction.toPdfStr().getBytes(), "application/pdf");
    ;;   case Depiction.PNG_FMT:
    ;;   case Depiction.JPG_FMT:
    ;;   case Depiction.GIF_FMT:
    ;;     ByteArrayOutputStream bao = new ByteArrayOutputStream();
    ;;     ImageIO.write(depiction.toImg(), fmtlc, bao);
    ;;     return makeResponse(bao.toByteArray(), "image/" + fmtlc);
    ;; }


(defn write-depiction [depiction fmt output-path]
  (case fmt
    "svg" (spit output-path (.toSvgStr depiction))
    "pdf" (spit output-path (.toPdfStr depiction))
    ;; For image formats (png, jpg, gif)
    ("png" "jpg" "gif")
    (let [img (.toImg depiction)]
      (ImageIO/write img fmt (File. output-path)))
    ;; Default case - throw error for unknown format
    (throw (IllegalArgumentException.
            (str "Unsupported format: " fmt)))))



(defn run-depict [{:keys [smiles fmt style output] :as params}]
  (let [depict-controller (DepictController.)
        param-map (-> params
                      (dissoc :smiles)
                      (dissoc :imgtype)
                      (into (java.util.HashMap.)))
        depiction (.depict depict-controller smiles fmt style param-map)
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
