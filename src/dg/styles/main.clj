(ns dg.styles.main
  (:require [garden.def :refer [defstyles]]
            [dg.styles.core.main :as core]
            [dg.styles.layout.main :as layout]))

(defstyles main
  core/main
  layout/main)
