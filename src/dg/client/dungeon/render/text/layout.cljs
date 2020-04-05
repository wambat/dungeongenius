(ns dg.client.dungeon.render.text.layout
  (:require [taoensso.timbre :as timbre
             :refer-macros [log
                            trace
                            debug
                            info
                            warn
                            error
                            fatal
                            report]]
            [dg.client.dungeon.render.text.board :as board]))

(defn main [params]
  [board/main params])
