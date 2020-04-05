(set-env!
 :source-paths    #{"src"}
 :resource-paths  #{"resources"}
 :dependencies '[[org.clojure/clojure "1.10.1"]

                 ;;ENV
                 [environ "1.1.0"]
                 [mount "0.1.16"]
                 [org.clojure/core.async "1.1.587"]

                 ;;DATA
                 [com.taoensso/encore "2.119.0"]
                 [org.clojure/core.cache "0.8.2"]
                 [org.clojure/core.memoize "0.8.2" :exclusions [org.clojure/core.cache]]
                 [net.mikera/core.matrix "0.62.0"]
                 ;;CLJS

                 [reagent "0.10.0"]
                 [re-frame "0.12.0"] ;;Process
                 [datascript "0.18.10"] ;;Data store
                 [com.domkm/silk "0.1.2"]
                 [kibu/pushy "0.3.8"] ;;/urls
                 ;; [cljsjs/three "0.0.84-0"]
                 ;; [cljsjs/tween "16.3.1"]
                 [org.clojure/clojurescript "1.10.597"]
                 [day8.re-frame/async-flow-fx "0.1.0"]

                 ;;WEB
                 [ring "1.8.0"]
                 [ring/ring-defaults "0.3.2"]
                 [http-kit "2.3.0"]
                 [http-kit.fake "0.2.2"]
                 [com.taoensso/sente "1.15.0"]
                 [compojure                 "1.6.1"] ; Or routing lib of your choice
                 [hiccup                    "1.0.5"] ; Optional, just for HTML
                 [garden "1.3.9"]
                 [com.cemerick/friend "0.2.3"] ;;auth
                 [com.tombooth/friend-token "0.1.1-SNAPSHOT" :exclusions [clj-time]]

                 ;;FS
                 [me.raynes/fs "1.4.6"]
                 [me.raynes/conch "0.8.0"]

                 ;; Transit deps optional; may be used to aid perf. of larger data payloads
                 ;; (see reference example for details):

                 ;;TRANSPORT
                 [com.cognitect/transit-clj  "1.0.324"]
                 [com.cognitect/transit-cljs "0.8.256"]
                 [cljs-http "0.1.46"]

                 ;;MISC
                 [mvxcvi/puget "1.2.0"]
                 [com.taoensso/timbre "4.10.0"]
                 [com.taoensso/tufte "2.1.0"]

                 ;;CRYPTO
                 [digest "1.4.9"]
                 [district0x.re-frame/web3-fx "1.0.5"]

                 ;;DEV
                 [samestep/boot-refresh "0.1.0" :scope "test"]
                 [adzerk/boot-cljs          "2.1.5"  :scope "test"]
                 [adzerk/boot-cljs-repl     "0.4.0"      :scope "test"]
                 [adzerk/boot-reload        "0.6.0"      :scope "test"]
                 [pandeiro/boot-http        "0.8.3"      :scope "test"]
                 [cider/piggieback "0.4.2" :scope "test"]
                 [nrepl "0.7.0" :scope "test"]
                 ;; [com.cemerick/piggieback   "0.2.2"      :scope "test"]
                 ;; [org.clojure/tools.nrepl   "0.2.13"     :scope "test"]
                 [weasel                    "0.7.1"      :scope "test"]
                 [crisptrutski/boot-cljs-test "0.3.4" :scope "test"]
                 [org.martinklepsch/boot-garden "1.3.2-1" :scope "test"]
                 [binaryage/dirac "1.5.9" :scope "test"]
                 [powerlaces/boot-cljs-devtools "0.2.0" :scope "test"]
                 [binaryage/devtools "1.0.0"]
                 ])

(require
 '[dg.server.core]
 '[samestep.boot-refresh :refer [refresh]]
 '[adzerk.boot-cljs      :refer [cljs]]
 '[adzerk.boot-cljs-repl :refer [cljs-repl start-repl]]
 '[adzerk.boot-reload    :refer [reload]]
 '[pandeiro.boot-http    :refer [serve]]
 '[crisptrutski.boot-cljs-test :refer [test-cljs]]
 '[org.martinklepsch.boot-garden :refer [garden]]
 '[powerlaces.boot-cljs-devtools :refer [cljs-devtools dirac]]
 )


(deftask build []
  (comp (speak)
     (cljs)
     (garden :styles-var 'dg.styles.main/main
             :output-to "public/css/styles.css")))

(deftask run-sente-server
  "Runs sente http-kit webserver"
  []
  (fn [next-task]
    (fn [fileset]
      (dg.server.core/start!)
      (next-task fileset))))

(deftask run []
  (comp
   ;; (serve :httpkit true
   ;;        :reload true
   ;;        :handler 'bonfire.api.core/app-ring-handler)
   (run-sente-server)
   (watch)
   (refresh)
   (cljs-repl)
   (reload)
   (build)))

;; (deftask run []
;;   (comp (serve)
;;      (watch)
;;      (cljs-repl)
;;      (dirac)
;;      (reload)
;;      (build)))


(deftask development []
  (task-options!
   cljs {:optimizations :none
         :compiler-options
         {:closure-defines {'dg.client.config.env.env "development"}}}
   reload {:on-jsload 'dg.client.app/init
           :asset-path "public"})
  identity)


(deftask production []
  (task-options!
   cljs {:optimizations :advanced
         :compiler-options
         {:closure-defines {'dg.client.config.env.env "production"}}}
   garden {:pretty-print false})
  identity)


(deftask dev
  "Simple alias to run application in development mode"
  []
  (comp (development)
     (run)))


(deftask testing []
  (set-env! :source-paths #(conj % "test/cljs"))
  identity)

;;; This prevents a name collision WARNING between the test task and
;;; clojure.core/test, a function that nobody really uses or cares
;;; about.
(ns-unmap 'boot.user 'test)

(deftask test []
  (comp (testing)
     (test-cljs :js-env :phantom
                :exit?  true)))

(deftask auto-test []
  (comp (testing)
     (watch)
     (test-cljs :js-env :phantom)))
