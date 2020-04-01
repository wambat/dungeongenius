(ns dg.server.services.sente
  (:require
   [clojure.string     :as str]
   [mount.core :refer [defstate]]
   [taoensso.sente     :as sente]
   [taoensso.sente.server-adapters.http-kit      :refer (get-sch-adapter)]
   [taoensso.timbre :refer [refer-timbre]]
   ;; Optional, for Transit encoding:
   [taoensso.sente.packers.transit :as sente-transit]))

(refer-timbre)
;;(def packer (sente-transit/get-flexi-packer :edn)) ; Experimental, needs Transit dep

(defn start-sente []
  (let [{:keys [ch-recv send-fn ajax-post-fn ajax-get-or-ws-handshake-fn
                connected-uids]}
        (sente/make-channel-socket! (get-sch-adapter) {:packer (sente-transit/get-transit-packer)})];;:packer packer
    (trace "Started sente")
    {:ring-ajax-post                ajax-post-fn
     :ring-ajax-get-or-ws-handshake ajax-get-or-ws-handshake-fn
     :ch-chsk                       ch-recv ; ChannelSocket's receive channel
     :chsk-send!                    send-fn ; ChannelSocket's send API fn
     :connected-uids                connected-uids} ; Watchable, read-only atom
    ))

(defstate state
  :start (start-sente))
