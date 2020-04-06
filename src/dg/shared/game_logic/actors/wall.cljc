(ns dg.shared.game-logic.actors.wall)


(def default
  {:position {:x 1 :y 1}
   :type :wall})

(defn ->make [params]
  (merge
   default
   params))

(def calls {:make ->make})
