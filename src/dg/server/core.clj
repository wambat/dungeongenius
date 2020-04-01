(ns dg.server.core
  (:require
   [dg.server.services.config]
   [dg.server.services.sente]
   [dg.server.services.web]
   [mount.core :as mount]
   )
  (:gen-class))

;;;; Init

(defn stop! []
  (mount/stop))

(defn start! []
  (mount/start))

(defn -main [& args]
  (start!))
