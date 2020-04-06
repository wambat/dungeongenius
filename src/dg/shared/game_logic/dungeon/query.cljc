(ns dg.shared.game-logic.dungeon.query)

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
