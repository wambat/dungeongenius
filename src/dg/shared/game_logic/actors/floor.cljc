(ns dg.shared.game-logic.actors.floor)

(def default
  {:position {:x 1 :y 1}
   :type :floor
   :state :normal})

(defn ->make [params]
  (merge
   default
   params))

(def calls {:make ->make})
