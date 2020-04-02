(ns dg.shared.game-logic.actors.index
  (:require [dg.shared.game-logic.actors.player :as player]
            [dg.shared.game-logic.actors.floor :as floor]
            [dg.shared.game-logic.actors.door :as door]))

(def idx {:player player/calls
          :floor floor/calls
          :door door/calls})


(defn make-call [params]
  (let [tp (:type params)
        ->ent (get-in idx [tp (:call params)])]
    (->ent (dissoc params :type :call))))


(defn ->make [params]
  (make-call (assoc params :call :make)))
