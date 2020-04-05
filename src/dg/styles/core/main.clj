(ns dg.styles.core.main
  (:require [garden.def :refer [defstyles]]
            [garden.stylesheet :refer [rule]]
            [garden.selectors :as s]
            [garden.stylesheet :refer [at-media]]
            [garden.units :as units :refer [percent px]]))

(defstyles main
  [:body {:font-family "Helvetica Neue"
          :padding "0px"
          :margin "0px"
          :font-size   "16px"
          :line-height 1.5}])
