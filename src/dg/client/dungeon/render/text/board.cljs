(ns dg.client.dungeon.render.text.board
  (:require [taoensso.timbre :as timbre
             :refer-macros [log
                            trace
                            debug
                            info
                            warn
                            error
                            fatal
                            report]]
            [clojure.string :as cstr]
            [dg.client.dungeon.render.text.draw-actor :as dact]
            ))

(def actor-chars {:floor "+"
                  :door  "|"
                  :player  "@"
                  :empty "E"})
(defn board-size [params]
  (get-in params [:layout :board :size]))

(defn render-blk [params]
  (let [bs (board-size params)
        blk (repeat (* (:x bs)
                       (:y bs)) (:empty actor-chars))
        filled-blk (reduce (fn [acc [x y]]
                             (dact/draw-position params acc x y))
                           blk
                           (for [x (range 0 (:x bs))
                                 y (range 0 (:y bs))]

                             [x y]))]
    (partition (:x bs) filled-blk)))

(defn render-text [blk]
  (cstr/join "\n" (map #(apply str %) blk)))

(defn main [params]
  (let [board (get-in params [:layout :board])
        board-size (:size board)
        t (render-text (render-blk params))
        ]
    (info [:T t])
    [:div
     [:pre.text-board
      t]
     ;; (str (render-blk params))
     ;; (str (:layout params))
     ]))
