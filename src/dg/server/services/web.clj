(ns dg.server.services.web
  (:require
   [mount.core :refer [defstate]]
   [environ.core :refer [env]]
   [ring.middleware.defaults :refer [site-defaults]]
   [ring.middleware.reload :refer [wrap-reload]]
   [compojure.core     :as comp :refer (defroutes GET POST PUT ANY)]
   [compojure.route    :as route]
   [taoensso.sente     :as sente]
   [hiccup.core        :as hiccup]
   [hiccup.element     :as hiccup-e]
   [hiccup.page        :as hiccup-p]
   [taoensso.timbre :refer [refer-timbre]]
   [dg.server.services.sente :as sente-s]
   [dg.server.services.config :as config]
   ;; [uniblock.api.services.datomic :as datomic-s]
   [org.httpkit.server :as http-kit]))

(refer-timbre)

(defn landing-handler [req]
  (hiccup-p/html5 {:lang "en"}
                  [:head
                   [:meta {:charset "utf-8"}]
                   [:title "InSieme"]
                   [:meta {:name "description" :content ""}]
                   [:meta {:name "author" :content ""}]
                   ;; [:meta {:name "version" :content (version-string)}]
                   [:meta {:http-equiv "X-UA-Compatible" :content "IE=edge,chrome=1"}]
                   [:meta {:name "viewport"
                           :content "width=device-width, initial-scale=1.0, maximum-scale=1.0"}]
                   (map (fn [f] [:link {:href f :rel "stylesheet" :type "text/css"}])
                        ["/css/styles.css"])
                   (map (fn [[img size]] [:link {:href img :rel "icon" :sizes size}])
                        [["/images/favicon/android-icon-192x192.png" "192x192"]
                         ["/images/favicon/favicon-32x32.png" "32x32"]
                         ["/images/favicon/favicon-96x96.png" "96x96"]
                         ["/images/favicon/favicon-16x16.png" "16x16"]])
                   #_[:style {:rel "stylesheet"
                            :type "text/css"}
                    (slurp (clojure.java.io/resource "json.human.css"))]
                   [:base {:href "/"}]
                   ]
                  [:body {:id "body"}
                   [:div#app "Initializing..."]
                   ;; [:script {:src (str "js/lib/three.js")}] ; Include our cljs target
                   ;; [:script {:src (str "js/lib/Tween.js")}] ; Include our cljs target
                   ;; [:script {:src "https://cdn.jsdelivr.net/gh/ethereum/web3.js/dist/web3.min.js"}] ; Include our cljs target
                   [:script {:src (str "js/app.js")}] ; Include our cljs target
                   
                   #_(map (fn [s]
                          [:script {:type (:type s)
                                    :id (:id s)}
                           (:script s)]

                          ) shaders/shaders)
                   ;; [:script {:src (str "js/app.js?version=" (version-string))}] ; Include our cljs target
                   ]))

(defroutes routes
  ;;(GET  "/"      req (landing-pg-handler req))
  ;;
  (GET  "/" [] landing-handler)
  (route/not-found landing-handler))



(def app-ring-handler
  (let [ring-defaults-config
        (-> site-defaults
            (assoc-in [:static :resources] "/public/")
            (assoc-in [:security :anti-forgery] {:read-token (fn [req] (-> req :params :csrf-token))}))]

    ;; NB: Sente requires the Ring `wrap-params` + `wrap-keyword-params`
    ;; middleware to work. These are included with
    ;; `ring.middleware.defaults/wrap-defaults` - but you'll need to ensure
    ;; that they're included yourself if you're not using `wrap-defaults`.
    ;;
    ;;wrap-reload
    (ring.middleware.defaults/wrap-defaults routes ring-defaults-config)))

(defn start-web-server!* [ring-handler port]
  (println "Starting http-kit...")
  (let [http-kit-stop-fn (http-kit/run-server ring-handler {:port port})]
    {:server  nil ; http-kit doesn't expose this
     :port    (:local-port (meta http-kit-stop-fn))
     :stop-fn (fn [] (http-kit-stop-fn :timeout 100))}))

(defn start-web-server! [& [port]]
  ;;(stop-web-server!)
  (let [{:keys [stop-fn port] :as server-map}
        (start-web-server!* (var app-ring-handler)
                            (or port 0) ; 0 => auto (any available) port
                            )
        uri (format "http://localhost:%s/" port)]
    (debugf "Web server is running at `%s`" uri)
    #_(try
        (.browse (java.awt.Desktop/getDesktop) (java.net.URI. uri))
        (catch java.awt.HeadlessException _))
    ;;(reset! web-server_ server-map)
    server-map))

(defn stop-web-server! [conn]
  (when-let [stop-fn (:stop-fn conn)] (stop-fn)))

(defstate state
  :start (start-web-server! (:www-port config/state))
  :stop (stop-web-server! state))
