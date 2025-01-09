(ns cdk.depict
  (:import [org.openscience.cdk.depict DepictionGenerator]
           [org.openscience.cdk.silent SilentChemObjectBuilder]
           [org.openscience.cdk.smiles SmilesParser]
           [com.simolecule.centres BaseMol CdkLabeller Descriptor]
           [org.openscience.cdk.app DepictController]
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
           ))


; todo
(def get-hydrogen-display [])


; todo
(def get-hydrogen-display [])


;; https://github.com/cdk/depict/blob/master/cdkdepict-lib/src/main/java/org/openscience/cdk/app/DepictController.java
;; inputs are:
(defn depict [{:keys [smalim sma hdisp alignrxnmap anon suppressh annotate abbr bgcolor fgcolor showtitle arw dat zoom ratio r f w h svgunits]}]
    (clojure.pprint/pprint {:smalim smalim :sma sma :hdisp hdisp :alignrxnmap alignrxnmap :anon anon :suppressh suppressh :annotate annotate :abbr abbr :bgcolor bgcolor :fgcolor fgcolor :showtitle showtitle :arw arw :dat dat :zoom zoom :ratio ratio :r r :f f :w w :h h :svgunits svgunits})


    )
