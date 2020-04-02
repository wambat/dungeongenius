(ns dg.shared.game-logic.actors.door)


(def default
  {:position {:x 1 :y 1}
   :type :door
   :state :closed})

(defn ->make [params]
  (merge
   default
   params))

(def calls {:make ->make})
