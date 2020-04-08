(ns dg.shared.game-logic.dungeon.query
  (:require

   #?(:clj [taoensso.timbre :refer [log trace debug info warn error]])
   #?(:cljs [taoensso.timbre :refer-macros [log trace debug info warn error]])
   ))

(defn get-actors-at-x-y [params x y]
  (let [actors (get-in params [:layout :actors])
        actors-found (filter (fn [a]
                               (and
                                (= (get-in a [:position :x]) x)
                                (= (get-in a [:position :y]) y))) actors)]
    (if (empty? actors-found)
      (if-let [da (get-in params [:layout :default-actor])]
        [da]
        [])
      actors-found)))

(defn get-actor-traits [world actor]
  (let [idx (:actors-index world)
        tp (get idx (:type actor))]
    (:traits tp)))

(defn get-actors-by-trait [world trait]
  (let [actors (get-in world [:actors])
        actors-found (filter (fn [a]
                               (some #{trait} (keys (get-actor-traits world a)))) actors)]
    (if (empty? actors-found)
      []
      actors-found)))

(defn update-actor-by-id [world id actor]
  (update-in world [:actors]
             (fn [actors]
               (info [:AAAA id actors])
               (map (fn [a]
                      (info [:id (:id a)])
                      (if (= (:id a) id)
                        (do (info [:HIT])
                            actor)
                        a))
                    actors))))

(comment
  (get-actors-by-trait dg.shared.game-logic.dungeon.layout/example
                       :controllable)

 (:moveable :controllable)(:call :on :speed) 
  (keys
   (get-actor-traits
    dg.shared.game-logic.dungeon.layout/example
    (last (:actors dg.shared.game-logic.dungeon.layout/example))))

  (some #{:controllable}
        '(:moveable :controllable)) 

  (update-in {:position {:x 11}}
             [:position :x] #(max 0 (min 20 (inc %))))

  )
