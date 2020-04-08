(ns dg.client.io.kbd.handlers
  (:require [re-frame.loggers :as u]
            [re-frame.core :as re-frame]
            [taoensso.timbre :refer-macros [log trace debug info warn error]]
            [clojure.string :as cstr]))

(defn register-keypress! []
  (info "Registered key listener")
  (aset js/document "onkeypress"
        (fn [e]
          (info [:KEY e])
          (let [c (.-code e)
                k (.-key e)
                m (if (.-shiftKey e)
                    :shift)
                tgt (.. e -target -tagName toLowerCase)]
            (cond (not (contains? #{"input" "textarea"} tgt))
                  (re-frame/dispatch [::pressed [k c m]]))))))

(defn handle-keypress [cofx [evt key]]
  (let [gesture-acceptors (get (:db cofx)
                               :gesture-acceptors [:dg.client.dungeon.input.handlers])]
    {:db (:db cofx)
     :dispatch-n (map (fn [ga]
                        [(keyword (name ga) "on-key") key])
                      gesture-acceptors)}))

(defn register! []
  (re-frame/reg-event-fx
   ::pressed
   handle-keypress)

  (register-keypress!))
