(ns system-info.api
  (:require [system-info.system :as sys]
            [jsonista.core :as j]))

(defn api-handler
  [req]
  {:status  200
   :headers {"Content-Type" "application/json; charset=utf-8"}
   :body    (j/write-value-as-string (sys/data) j/default-object-mapper)})
