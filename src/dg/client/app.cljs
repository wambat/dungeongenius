(ns dg.client.app
  (:require [reagent.core :as reagent :refer [atom]]
            [reagent.dom :as rdom]
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

(defn register-handlers! []
  ;; (deploy/register!)
  )

(defn root-component []
  (let []
    (fn []
      [:div "DUNGEON GENIUS"
       [:div ]])))

(defn contracts-to-update [old new]
  (reduce (fn [acc [name fd]]
            ;; (info [:NAME name])
            ;; (info [:FD fd])
            ;; (info [:CMP (get-in old [name :bin :digest]) (get-in fd [:bin :digest])])
            (if (and
                 (get fd :bin)
                 (not= (get-in old [name :bin :digest])
                       (get-in fd [:bin :digest])))
              (assoc acc name fd)
              acc))
          {}
          (into [] new)))


(defn init []
  (info "INIT")
  (register-handlers!)
  (rdom/render [root-component]
               (.getElementById js/document "app")))


