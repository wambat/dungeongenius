(ns dg.client.dungeon.layout
  (:require [taoensso.timbre :as timbre
             :refer-macros [log
                            trace
                            debug
                            info
                            warn
                            error
                            fatal
                            report]]
            [dg.client.dungeon.render.text.layout :as text-layout]))

(defn main [params]
  (info [:params params])
  [text-layout/main params])

