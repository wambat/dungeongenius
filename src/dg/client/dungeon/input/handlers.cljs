(ns dg.client.dungeon.input.handlers
  (:require [re-frame.core :as re-frame]
            [dg.shared.game-logic.dungeon.layout :as dungeon-layout]
            [taoensso.timbre :refer-macros [log trace debug info warn error]]
            [clojure.string :as cstr]))

(def mapping {["KeyA" nil] [:cursor/move :left]
              ["KeyD" nil] [:cursor/move :right]
              ["KeyW" nil] [:cursor/move :up]
              ["KeyS" nil] [:cursor/move :down]
              ["Space" nil] (fn [state]
                              [:placement/add (:cursor state)])

              ["KeyQ" nil]    [:cursor/rotate :up]
              ["KeyQ" :shift] [:cursor/rotate :down]
              ["KeyE" nil]    [:cursor/rotate :left]
              ["KeyE" :shift] [:cursor/rotate :right]
              ["KeyR" nil]    [:cursor/rotate :clock]
              ["KeyR" :shift] [:cursor/rotate :counter]

              ["KeyJ" nil] [:camera/rotate :left]
              ["KeyL" nil] [:camera/rotate :right]
              ["KeyI" nil] [:camera/rotate :up]
              ["KeyK" nil] [:camera/rotate :down]
              ["KeyO" nil] [:camera/rotate :clock]
              ["KeyU" nil] [:camera/rotate :counter]
              })

(defn on-key [cofx [event [skey key m]]]
  (info [:KEYPRESS skey key m])
  (let [action (get mapping [key m])
        state (:db cofx)]
    (if action
      (let [ret (dungeon-layout/make-move
                 {:world (:current-dungeon state)
                  :action  (if (vector? action)
                             action
                             (action (:current-dungeon state)))})]
        (info [:ACTION action])
        {:db
         (-> state
             (assoc :current-dungeon (:world ret))
             (update :current-dungeon-events concat (:events ret))
             )})
      {:db (:db cofx)})))

(defn register! []
  (re-frame/reg-event-fx
   ::on-key
   on-key))
