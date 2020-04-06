(ns dg.client.dungeon.render.text.draw-actor
  (:require [dg.client.dungeon.render.text.actors.idx :as act-idx]
            [dg.shared.game-logic.dungeon.query :as q]
            [taoensso.timbre :as timbre
             :refer-macros [log
                            trace
                            debug
                            info
                            warn
                            error
                            fatal
                            report]]
            ))


(defn draw-actor [params screen entity]
  (let [draw-fn (get act-idx/calls (:type entity))]
    (draw-fn params screen entity)))

(defn actor-on-top [actors]
  (last (sort-by :layer actors)))

(defn idx-from-x-y [params x y]
  (let [bx (get-in params [:layout :board :size :x])
        ;; by (get-in params [:layout :board :size :y])
        ]
    (+ (* bx y)
       x)))

(defn put-character [params screen x y act]
  (let [idx (idx-from-x-y params x y)]
    (info [:IDX idx :C (:char act)])
    (assoc (into [] screen) idx (:char act))))

(defn draw-position [params screen x y]
  (let [actors (q/get-actors-at-x-y params x y)]
    (if-let [act (actor-on-top actors)
             ]
      (let [predraw (draw-actor params screen act)]
        (info [:ACT predraw])
        (put-character params screen x y
                       predraw))
      screen)))
