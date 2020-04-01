(ns uniblock.model.game-test

  (:require-macros [cljs.test :refer [deftest testing is]])
  (:require  [cljs.test :as t :include-macros true]
             [uniblock.shared.model.game :as game-model]
             [taoensso.timbre :as timbre
              :refer-macros [log
                             trace
                             debug
                             info
                             warn
                             error
                             fatal
                             report]]))
(def events
  [{:entity :placement
    :name :add
    :player-id :player-1
    :params {:block-id :angle
             :coords [0 0 0]
             :rotation [0 0 0]}}
   {:entity :placement
    :name :move
    :player-id :player-1
    :params {:placement-id :p1
             :coords [0 0 1]
             :rotation [0 0 0]}}])

(def results
  [{:state {:placements [{:block-id :angle
                          :id :p1
                          :coords [0 0 0]
                          :rotation [0 0 0]}]}
    :dispatch-events [[:placement/add
                       {:block-id :angle
                        :id :p1
                        :coords [0 0 0]
                        :rotation [0 0 0]}]
                      [:sound/play {:id :tick}]]}
   {:state {:placements [{:block-id :angle
                          :coords [0 0 1]
                          :rotation [0 0 0]}]}
    :dispatch-events [[:placement/add
                       {:block-id :angle
                        :id :p1
                        :coords [0 0 0]
                        :rotation [0 0 0]}]
                      [:sound/play {:id :tick}]]}])

#_(deftest test-game []
  (let [r (reduce (fn [log op]
                    (let [result (game-model/process-op (-> log last :state) op)]
                      (conj log result))) [{:state {}}] events)]
    ;;(info r)
    (is (= r results) "Something is ok.")))
