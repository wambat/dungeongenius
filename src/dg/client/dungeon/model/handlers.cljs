(ns dg.client.dungeon.model.handlers
  (:require [re-frame.loggers :as u]
            [re-frame.core :as re-frame]
            [taoensso.timbre :refer-macros [log trace debug info warn error]]
            [dg.shared.game-logic.dungeon.layout :as dungeon-layout]
            [clojure.string :as sstr]))


(defn on-new [cofx [event [skey key m]]]
  (info [:KEYPRESS skey key m])
  (let [state (:db cofx)
        ret dungeon-layout/example]
    (info [:NEW-DUNGEON])
    {:db (-> state
             (assoc :current-dungeon ret)
             (assoc :current-dungeon-events []))}))

(defn register! []
  (re-frame/reg-event-fx
   ::new
   on-new))

