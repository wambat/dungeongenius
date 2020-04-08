(ns dg.shared.game-logic.traits.controllable
  (:require [dg.shared.game-logic.dungeon.query :as dq]
            #?(:clj [taoensso.timbre :refer [log trace debug info warn error]])
            #?(:cljs [taoensso.timbre :refer-macros [log trace debug info warn error]])))


(defn control [world actor [gesture-type gesture-key]]
  (let [moveable (:moveable (dq/get-actor-traits world actor))]
    (info [:MOVABLET (dq/get-actor-traits world actor)])
    (dq/get-actor-traits world actor)
    (info [:MOVABLE actor moveable gesture-type gesture-key])
    (when (and
           (= gesture-type :player/move)
           moveable)
        ((:call moveable)
         world
         actor
         gesture-key))))

(defn ->make [params]
  {:controllable
   (merge
    {:call control}
    params)})
