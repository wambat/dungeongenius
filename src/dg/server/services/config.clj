(ns dg.server.services.config
  (:require
   [environ.core :refer [env]]
   [taoensso.timbre    :refer [refer-timbre]]
   [mount.core :refer [defstate]]))

(refer-timbre)

(defn get-config []
  {:www-port                    (read-string (:www-port env "3003"))
   :admin-email "noospheratum@gmail.com"
   :info-email "noospheratum@gmail.com"
   })

(defstate state
  :start (get-config))

(defn get-test-config []
  {:www-port (:www-port env 3003)})

(defstate test-state
  :start (get-test-config))
