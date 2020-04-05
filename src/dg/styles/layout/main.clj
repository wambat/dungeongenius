(ns dg.styles.layout.main
  (:require [garden.def :refer [defstyles]]))

(defstyles main
  [:pre.text-board {:font-size   "16px"
                    :width "20em"
                    :height "20em"
                    :overflow :hidden
                    :background-color :lightgrey}])
