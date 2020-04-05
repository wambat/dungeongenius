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
            [clojure.string :as cstr]))

(def actor-chars {:floor "+"
                  :door  "|"
                  :player  "@"
                  :empty "."})
(defn board-size [params]
  (get-in params [:layout :board :size]))

(defn render-blk [params]
  (let [bs (board-size params)
        blk (repeat (* (:x bs)
                       (:y bs)) (:empty actor-chars))]
    (partition (:x bs) blk)))

(defn render-text [blk]
  (cstr/join "\n" (map #(apply str %) blk)))

(defn main [params]
  (let [board (get-in params [:layout :board])
        board-size (:size board)
        t (render-text (render-blk params))]
    (info [:T t])
    [:pre.text-board
     t]))
