(ns clojure-system-info.core
  (:gen-class)
  (:require [clojure-system-info.api :refer [api-handler]]
            [clojure-system-info.page :refer [html-handler]]
            [clojure-system-info.system :refer [gc-handler mem-handler]]
            [clojure.tools.cli :refer [parse-opts]]
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
      {:get {:handler gc-handler}}]
     ["/mem"
      {:get {:handler mem-handler}}]])))

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
  [port]
  (println "Starting server on port" port)
  (reset! server (run-server app {:port port})))

(defn restart-server
  [port]
  (stop-server)
  (start-server port))

(def cli-options
  [["-p" "--port PORT" "Port number"
    :default 8080
    :parse-fn #(Integer/parseInt %)
    :validate [#(< 0 % 0x10000) "Must be a number between 0 and 65536"]]])

(defn -main
  "Entrypoint"
  [& args]
  (let [parsed (parse-opts args cli-options)]
    (restart-server (:port (:options parsed)))))
