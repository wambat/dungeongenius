(ns dg.shared.game-logic.actors.player)


(def default
  {:position {:x 1 :y 1}
   :type :player
   :state {:facing :right
           :health 3}})

(defn ->make [params]
  (merge
   default
   params))

(defn control [params]
  {:events []
   :actors (get-in params [:layout :actors])})

(def calls {:make ->make
            :control control})
