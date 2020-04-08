(ns dg.client.app
  (:require [reagent.core :as reagent :refer [atom]]
            [reagent.dom :as rdom]
            [dg.client.dungeon.layout :as layout]
            [taoensso.timbre :as timbre
             :refer-macros [log
                            trace
                            debug
                            info
                            warn
                            error
                            fatal
                            report]]
            [cljs-http.client :as http]
            [cljs.tools.reader.edn :as edn]
            [cljs.core.async :refer [<!]]
            [re-frame.core :as re-frame]
            [dg.client.dungeon.model.handlers :as model-h]
            [dg.client.dungeon.input.handlers :as input-h]
            [dg.client.io.kbd.handlers :as kbd-h]
            )
  [:require-macros
   [cljs.core.async.macros :refer [go]]])

(defonce contracts-data (atom {}))

;; (def mis "SMTH IS MISSING" )

(defn register-handlers! []
  (model-h/register!)
  (input-h/register!)
  (kbd-h/register!))

(defn root-component []
  [:div "DUNGEON GENIUS"
   [:div
    [layout/main]]])

(defn init-state! []
  (re-frame/dispatch [::model-h/new]))

(defn init []
  (info "INIT")
  (register-handlers!)
  (init-state!)
  (rdom/render [root-component]
               (.getElementById js/document "app")))


