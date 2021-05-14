(ns clojure-system-info.core
  (:gen-class)
  (:require [clojure-system-info.api :refer [api-handler]]
            [clojure-system-info.page :refer [html-handler]]
            [clojure-system-info.system :refer [gc-handler]]
            [org.httpkit.server :refer [run-server]]
            [reitit.ring :as ring]))

(defonce server (atom nil))

(def app
  (ring/ring-handler
   (ring/router
    [["/"
      {:get {:handler html-handler}}]
     ["/api"
      {:get {:handler api-handler}}]
     ["/gc"
      {:get {:handler gc-handler}}]])))

(defn stop-server
  []
  (when-not (nil? @server)
    (println "Stopping server...")
    ;; graceful shutdown: wait 100ms for existing requests to be
    ;; finished :timeout is optional, when no timeout, stop
    ;; immediately
    (@server :timeout 100)
    (reset! server nil)))

(defn start-server
  [& {:keys [port] :or {port 8080}}]
  (println "Starting server on port" port)
  (reset! server (run-server app {:port port})))

(defn restart-server
  []
  (stop-server)
  (start-server))

(defn -main
  "Entrypoint"
  [& args]
  (restart-server))
