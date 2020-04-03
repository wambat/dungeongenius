(ns dg.client.app
  (:require [reagent.core :as reagent :refer [atom]]
            [reagent.dom :as rdom]
            [dg.client.dungeon.layout :as layout]
            [dg.shared.game-logic.dungeon.layout :as logic-layout]
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
            [re-frame.core :as re-frame])
  [:require-macros
   [cljs.core.async.macros :refer [go]]])

(defonce contracts-data (atom {}))

;; (def mis "SMTH IS MISSING" )

(defn register-handlers! []
  ;; (deploy/register!)
  )

(defn root-component []
  (let [dungeon-layout logic-layout/example]
    (fn []
      [:div "DUNGEON GENIUS"
       [:div
        [layout/main {:layout dungeon-layout}]]])))


(defn init []
  (info "INIT")
  (register-handlers!)
  (rdom/render [root-component]
               (.getElementById js/document "app")))


