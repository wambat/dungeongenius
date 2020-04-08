(ns dg.shared.game-logic.traits.movable
  (:require

   #?(:clj [taoensso.timbre :refer [log trace debug info warn error]])
   #?(:cljs [taoensso.timbre :refer-macros [log trace debug info warn error]])))

(defn upd-position [world actor fn-x fn-y]
  (info [:ORIG-ACTOR actor])
  (let [bs (get-in world [:board :size])
        bs-x (:x bs)
        bs-y (:y bs)
        upd-actor (-> actor
                      (update-in [:position :x] #(max 0 (min bs-x (fn-x %))))
                      (update-in [:position :y] #(max 0 (min bs-y (fn-y %)))))
        ]
    upd-actor))

(defn move [world actor direction]
  (case direction
        :up
        (upd-position world actor identity dec)
        :down
        (upd-position world actor identity inc)
        :left
        (upd-position world actor dec identity)
        :right
        (upd-position world actor inc identity)))

(defn ->make [params]
  {:moveable
   (merge
    {:call move
     :on [:floor]
     :speed 1}
    params)})
