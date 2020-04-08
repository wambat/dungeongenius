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
            [re-frame.core :as re-frame]
            [dg.client.dungeon.render.text.layout :as text-layout]
            [dg.client.dungeon.model.subs :as dungeon-model]))

(defn main []
  (let [dungeon-layout (re-frame/subscribe [::dungeon-model/current])]
    (fn []
      (info [:DUNGLAYOUT @dungeon-layout])
      [text-layout/main {:layout @dungeon-layout}])))

