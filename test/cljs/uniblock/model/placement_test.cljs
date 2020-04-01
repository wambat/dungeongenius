(ns uniblock.model.placement-test
  (:require-macros [cljs.test :refer [deftest testing is]])
  (:require  [cljs.test :as t :include-macros true]
             [uniblock.shared.model.placement :as placement-model]
             [taoensso.timbre :as timbre
              :refer-macros [log
                             trace
                             debug
                             info
                             warn
                             error
                             fatal
                             report]]))
(def placements
  [{:block-id :angle
    :id :p1
    :coords [1 3 2]
    :rotation [0 0 0]}
   {:block-id :angle
    :id :p2
    :coords [-2 -3 -1]
    :rotation [0 0 0]}])

(def placement
  {:block-id :bar
   :id :p1
   :coords [0 0 0]
   :rotation [0 0 0]})

(deftest test-transform-point []
  (is (= (placement-model/transform-point
          [1 2 3]
          [1 2 1])
         [3 1 2]) "Transforms point x."))

(deftest test-transform-geometry []
  (is (= (placement-model/transform-geometry
          [[0 0 0]
           [0 1 0]
           [0 1 1]
           [1 1 1]]
          [0 0 1])
         [[0 0 0] [1 0 0] [1 0 1] [1 -1 1]]) "Transform geom."))

(deftest test-placement-map []
  (is (= (placement-model/placement-map (first placements)) [[1 3 2] [1 3 3] [1 4 3]]) "Placement maps."))

(deftest test-placements-map []
  (is (= (placement-model/placements-map placements)
         [[1 3 2] [1 3 3] [1 4 3] [-2 -3 -1] [-2 -3 0] [-2 -2 0]]) "Placements maps."))

(deftest test-placement-intesects []
  (is (= (placement-model/placement-intesects
          (placement-model/placements-map placements)
          (first placements)) #{[1 3 2] [1 3 3] [1 4 3]}) "Placement conflicts."))

(deftest test-placement-non-intesects []
  (is (= (placement-model/placement-intesects
          (placement-model/placements-map placements)
          placement) nil) "Placement no conflicts."))
