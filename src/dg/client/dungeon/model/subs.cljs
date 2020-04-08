(ns dg.client.dungeon.model.subs
  (:require
   [taoensso.timbre :as timbre
    :refer-macros [log
                   trace
                   debug
                   info
                   warn
                   error
                   fatal
                   report]]
   [re-frame.core :as re-frame])
  (:require-macros [reagent.ratom :refer [reaction]]))

(re-frame/reg-sub-raw
 ::current
 (fn [app-db [_]]
   (info [:APPDB @app-db])
   (reaction (get-in @app-db [:current-dungeon]))))
