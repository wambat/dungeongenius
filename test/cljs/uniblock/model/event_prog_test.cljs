(ns uniblock.model.event-prog-test
  (:require-macros [cljs.test :refer [deftest testing is]])
  (:require  [cljs.test :as t :include-macros true]))

(def input-events
  [{:time 0
    :op {:entity :placement
         :player-id :player-1
         :name :add
         :params {:block-id :angle}}}
   {:time 1
    :op {:entity :placement
         :player-id :player-1
         :name :move
         :params {:placement-id :p1
                  :vector [0 0 1] }}}])

(def sample-log
  [{:time 0
    :op {:entity :placement
         :name :add
         :params {:block-id :angle}
         :result {:state {:placements [{:block-id :angle
                                       :id :p1
                                       :coords [0 0 0]
                                       :rotation [0 0 0]} ]}
                  :dispatch-events [[:placement/add
                                     {:block-id :angle
                                      :id :p1
                                      :coords [0 0 0]
                                      :rotation [0 0 0]}]
                                    [:sound/play {:id :tick}]]}}}
   {:time 1
    :op {:entity :placement
         :name :move
         :params {:placement-id :p1
                  :vector [0 0 1] }
         :result {:state {:placements [{:block-id :angle
                                        :coords [0 0 1]
                                        :rotation [0 0 0]}]}
                  :dispatch-events [[:placement/move
                                     {:placement-id :p1
                                      :coords [0 0 1]
                                      :rotation [0 0 0]}]
                                    [:sound/play {:id :tick}]]}}}])

(deftest test-arithmetic-2 []
  (is (= (+ 1 2) 3) "Something is ok."))
