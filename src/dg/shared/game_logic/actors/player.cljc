(ns dg.shared.game-logic.actors.player
  (:require [dg.shared.game-logic.traits.movable :as t-movable]
            [dg.shared.game-logic.traits.controllable :as t-controllable]))


(def default
  {:position {:x 1 :y 1}
   :type :player
   :state {:facing :right
           :health 3}})

(defn ->make [params]
  (merge
   default
   {:id #?(:cljs (random-uuid)
           :clj (java.util.UUID/randomUUID))}
   params))

(def calls {:make ->make
            :traits (merge
                     (t-movable/->make {:speed 1})
                     (t-controllable/->make {}))})
